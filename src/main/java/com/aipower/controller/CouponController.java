package com.aipower.controller;

import com.aipower.domain.Coupon;
import com.aipower.service.CouponService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @GetMapping
    public Result getAll(@RequestHeader(value = "userId") String userId) {
        List<Coupon> couponList = couponService.list(Wrappers.<Coupon>lambdaQuery().eq(Coupon::getUserId, userId));
        return new Result(couponList);
    }

    @PostMapping
    public Result saveCoupon(
            @RequestHeader(value = "userId") String userId,
            @RequestBody Coupon coupon
    ) {
        coupon.setUserId(userId);
        boolean success = couponService.save(coupon);
        return new Result(success ? 200 : Code.SYSTEM_ERR, success ? "" : "出错了");
    }

    @DeleteMapping("/{id}")
    public Result deleteCouponById(
            @RequestHeader(value = "userId") String userId,
            @PathVariable Long id
    ) {
        boolean success = couponService.remove(Wrappers.<Coupon>lambdaQuery()
                .eq(Coupon::getId, id)
                .eq(Coupon::getUserId, userId));
        return new Result(success ? 200 : Code.SYSTEM_ERR, success ? "" : "出错了");
    }

    @PutMapping("/{id}")
    public Result updateCouponById(
            @RequestHeader(value = "userId") String userId,
            @PathVariable Long id,
            @RequestBody Coupon coupon
    ) {
        boolean success = couponService.update(coupon, Wrappers.lambdaUpdate(Coupon.class)
                .eq(Coupon::getUserId, userId)
                .eq(Coupon::getId, id));
        return new Result(success ? 200 : Code.SYSTEM_ERR, success ? "" : "出错了");
    }
}
