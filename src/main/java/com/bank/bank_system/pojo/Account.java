package com.bank.bank_system.pojo;

import lombok.Data;
import org.apache.ibatis.annotations.Insert;

@Data
public class Account {
    private Integer accountId;
    private String accountName;
    private Long accountBalance;
    private Integer userId;
    private Integer accountStatus;

    // 當查詢有join user表時會將user_login_name和user_name欄位封裝到這兩個屬性
    private String userLoginName;
    private String userName;
}
