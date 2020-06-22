package com.aispeech.ezml.authserver.exception;

import com.aispeech.ezml.authserver.support.ErrorCoder;

/**
 * 无效数据异常
 *
 * @author ZhangXi
 */
public class InvalidDataException extends Exception {

    private ErrorCoder errorCode;

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataException with(ErrorCoder errorCode) {
        this.errorCode = errorCode;
        return this;
    }

}
