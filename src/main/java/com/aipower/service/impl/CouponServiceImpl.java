package com.aipower.service.impl;

import com.aipower.controller.Code;
import com.aipower.dao.CouponDao;
import com.aipower.domain.Coupon;
import com.aipower.exception.MyRuntimeException;
import com.aipower.service.CouponService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl extends ServiceImpl<CouponDao, Coupon> implements CouponService {
    @Override
    public boolean useCoupon(String userId, Long id, Double useMoney) {
        Coupon coupon = getOne(Wrappers.lambdaQuery(Coupon.class)
                .eq(Coupon::getUserId, userId)
                .eq(Coupon::getId, id));

        if (null != coupon)
            throw new MyRuntimeException(Code.ERR_CAN_NOT_FIND_COUPON);

        return update(Wrappers.lambdaUpdate(Coupon.class)
                .eq(Coupon::getId, id)
                .eq(Coupon::getUserId, userId)
                .setSql("surplus_money=surplus_money-" + useMoney)
                .setSql("use_number=use_number+1"));
    }
}
