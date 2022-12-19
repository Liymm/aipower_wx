package com.aipower.service;

import com.aipower.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService extends IService<User>{
    User getUserByUserId(String userId);

    User getUserByWXToken(String wxToken);

    boolean insertUser(User user);
}
