package com.bank.bank_system.service;

import com.bank.bank_system.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface UserService {
    User login(String loginName, String password);

    Boolean register(User user);

    Boolean updateInfo(User user);

    User selectByUserId(Integer userId);

}
