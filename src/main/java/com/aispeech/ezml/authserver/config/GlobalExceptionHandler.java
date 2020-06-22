package com.aispeech.ezml.authserver.config;

import com.aispeech.ezml.authserver.constant.ApiStatus;
import com.aispeech.ezml.authserver.constant.SysECoder;
import com.aispeech.ezml.authserver.exception.InvalidDataException;
import com.aispeech.ezml.authserver.exception.InvalidParamException;
import com.aispeech.ezml.authserver.support.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理
 *
 * @author ZhangXi
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse handleException(Exception e) {
        log.error("处理未知异常", e);
        BaseResponse response = new BaseResponse();
        response.setStatus(ApiStatus.FAILURE);
        response.fail("未知异常", SysECoder.INNER_ERROR);
        return response;
    }

    @ExceptionHandler(InvalidParamException.class)
    @ResponseBody
    public BaseResponse handleInvalidParamException(InvalidParamException e) {
        log.error("处理无效参数异常", e);
        BaseResponse response = new BaseResponse();
        response.setStatus(ApiStatus.FAILURE);
        response.fail("请求参数错误", e.getErrorCode());
        return response;
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseBody
    public BaseResponse handleInvalidDataException(InvalidDataException e) {
        log.error("处理无效数据异常", e);
        BaseResponse response = new BaseResponse();
        response.setStatus(ApiStatus.FAILURE);
        response.fail("部分数据无效", e.getErrorCode());
        return response;
    }


}
