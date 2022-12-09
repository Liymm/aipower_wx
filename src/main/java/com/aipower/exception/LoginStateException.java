package com.aipower.exception;

import lombok.Data;

@Data
public class LoginStateException extends IllegalStateException {
    private Integer code;

    public LoginStateException(Integer code, String s) {
        super(s);
        this.code = code;
    }

    public LoginStateException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
