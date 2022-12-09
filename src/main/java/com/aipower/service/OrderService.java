package com.aipower.service;

import com.aipower.domain.Coupon;
import com.aipower.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderService extends IService<Order> {
    Order selectOrderById(String userId, Long orderId);
}
