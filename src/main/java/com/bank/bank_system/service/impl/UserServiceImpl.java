package com.bank.bank_system.service.impl;

import com.bank.bank_system.dao.UserDao;
import com.bank.bank_system.exception.BusinessException;
import com.bank.bank_system.pojo.User;
import com.bank.bank_system.service.AccountService;
import com.bank.bank_system.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    // 登入
    @Override
    public User login(String loginName, String password) {
        //密碼在資料庫裡有用SHA256加密
        password = DigestUtils.sha256Hex(password);
        User user = userDao.login(loginName, password);
        return user;
    }

    // 註冊
    @Override
    public Boolean register(User user) {
        //先檢查用戶登入名是否重複
        User u = userDao.selectByUserLoginName(user.getUserLoginName());
        if(u != null){
            return false;
        }

        //將密碼用SHA256加密再存進資料庫
        user.setUserPassword(DigestUtils.sha256Hex(user.getUserPassword()));
        int count = userDao.insert(user);
        return count > 0;
    }

    // 修改個人資料、密碼
    @Override
    public Boolean updateInfo(User user){
        String password = user.getUserPassword();

        // dao設計為如果密碼有更改則進行更改，沒有則維持不變
        if(password != null && password != ""){
            user.setUserPassword(DigestUtils.sha256Hex(user.getUserPassword()));
        }

        int count = userDao.update(user);
        return count > 0;
    }

    @Override
    public User selectByUserId(Integer userId) {
        User user = userDao.selectByUserId(userId);
        return user;
    }


}
