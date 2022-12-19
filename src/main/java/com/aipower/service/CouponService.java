package com.aipower.service;

import com.aipower.domain.Coupon;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CouponService extends IService<Coupon> {
    boolean useCoupon(String userId, Long id, Double useMoney);
}
