package com.aipower.service.impl;

import com.aipower.dao.UserDao;
import com.aipower.domain.User;
import com.aipower.service.UserService;
import com.aipower.utils.MyBatisPlusUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByUserId(String userId) {
        return getOne(Wrappers.<User>lambdaQuery().eq(User::getUserId,userId));
    }

    @Override
    public User getUserByWXToken(String wxToken) {
        return userDao.selectOne(MyBatisPlusUtil.selectUserByWhere("weixin_token", wxToken));
    }

    @Override
    public boolean insertUser(User user) {
        return save(user);
    }
}
