package com.aipower.service.impl;

import com.aipower.dao.OrderDao;
import com.aipower.domain.Order;
import com.aipower.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public Order selectOrderById(String userId, Long orderId) {
        return orderDao.selectOrderById(userId, orderId);
    }
}
