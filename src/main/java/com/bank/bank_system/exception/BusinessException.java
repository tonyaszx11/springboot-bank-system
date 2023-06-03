package com.bank.bank_system.exception;


// 自定義異常，當轉帳、存款、提款業務功能失敗時拋出此異常
public class BusinessException extends RuntimeException{
    private String message;
    public BusinessException(){
    }
    public BusinessException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
