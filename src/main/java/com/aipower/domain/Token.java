package com.aipower.domain;

/**
 * @author: MM
 * @date: 2022-10-14 14:21
 */
public class Token {
    //接口访问凭证
    private String accessToken;
    //凭证有效期 秒
    private int expiresIn;
    private Long timestamp=System.currentTimeMillis();
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public int getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}   
