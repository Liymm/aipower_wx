package com.aipower.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_order")
public class Order {
    private Long id;
    private String userId;
    private String productName; // 商品名称
    private Double originalPrice;// 原价格
    private Double transactionPrice; // 成交价格
    private Double couponPrice; // 券抵扣金额
    private Coupon coupon; // 卷信息
    private Address address; //  收货地址信息
}
