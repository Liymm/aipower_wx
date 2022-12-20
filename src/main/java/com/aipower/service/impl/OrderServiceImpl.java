package com.aipower.service.impl;

import com.aipower.controller.Code;
import com.aipower.dao.OrderDao;
import com.aipower.domain.Order;
import com.aipower.exception.MyRuntimeException;
import com.aipower.service.CouponService;
import com.aipower.service.OrderService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CouponService couponService;

    @Override
    public Order getOrderDetailsById(String userId, Long orderId) {
        return orderDao.selectOrderById(userId, orderId);
    }

    @Override
    public Order getOne(String userId, Long id) {
        return orderDao.selectOne(Wrappers.lambdaQuery(Order.class)
                .eq(Order::getUserId, userId)
                .eq(Order::getId, id));
    }

    @Override
    public boolean update(Order order, String userId, Long id) {
        order.setPayFinish(null);
        return update(order, Wrappers.lambdaUpdate(Order.class)
                .eq(Order::getUserId, userId)
                .eq(Order::getId, id));
    }

    @Override
    public boolean paySuccess(String userId, Long id, Double couponMoney) {
        boolean updateOrder = update(Wrappers.lambdaUpdate(Order.class)
                .eq(Order::getUserId, userId)
                .eq(Order::getId, id)
                .set(Order::getPayFinish, 1));

        Order order = getOne(userId, id);

        boolean useCoupon = couponService.useCoupon(userId, order.getCouponId(), couponMoney);

        if (!(updateOrder && useCoupon))
            throw new MyRuntimeException(Code.ERR_USE_COUPON);

        return true;
    }
}
