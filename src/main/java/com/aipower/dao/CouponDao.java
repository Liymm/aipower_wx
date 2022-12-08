package com.aipower.dao;

import com.aipower.domain.Address;
import com.aipower.domain.Coupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponDao extends BaseMapper<Coupon> {
}
