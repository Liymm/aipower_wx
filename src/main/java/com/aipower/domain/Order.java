package com.aipower.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("tb_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String userId;
    private Long couponId; // 优惠券id
    private String address; // 收货地址
    private String productName; // 商品名称
    private Double originalPrice;// 原价格
    private Double transactionPrice; // 成交价格
    private Double couponPrice; // 券抵扣金额
    @TableField(exist = false)
    private Coupon coupon; // 卷信息
    private Integer payFinish; // 是否支付成功
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; // 创建时间
    private Integer quantity; // 商品数量
}
