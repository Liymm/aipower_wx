package com.aipower.interceptor;

import com.aipower.controller.Code;
import com.aipower.domain.User;
import com.aipower.exception.LoginStateException;
import com.aipower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("userId");

        if (null != userId) {
            User user = userService.getUserByUserId(userId);
            if (null == user) {
                throw new LoginStateException(Code.ACCOUNT_NOT_REGISTER, "账号未注册");
            }
        }
        return true;
    }
}
