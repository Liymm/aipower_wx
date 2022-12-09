package com.aipower.controller;

import com.aipower.domain.Order;
import com.aipower.service.OrderService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public Result getOrderById(@RequestHeader(value = "userId") String userId, @PathVariable Long id) {
        Order order = orderService.selectOrderById(userId, id);
        return new Result(order);
    }

    @GetMapping
    public Result getAllOrder(@RequestHeader(value = "userId") String userId) {
        List<Order> orderList = orderService.list(Wrappers.<Order>lambdaQuery()
                .eq(Order::getUserId, userId));
        return new Result(orderList);
    }
}
