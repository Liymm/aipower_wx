package com.aipower.controller;

import lombok.Data;

@Data
public class Result {
    private int success; // 1:请求成功，0，请求失败
    private int code;
    private Object data;
    private String msg;

    public Result() {

    }

    public Result(Code code, Object data) {
        this.code = code.getCode();
        this.success = code.getCode() == 200 ? 1 : 0;
        this.msg = code.getMsg();
        this.data = data;
    }
}
