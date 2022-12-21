package com.aipower.interceptor;

import com.aipower.controller.Code;
import com.aipower.controller.Result;
import com.aipower.domain.User;
import com.aipower.exception.MyRuntimeException;
import com.aipower.service.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
                throw new MyRuntimeException(Code.ERR_ACCOUNT_NOT_REGISTER);
            }
        }
        return true;
    }

    /**
     * 封装返回参数
     */
    private void returnStr(HttpServletResponse response, Code code) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            Result result = new Result(code, null);
            writer.print(JSON.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
