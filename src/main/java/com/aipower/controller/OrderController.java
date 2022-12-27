package com.aipower.controller;

import com.aipower.domain.Coupon;
import com.aipower.domain.Order;
import com.aipower.exception.MyRuntimeException;
import com.aipower.service.CouponService;
import com.aipower.service.OrderService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CouponService couponService;

    /**
     * 根据id获取订单详情
     *
     * @param userId 用户id
     * @param id     订单唯一id
     * @return 订单详情
     */
    @GetMapping("/{id}")
    public Result getOrderDetailsById(@RequestHeader(value = "userId") String userId, @PathVariable Long id) {
        Order order = orderService.getOrderDetailsById(userId, id);
        return new Result(Code.SUCCESS, order);
    }

    /**
     * 获取当前用户所有订单
     *
     * @param userId 用户id
     * @return 订单列表
     */
    @GetMapping
    public Result getAllOrder(@RequestHeader(value = "userId") String userId) {
        List<Order> orderList = orderService.list(Wrappers.<Order>lambdaQuery()
                .eq(Order::getUserId, userId));
        return new Result(Code.SUCCESS, orderList);
    }

    @RequestMapping("/list/{uuid}")
    public Result getAllOrderByUuid(@PathVariable String uuid) {
        System.out.println("uuid=="+uuid);
        List<Order> orderList = orderService.list(Wrappers.<Order>lambdaQuery()
                .eq(Order::getUserId, uuid));
        return new Result(Code.SUCCESS, orderList);
    }

    /**
     * 添加订单，由客服人员生成订单，不填userid和地址，后续由用户添加
     *
     * @param order 订单参数
     * @return 结果
     */
    @PostMapping
    public Result saveOrder(@RequestBody Order order) {
        if (null != order.getCouponId()) {
            Coupon coupon = couponService.getById(order.getCouponId());
            if (null == coupon)
                throw new MyRuntimeException(Code.ERR_CAN_NOT_FIND_COUPON);

            if (!Objects.equals(coupon.getUserId(), order.getUserId()))
                throw new MyRuntimeException(Code.ERR_NOT_YOUR_COUPON);
        }
        boolean success = orderService.save(order);
        return new Result(success ? Code.SUCCESS : Code.ERR_SYSTEM, "");
    }

    /**
     * 修改订单信息，如添加userid和地址
     *
     * @param userId 用户id
     * @param id     订单唯一编号
     * @param order  修改的内容
     * @return 结果
     */
    @PutMapping("/{id}")
    public Result updateOrder(@RequestHeader("userId") String userId,
                              @PathVariable Long id,
                              @RequestBody Order order) {
        System.out.println(order);

        Order thisOrder = orderService.getOne(userId, id);

        boolean changePayState = (0 == thisOrder.getPayFinish() && 1 == order.getPayFinish());

        // 已支付的订单不能修改除地址外的其他信息
        if (thisOrder.getPayFinish() == 1) {
            if (null != order.getCouponId()
                    || null != order.getProductName()
                    || null != order.getOriginalPrice()
                    || null != order.getTransactionPrice()
                    || null != order.getCouponPrice())
                throw new MyRuntimeException(Code.ERR_ONLY_UPDATE_ADDRESS);
        }

        boolean success = orderService.update(order, userId, id);

        if (changePayState)
            orderService.paySuccess(userId, id, thisOrder.getCouponPrice());

        return new Result(success ? Code.SUCCESS : Code.ERR_SYSTEM, null);
    }

    @PostMapping("/pay_success/{id}")
    public Result paySuccess(@RequestHeader("userId") String userId,
                             @PathVariable Long id) {
        Order order = orderService.getOne(userId, id);

        if (null == order)
            throw new MyRuntimeException(Code.ERR_CAN_NOT_FIND_COUPON);

        if (order.getPayFinish() == 1)
            throw new MyRuntimeException(Code.ERR_PAY_REPEAT);

        boolean updateCoupon = orderService.paySuccess(userId, id, order.getCouponPrice());

        return new Result(updateCoupon ? Code.SUCCESS : Code.ERR_SYSTEM, null);
    }
}
