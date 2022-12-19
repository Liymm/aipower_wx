package com.aipower.controller;

import lombok.Getter;

@Getter
public enum Code {
    SUCCESS(200, ""),
    ERR_ACCOUNT_NOT_REGISTER(51001, "账号不存在或未注册"),
    ERR_SYSTEM(99999, "系统异常"),
    ERR_NOT_YOUR_COUPON(52001, "添加的优惠卷不是你的优惠券"),
    ERR_ONLY_UPDATE_ADDRESS(52002, "订单已付款，只支持修改地址信息"),
    ERR_USE_COUPON(52003, ""),
    ERR_PAY_REPEAT(52004, "重复支付订单"),
    ERR_CAN_NOT_FIND_COUPON(53001, "找不到账号对应的优惠券"),
    ;
    private final int code;
    private final String msg;

    private Code(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
