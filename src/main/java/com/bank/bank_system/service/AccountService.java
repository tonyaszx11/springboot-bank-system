package com.bank.bank_system.service;

import com.bank.bank_system.pojo.Account;
import com.bank.bank_system.pojo.Transactions;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

public interface AccountService {
    Boolean applyForAnAccount(Integer userId);

    public Boolean checkIfUserIsAccountOwner(Integer accountId, Integer userId);

    public Boolean checkIsAdmin(Integer userId);

    Account[] selectAccountsByUserId(Integer id);

    Account selectByAccountId(Integer accountId);

    Boolean transferMoney(Integer accountId, Long money, String reciprocalName, String description);

    Boolean deposit(Integer accountId, Long money, String description);

    Boolean withdraw(Integer accountId, Long money, String description);

    Boolean insertTransaction(Transactions transactions);

    //Transactions[] checkTransactionRecordsByUserId(Integer userId);

    Transactions[] checkTransactionRecordsByAccountId(Integer accountId);

    Account[] getUnreviewedAccounts();

    Boolean approveAccount(Integer accountId);

    Boolean rejectAccount(Integer accountId);

}
