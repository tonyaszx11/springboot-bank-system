package com.bank.bank_system.pojo;

import lombok.Data;

@Data
public class User {
    private Integer userId;
    private String userLoginName;
    private String userPassword;
    private String userName ;
    private String userGender;
    private String userPhone;
    private String userAddress;
    private String userEmail;
    private Integer userAuthority;
}
