package com.aipower.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_coupon")
public class Coupon {
    private Long id;
    private String userId;
    private Double money; // 券金额
    private Double surplusMoney; // 券剩余金额
    private Integer useNumber;// 券使用次数
    private Integer type;// 券类型
    private Date expirationTime;// 券过期时间

}
