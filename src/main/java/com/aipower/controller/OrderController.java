package com.aipower.controller;

import com.aipower.domain.Order;
import com.aipower.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public Order getOrder(@RequestHeader(value = "userId") String userId, @PathVariable Long id) {
        return orderService.selectOrderById(userId, id);
    }
}
