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

    /**
     * 获取用户所有优惠券
     *
     * @param userId 用户id
     * @return 所有匹配的优惠券信息
     */
    @GetMapping
    public Result getAll(@RequestHeader(value = "userId") String userId) {
        List<Coupon> couponList = couponService.list(Wrappers.<Coupon>lambdaQuery().eq(Coupon::getUserId, userId));
        return new Result(Code.SUCCESS, couponList);
    }

    /**
     * 添加优惠券
     *
     * @param userId 用户id
     * @param coupon 优惠券信息
     * @return 结果
     */
    @PostMapping
    public Result saveCoupon(
            @RequestHeader(value = "userId") String userId,
            @RequestBody Coupon coupon
    ) {
        coupon.setUserId(userId);
        boolean success = couponService.save(coupon);
        return new Result(success ? Code.SUCCESS : Code.ERR_SYSTEM, null);
    }

    /**
     * 删除某条优惠券
     *
     * @param userId 用户id
     * @param id     优惠券id
     * @return 结果
     */
    @DeleteMapping("/{id}")
    public Result deleteCouponById(
            @RequestHeader(value = "userId") String userId,
            @PathVariable Long id
    ) {
        boolean success = couponService.remove(Wrappers.<Coupon>lambdaQuery()
                .eq(Coupon::getId, id)
                .eq(Coupon::getUserId, userId));
        return new Result(success ? Code.SUCCESS : Code.ERR_SYSTEM, null);
    }

    /**
     * 更新某个优惠券
     *
     * @param userId 用户id
     * @param id     优惠券id
     * @param coupon 修改信息
     * @return 结果
     */
    @PutMapping("/{id}")
    public Result updateCouponById(
            @RequestHeader(value = "userId") String userId,
            @PathVariable Long id,
            @RequestBody Coupon coupon
    ) {
        boolean success = couponService.update(coupon, Wrappers.lambdaUpdate(Coupon.class)
                .eq(Coupon::getUserId, userId)
                .eq(Coupon::getId, id));
        return new Result(success ? Code.SUCCESS : Code.ERR_SYSTEM, null);
    }
}
