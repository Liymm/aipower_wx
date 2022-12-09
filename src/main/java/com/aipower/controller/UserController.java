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

    @GetMapping("/{userId}")
    public Result getUserByUserId(@PathVariable String userId) {
        User user = userService.getUserByUserId(userId);
        return new Result(user);
    }
}
