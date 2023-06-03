package com.bank.bank_system.dao;

import com.bank.bank_system.pojo.Account;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountDao {
    @Insert("insert into account values(null, #{accountName}, #{accountBalance}, #{userId}, #{accountStatus})")
    int insert(Account account);

    //用userId查account
    @Select("select account_id, account_name, account_balance, user_id, account_status from account where user_id = #{id}" +
            " and account_status = 1")
    @ResultMap("accountMap")
    Account[] selectByUserId(Integer id);

    @Select("select account_id, account_name, account_balance, user_id, account_status from account where account_id = #{id}" +
            " and account_status = 1")
    @ResultMap("accountMap")
    Account selectByAccountId(Integer id);

    //用accountName查account
    @Select("select account_id, account_name, account_balance, user_id, account_status from account where account_name = #{accountName}" +
            " and account_status = 1")
    @ResultMap("accountMap")
    Account selectByAccountName(String accountName);

    @Update("update account set account_balance = account_balance + #{money} where account_id = #{accountId}" +
            " and account_status = 1")
    int addMoneyByAccountId(@Param("accountId") Integer accountId, @Param("money") Long Money);

    @Update("update account set account_balance = account_balance - #{money} where account_id = #{accountId}" +
            " and account_status = 1")
    int minusMoneyByAccountId(@Param("accountId") Integer accountId, @Param("money") Long Money);

    @Select("select a.account_id, a.account_name, u.user_id, u.user_login_name, u.user_name " +
            "from account as a join user as u on a.user_id = u.user_id " +
            "where a.account_status = 0")
    @ResultMap("accountMap")
    Account[] selectUnreviewedAccounts();

    @Update("update account set account_status = 1 where account_id = #{accountId}")
    int approveAccount(Integer accountId);

    @Update("update account set account_status = -1 where account_id = #{accountId}")
    int rejectAccount(Integer accountId);

    @Select("select account_id from account where user_id = #{userId} and account_status = 0")
    @ResultMap("accountMap")
    Account hasApplyingAccount(Integer userId);
}

