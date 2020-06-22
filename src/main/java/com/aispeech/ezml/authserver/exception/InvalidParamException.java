package com.aispeech.ezml.authserver.exception;

import com.aispeech.ezml.authserver.support.ErrorCoder;

/**
 * 无效参数异常
 *
 * @author ZhangXi
 */
public class InvalidParamException extends Exception {

    private ErrorCoder errorCode;

    public InvalidParamException(String message) {
        super(message);
    }

    public InvalidParamException(String message, Throwable cause) {
        super(message, cause);
    }


    public InvalidParamException with(ErrorCoder errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ErrorCoder getErrorCode() {
        return errorCode;
    }
}
