package com.bank.bank_system.dao;

import com.bank.bank_system.pojo.Transactions;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface TransactionsDao {
    @Insert("insert into transactions values(null, #{accountId}, #{tranReciprocalId}, #{tranAmount}, #{tranDatetime}" +
            ", #{tranDescription}, #{tranBalance}, #{tranReciprocalBalance}, #{typeId})")
    int insert(Transactions transactions);

    //查詢轉帳紀錄，包括轉帳給別人
    @Select("select tran_id, a.account_name, a2.account_name as reciprocal_account_name, tran_amount, tran_datetime, tran_description, tran_balance, type_id " +
            " from transactions as t join account as a on t.account_id = a.account_id join account a2 on t.tran_reciprocal_id = a2.account_id " +
            " where t.account_id = #{accountId}")
    @ResultMap("transactionsMap")
    ArrayList<Transactions> selectTransferByAccountId(Integer accountId);

    //查詢轉帳紀錄，別人轉給自己
    @Select("select tran_id, a.account_name, a2.account_name as reciprocal_account_name, tran_amount, tran_datetime, tran_description, tran_reciprocal_balance, type_id " +
            "from transactions as t join account as a on t.account_id = a.account_id join account a2 on t.tran_reciprocal_id = a2.account_id " +
            "   where t.tran_reciprocal_id = #{accountId};")
    @ResultMap("transactionsMap")
    ArrayList<Transactions> selectTransferRByAccountId(Integer accountId);

    //查詢存提款紀錄
    @Select("select tran_id, a.account_name, tran_amount, tran_datetime, tran_description, tran_balance" +
            ", type_id from transactions as t join account as a on t.account_id = a.account_id where t.account_id = #{accountId} and t.type_id != 1;")
    @ResultMap("transactionsMap")
    ArrayList<Transactions> selectDepositAndWithdrawByAccountId(Integer accountId);

    @Select("select * from transactions where account_name = #{account_name}")
    @ResultMap("transactionsMap")
    Transactions[] selectByAccountName(String accountName);

    @Select("select * from transactions where user_id = #{userId}}")
    @ResultMap("transactionsMap")
    Transactions[] selectByUserId(Integer userId);


}
