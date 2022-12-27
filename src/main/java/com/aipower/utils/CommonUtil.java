package com.aipower.utils;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

public class CommonUtil {
    /**
     *
     * 生产用户ID规则
     * @author lunzi
     */
    public static String generateUserId(String content){
        String currentTime = String.valueOf(System.currentTimeMillis());
        String userId = null;
        try {
            userId = DigestUtils.md5DigestAsHex((content + currentTime).getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return userId;
    }
    /**
     * 请求微信接口获取回调数据
     * @param appid
     * @param secret
     * @param code
     * @return
     */
    public static String getBaseAccessTokenUrl(String appid,String secret,String code){
        StringBuffer url=new StringBuffer()
                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid="+appid)
                .append("&secret="+secret)
                .append("&code="+code)
                .append("&grant_type=authorization_code");
        return url.toString();
    }

}
