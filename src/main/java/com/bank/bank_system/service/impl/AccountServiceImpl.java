package com.bank.bank_system.service.impl;

import com.bank.bank_system.dao.AccountDao;
import com.bank.bank_system.dao.TransactionsDao;
import com.bank.bank_system.dao.UserDao;
import com.bank.bank_system.exception.BusinessException;
import com.bank.bank_system.pojo.Account;
import com.bank.bank_system.pojo.Transactions;
import com.bank.bank_system.pojo.User;
import com.bank.bank_system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.timestamp.HttpTimestamper;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TransactionsDao transactionsDao;

    @Autowired
    private UserDao userDao;

    //申請account，user註冊後還需要再申請account
    @Override
    public Boolean applyForAnAccount(Integer userId){
        //先檢查此用戶是否已經有正在申請的帳號，有則不允許再申請
        Account applyingAccount = accountDao.hasApplyingAccount(userId);
        if(applyingAccount != null){
            return false;
        }


        //生成帳戶名稱為10位隨機數字
        String accountName = "";
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            accountName += String.valueOf(random.nextInt(10));
        }

        Account account = new Account();
        account.setAccountName(accountName);
        account.setAccountBalance(0L);
        account.setUserId(userId);
        account.setAccountStatus(0);

        //檢查隨機生成的帳戶名是否重複
        Account acc = accountDao.selectByAccountName(account.getAccountName());
        if(acc != null){
            return false;
        }

        int c = accountDao.insert(account);
        return c > 0;

    }

    // 有些controller方法的請求參數是用accountId進行訪問，為了安全，因此會先確認操作者是否為此帳戶的擁有者
    // 方式:取得操作者的session的userId與account的userId進行比對
    @Override
    public Boolean checkIfUserIsAccountOwner(Integer accountId, Integer userId){
        Account account = selectByAccountId(accountId);

        if(account == null){
            return false;
        }

        if(userId == account.getUserId()){
            return true;
        }else{
            return false;
        }
    }

    // 當進行審核帳戶相關的操作時，會先進行驗證是否為管理員
    // 方式: 登入時會session會記錄使用者的權限，取出來判斷即可
    @Override
    public Boolean checkIsAdmin(Integer userId) {
        User user = userDao.selectByUserId(userId);
        return user.getUserAuthority() == 1; //0為客戶，1為管理員
    }

    @Override
    public Account[] selectAccountsByUserId(Integer id) {
        Account[] accounts = accountDao.selectByUserId(id);
        return accounts;
    }

    @Override
    public Account selectByAccountId(Integer accountId) {
        Account account = accountDao.selectByAccountId(accountId);
        return account;
    }

    // 轉帳功能
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean transferMoney(Integer accountId, Long money, String reciprocalName, String description) {
        Account account = accountDao.selectByAccountId(accountId);
        Account reciprocalAccount = accountDao.selectByAccountName(reciprocalName);

        //如果查不到對方帳戶，return false
        if(reciprocalAccount == null){
            return false;
        }

        //如果帳戶與對方帳戶相同，return false
        if(account.getAccountId() == reciprocalAccount.getAccountId()){
            return false;
        }

        //檢查餘額是否足夠
        if(account.getAccountBalance() < money){
            return false;
        }

        int c1 = accountDao.minusMoneyByAccountId(account.getAccountId(), money);
        int c2 = accountDao.addMoneyByAccountId(reciprocalAccount.getAccountId(), money);

        if(c1 == 0 || c2 == 0){
            //交易失敗會拋異常，於是上面的dao更新金額操作會rollback
            throw new BusinessException("轉帳失敗!!!");
        }

        //紀錄交易紀錄
        Transactions transactions = new Transactions();

        //取得現在時間，並轉成yyyy-MM-dd hh:mm:ss格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String d = sdf.format(new Date());

        transactions.setAccountId(accountId);
        transactions.setTranReciprocalId(reciprocalAccount.getAccountId());
        transactions.setTranAmount(money);
        transactions.setTranDatetime(d);
        transactions.setTranDescription(description);
        transactions.setTranBalance(account.getAccountBalance() - money);
        transactions.setTranReciprocalBalance(reciprocalAccount.getAccountBalance() + money);
        transactions.setTypeId(1);

        this.insertTransaction(transactions);
        return true;

    }

    // 存款功能
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deposit(Integer accountId, Long money, String description){
        Account account = accountDao.selectByAccountId(accountId);
        int c = accountDao.addMoneyByAccountId(account.getAccountId(), money);

        if(c == 0){
            //交易失敗會拋異常，於是上面的dao更新金額操作會rollback
            throw new BusinessException("存款失敗!!!");
        }

        //紀錄交易紀錄
        Transactions transactions = new Transactions();
        //取得現在時間，並轉成yyyy-MM-dd hh:mm:ss格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String d = sdf.format(new Date());

        transactions.setAccountId(accountId);
        transactions.setTranReciprocalId(null);
        transactions.setTranAmount(money);
        transactions.setTranDatetime(d);
        transactions.setTranDescription(description);
        transactions.setTranBalance(account.getAccountBalance() + money);
        transactions.setTypeId(2);

        //交易成功，就紀錄
        this.insertTransaction(transactions);
        return true;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean withdraw(Integer accountId, Long money, String description){
        Account account = accountDao.selectByAccountId(accountId);
        //檢查餘額是否足夠
        if(account.getAccountBalance() < money){
            return false;
        }

        int c = accountDao.minusMoneyByAccountId(account.getAccountId(), money);

        if(c == 0){
            //交易失敗
            throw new BusinessException("提款失敗!!!");
        }


        //取得現在時間，並轉成yyyy-MM-dd hh:mm:ss格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String d = sdf.format(new Date());

        Transactions transactions = new Transactions();
        transactions.setAccountId(accountId);
        transactions.setTranReciprocalId(null);
        transactions.setTranAmount(money);
        transactions.setTranDatetime(d);
        transactions.setTranDescription(description);
        transactions.setTranBalance(account.getAccountBalance() - money);
        transactions.setTypeId(3);

        //交易成功，就紀錄
        this.insertTransaction(transactions);
        return true;
    }

    @Override
    public Boolean insertTransaction(Transactions transactions) {
        int count = transactionsDao.insert(transactions);
        return count > 0;
    }

    //透過accountId查詢帳戶交易紀錄的功能
    @Override
    public Transactions[] checkTransactionRecordsByAccountId(Integer accountId) {
        ArrayList<Transactions> lists = new ArrayList<>();
        //存提款紀錄
        ArrayList<Transactions> depositAndWithdrawTransactions = transactionsDao.selectDepositAndWithdrawByAccountId(accountId);
        //轉帳紀錄，轉帳給別人的紀錄
        ArrayList<Transactions> transferTransactions = transactionsDao.selectTransferByAccountId(accountId);
        //轉帳紀錄，別人轉帳給自己的紀錄
        ArrayList<Transactions> transferRTransactions = transactionsDao.selectTransferRByAccountId(accountId);
        lists.addAll(depositAndWithdrawTransactions);
        lists.addAll(transferTransactions);
        lists.addAll(transferRTransactions);
        Transactions[] transactions = lists.toArray(new Transactions[lists.size()]);

        //將記錄按照日期排序
        Arrays.sort(transactions, new Comparator<Transactions>() {
            @Override
            public int compare(Transactions o1, Transactions o2) {
                return o1.getTranDatetime().compareTo(o2.getTranDatetime());
            }
        });
        return transactions;
    }

    // 查詢所有未審核的帳戶(account_status = 0 的帳戶)
    @Override
    public Account[] getUnreviewedAccounts() {
        Account[] accounts = accountDao.selectUnreviewedAccounts();
        return accounts;
    }

    // 審核通過帳戶
    @Override
    public Boolean approveAccount(Integer accountId) {
        int c = accountDao.approveAccount(accountId);
        return c > 0;
    }

    // 審核拒絕通過帳戶
    @Override
    public Boolean rejectAccount(Integer accountId) {
        int c = accountDao.rejectAccount(accountId);
        return c > 0;
    }


}
