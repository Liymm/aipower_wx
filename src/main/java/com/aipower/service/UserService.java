package com.aipower.service;

import com.aipower.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService{
    User getUserByUserId(String userId);

    User getUserByWXToken(String wxToken);

    boolean insertUser(User user);
}
