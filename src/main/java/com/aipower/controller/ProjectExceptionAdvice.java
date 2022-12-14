package com.aipower.controller;

import com.aipower.exception.LoginStateException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public Result doException(Exception e) {
        System.out.println("发生了未知异常:" + e.getMessage());
        return new Result(Code.SYSTEM_ERR, "系统异常，请稍后再试");
    }

    @ExceptionHandler(LoginStateException.class)
    public Result doLoginException(LoginStateException e) {
        return new Result(e.getCode(), e.getMessage());
    }
}
