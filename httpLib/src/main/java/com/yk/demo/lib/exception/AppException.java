package com.yk.demo.lib.exception;

/**
 * App异常
 * @author yk
 * @version V1.0.0
 * created at 2016/11/25 14:56
 */
public class AppException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private int errorCode;
    private String errorMessage;

    public AppException(int errorCode, String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "AppException{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

}
