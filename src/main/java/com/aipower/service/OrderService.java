package com.aipower.service;

import com.aipower.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrderService extends IService<Order> {
    Order selectOrderById(String userId, Long orderId);
}
