package com.aipower.service.impl;

import com.aipower.dao.UserDao;
import com.aipower.domain.User;
import com.aipower.service.UserService;
import com.aipower.utils.MyBatisPlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByUserId(String userId) {
        return userDao.selectOne(MyBatisPlusUtil.selectUserByWhere("user_id", userId));
    }

    @Override
    public User getUserByWXToken(String wxToken) {
        return userDao.selectOne(MyBatisPlusUtil.selectUserByWhere("weixin_token", wxToken));
    }

    @Override
    public boolean insertUser(User user) {
        return userDao.insert(user) > 0;
    }
}
