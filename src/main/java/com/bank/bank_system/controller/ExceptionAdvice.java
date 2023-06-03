package com.bank.bank_system.controller;

import com.bank.bank_system.common.Result;
import com.bank.bank_system.exception.BusinessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {

    //當轉帳、存款、提款的業務方法操作失敗時會throw此異常，可讓事務rollback
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result doBusinessException(Exception e){
        String ex = e.getClass() + " : " + e.getMessage();
        return new Result(0, ex, e.getMessage());
    }

    //其他異常處理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception e){
        e.printStackTrace();
        String ex = e.getClass() + " : " + e.getMessage();
        return new Result(0, ex, "發生異常!!!");
    }
}
