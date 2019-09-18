package com.zljx.exception;

/**
 * @Author: leichengzhi
 * @email: leichengzhi@bstyjy.cn
 * @Date: 2019/3/29 11:19
 * @Version 1.0
 */
public class LoginParamException extends RuntimeException{
    public LoginParamException() {
        super();
    }

    public LoginParamException(String message) {
        super(message);
    }

    public LoginParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginParamException(Throwable cause) {
        super(cause);
    }

    protected LoginParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
