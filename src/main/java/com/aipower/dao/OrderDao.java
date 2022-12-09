package com.aipower.dao;

import com.aipower.domain.Address;
import com.aipower.domain.Coupon;
import com.aipower.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao extends BaseMapper<Order> {
    @Select("select id,user_id,product_name,original_price,transaction_price,coupon_price,address_id,coupon_id " +
            "from tb_order " +
            "where user_id=#{userId} " +
            "and id=#{orderId}")
    @Results({
            @Result(column = "address_id",property = "address",javaType = Address.class,
            many = @Many(select = "com.aipower.dao.AddressDao.selectById")),
            @Result(column = "coupon_id", property = "coupon",javaType = Coupon.class,
            many = @Many(select = "com.aipower.dao.CouponDao.selectById"))
    })
    Order selectOrderById(String userId, Long orderId);
}
