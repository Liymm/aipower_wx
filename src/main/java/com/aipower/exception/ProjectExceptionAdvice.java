package com.aipower.exception;

import com.aipower.controller.Code;
import com.aipower.controller.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public Result doException(Exception e) {
        System.out.println("发生了未知异常:" + e.getMessage());
        return new Result(Code.ERR_SYSTEM, "系统异常，请稍后再试");
    }

    @ExceptionHandler(MyRuntimeException.class)
    public Result doAppException(MyRuntimeException e) {
        return new Result(e.getCode(), e.getMessage());
    }

}
