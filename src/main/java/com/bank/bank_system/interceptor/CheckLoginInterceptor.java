package com.bank.bank_system.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 攔截器在訪問controller時會檢查用戶是否登入，沒登入會回應給前端進行redirect到登入頁面
// 方式:確認session裡是否有userId
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer userId = (Integer)request.getSession().getAttribute("userId");
        if(userId == null){
            //System.out.println("沒有userId，尚未登入");
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
