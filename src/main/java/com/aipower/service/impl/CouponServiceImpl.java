package com.aipower.service.impl;

import com.aipower.dao.AddressDao;
import com.aipower.dao.CouponDao;
import com.aipower.domain.Address;
import com.aipower.domain.Coupon;
import com.aipower.service.AddressService;
import com.aipower.service.CouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl extends ServiceImpl<CouponDao, Coupon> implements CouponService {
}
