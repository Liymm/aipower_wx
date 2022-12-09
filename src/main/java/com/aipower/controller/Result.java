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

    public Result(Object data) {
        this.code = 200;
        this.success = 1;
        this.data = data;
    }

    public Result(int code, String msg) {
        this.success = code == 200 ? 1 : 0;
        this.code = code;
        this.msg = msg;
    }
}
