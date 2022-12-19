package com.aipower.exception;

import com.aipower.controller.Code;
import lombok.Data;

@Data
public class MyRuntimeException extends RuntimeException {

    private Code code;

    public MyRuntimeException(Code code) {
        super("错误码：" + code.getCode() + ", " + code.getMsg());
        this.code = code;
    }

    public MyRuntimeException(Code code, Throwable cause) {
        super("错误码：" + code.getCode() + ", " + code.getMsg());
        this.code = code;
    }
}
