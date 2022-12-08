package com.aipower.utils;

import com.aipower.domain.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class MyBatisPlusUtil {
    public static Wrapper<User> selectUserByWhere(String column, Object val) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column, val);
        return queryWrapper;
    }
}
