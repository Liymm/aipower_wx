package com.aipower.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
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
}
