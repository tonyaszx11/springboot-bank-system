package com.bank.bank_system.controller;

import com.bank.bank_system.common.Code;
import com.bank.bank_system.common.Result;
import com.bank.bank_system.pojo.User;
import com.bank.bank_system.service.UserService;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    // 登入功能
    @PostMapping("/login")
    public Result userLogin(@RequestBody User u, HttpSession session){
        String loginName = u.getUserLoginName().trim();
        String password = u.getUserPassword().trim();
        User user = userService.login(loginName, password);

        Integer userAuthority = null;
        if(user != null){
            userAuthority = user.getUserAuthority();

            //登入成功後，將userId和userAuthority(用戶權限，0為客戶，1為管理員)存到session裡
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userAuthority", userAuthority);
        }


        Result r = new Result();
        r.setCode(user != null ? Code.SUCCESS : Code.ERROR);
        //登入的結果會把userAuthority當成data回應給前端，前端可以登入的是判斷客戶或是管理員，將其引導到相對應的首頁
        r.setData(userAuthority);
        r.setMsg(user != null ? "登入成功" : "登入失敗");
        return r;
    }

    // 登出功能
    @PostMapping("/logout")
    public Result logout(HttpSession session){
        session.removeAttribute("userId");
        return new Result(Code.SUCCESS, null, "登出");
    }

    // 註冊功能
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        user.setUserLoginName(user.getUserLoginName().trim());
        user.setUserPassword(user.getUserPassword().trim());
        Boolean flag = userService.register(user);
        Result r = new Result();
        r.setCode(flag ? Code.SUCCESS : Code.ERROR);
        r.setData(flag);
        r.setMsg(flag ? "註冊成功" : "註冊失敗");
        return r;
    }

    // 更新用戶個人資料
    @PutMapping
    public Result updateInfo(@RequestBody User user, HttpSession session){
        user.setUserId((Integer) session.getAttribute("userId"));
        Boolean flag = userService.updateInfo(user);
        Result r = new Result();
        r.setCode(flag ? Code.SUCCESS : Code.ERROR);
        r.setData(flag);
        r.setMsg(flag ? "更新資料成功" : "更新資料失敗");
        return r;
    }

    // 取得用戶個人資料
    @GetMapping
    public Result selectByUserId(HttpSession session){
        User user = userService.selectByUserId((Integer) session.getAttribute("userId"));
        return new Result(user != null ? Code.SUCCESS : Code.ERROR, user, user != null ? "查詢成功" : "查詢失敗");
    }

}
