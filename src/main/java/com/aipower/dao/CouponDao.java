package com.aipower.dao;

import com.aipower.domain.Address;
import com.aipower.domain.Coupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CouponDao extends BaseMapper<Coupon> {
    @Select("select id,user_id,money,surplus_money,type,use_number,expiration_time " +
            "from tb_coupon where id=#{id}")
    Coupon selectById(int id);
}
