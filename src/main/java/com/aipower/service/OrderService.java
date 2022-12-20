package com.aipower.service;

import com.aipower.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrderService extends IService<Order> {
    Order getOrderDetailsById(String userId, Long orderId);

    Order getOne(String userId, Long id);

    boolean update(Order order, String userId, Long id);

    boolean paySuccess(String userId, Long id, Double couponMoney);
}
