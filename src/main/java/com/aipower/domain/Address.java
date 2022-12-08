package com.aipower.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private boolean defaultAddress;

}
