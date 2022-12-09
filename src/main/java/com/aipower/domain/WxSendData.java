package com.aipower.domain;

import lombok.Data;

@Data
public class WxSendData {
    private String value;
    private String color="#173177";

    public WxSendData(String value) {
        this.value = value;
    }
}
