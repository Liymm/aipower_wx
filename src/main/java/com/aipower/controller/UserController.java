package com.aipower.controller;

import com.aipower.domain.User;
import com.aipower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据userId获取用户信息
     *
     * @param userId 用户id
     * @return 结果
     */
    @GetMapping("/userid-{userId}")
    public Result getUserByUserId(@PathVariable String userId) {
        User user = userService.getUserByUserId(userId);
        return new Result(Code.SUCCESS, user);
    }


    /**
     * 根据userId获取用户信息
     *
     * @param openId 微信id
     * @return 结果
     */
    @GetMapping("/openid-{openId}")
    public Result getUserByOpenId(@PathVariable String openId) {
        User user = userService.getUserByWXToken(openId);
        return new Result(Code.SUCCESS, user);
    }
}
