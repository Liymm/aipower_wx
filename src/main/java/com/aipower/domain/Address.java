package com.aipower.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@TableName("tb_address")
@Data
public class Address {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String userId;
    private String address;
    private String phone;
    private String name;
    private Integer defaultAddress;
    private Integer areaCode;
}
