package com.bank.bank_system.controller;

import com.bank.bank_system.common.Code;
import com.bank.bank_system.common.Result;
import com.bank.bank_system.pojo.Account;
import com.bank.bank_system.pojo.Transactions;
import com.bank.bank_system.service.AccountService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    // 客戶申請帳戶
    @PostMapping("/apply")
    public Result applyForAnAccount(HttpSession session){
        Boolean flag = accountService.applyForAnAccount((Integer) session.getAttribute("userId"));
        return new Result(flag ? Code.SUCCESS : Code.ERROR, flag, flag ? "申請帳戶成功" : "申請帳戶失敗");
    }

    // 根據userId來查詢擁有的account，前端網頁的查詢帳戶功能會使用到
    @GetMapping
    public Result selectAccountsByUserId(HttpSession session){
        Integer id = (Integer) session.getAttribute("userId");
        Account[] accounts = accountService.selectAccountsByUserId(id);

        boolean flag = accounts != null;
        return new Result(flag ? Code.SUCCESS : Code.ERROR, accounts, flag ? "查詢成功" : "查不到您的帳號，請重試");
    }

    // 取得accountId後再獲得對應的account的資訊，如餘額
    @GetMapping("/{accountId}")
    public Result getAccountInfo(@PathVariable Integer accountId, HttpSession session){
        Boolean identification = accountService.checkIfUserIsAccountOwner(accountId, (Integer)session.getAttribute("userId"));
        if(identification != true){
            return new Result(Code.ERROR, null, "身分驗證失敗");
        }

        Account account = accountService.selectByAccountId(accountId);
        boolean flag = account != null;
        return new Result(flag ? Code.SUCCESS : Code.ERROR, account, flag ? "查詢成功" : "查不到您的帳號或身分驗證失敗，請重試");
    }

    // 轉帳功能
    @PostMapping("/transfer")
    public Result transferMoney(Integer accountId, Long money, String reciprocalName, String description, HttpSession session){
        Boolean identification = accountService.checkIfUserIsAccountOwner(accountId, (Integer)session.getAttribute("userId"));
        if(identification != true){
            return new Result(Code.ERROR, null, "身分驗證失敗");
        }

        Boolean flag = accountService.transferMoney(accountId, money, reciprocalName, description);
        return new Result(flag ? Code.SUCCESS : Code.ERROR, flag, flag ? "轉帳成功" : "轉帳失敗");
    }

    // 存款功能
    @PostMapping("/deposit")
    public Result deposit(Integer accountId, Long money, String description, HttpSession session){
        Boolean identification = accountService.checkIfUserIsAccountOwner(accountId, (Integer)session.getAttribute("userId"));
        if(identification != true){
            return new Result(Code.ERROR, null, "身分驗證失敗");
        }

        Boolean flag = accountService.deposit(accountId, money, description);
        return new Result(flag ? Code.SUCCESS : Code.ERROR, flag, flag ? "存款成功" : "存款失敗，請重試");
    }

    // 提款功能
    @PostMapping("/withdraw")
    public Result withdraw(Integer accountId, Long money, String description, HttpSession session){
        Boolean identification = accountService.checkIfUserIsAccountOwner(accountId, (Integer)session.getAttribute("userId"));
        if(identification != true){
            return new Result(Code.ERROR, null, "身分驗證失敗");
        }

        Boolean flag = accountService.withdraw(accountId, money, description);
        return new Result(flag ? Code.SUCCESS : Code.ERROR, flag, flag ? "取款成功" : "取款失敗，請重試");
    }

    // 根據accountId取得對應帳戶的交易紀錄
    @GetMapping("/transactions/{accountId}")
    public Result selectTransactionsByAccountId(@PathVariable Integer accountId, HttpSession session){
        Boolean identification = accountService.checkIfUserIsAccountOwner(accountId, (Integer)session.getAttribute("userId"));
        if(identification != true){
            return new Result(Code.ERROR, null, "身分驗證失敗");
        }

        Transactions[] transactions = accountService.checkTransactionRecordsByAccountId(accountId);
        return new Result(transactions != null ? Code.SUCCESS : Code.ERROR,
                transactions, transactions != null ? "查詢成功" : "沒有此帳號的紀錄或身分驗證失敗");
    }

    // 管理員取得未審核帳戶(account_status = 0 的帳戶)
    @GetMapping("/review")
    public Result getUnreviewedAccounts(HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        Boolean isAdmin = accountService.checkIsAdmin(userId);
        if(isAdmin == false){
            return new Result(Code.ERROR, null, "沒有管理員權限");
        }

        Account[] accounts = accountService.getUnreviewedAccounts();
        return new Result(accounts.length > 0 ? Code.SUCCESS : Code.ERROR, accounts, "");
    }

    // 管理員審核通過帳戶
    @PatchMapping("/review/{accountId}")
    public Result approveAccount(@PathVariable Integer accountId, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        Boolean isAdmin = accountService.checkIsAdmin(userId);
        if(isAdmin == false){
            return new Result(Code.ERROR, null, "沒有管理員權限");
        }

        Boolean flag = accountService.approveAccount(accountId);
        return new Result(flag ? Code.SUCCESS : Code.ERROR, flag, flag ? "審核通過操作成功" : "審核通過操作失敗");
    }

    // 管理員審核拒絕帳戶
    @DeleteMapping("/review/{accountId}")
    public Result rejectAccount(@PathVariable Integer accountId, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        Boolean isAdmin = accountService.checkIsAdmin(userId);
        if(isAdmin == false){
            return new Result(Code.ERROR, null, "沒有管理員權限");
        }

        Boolean flag = accountService.rejectAccount(accountId);
        return new Result(flag ? Code.SUCCESS : Code.ERROR, flag, flag ? "審核拒絕操作成功" : "審核拒絕操作失敗");
    }
}
