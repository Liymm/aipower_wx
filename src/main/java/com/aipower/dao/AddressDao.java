package com.aipower.dao;

import com.aipower.domain.Address;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AddressDao extends BaseMapper<Address> {
    @Select("select id,user_id,address,phone,name,default_address,area_code from tb_address where id=#{id}")
    Address selectById(int id);
}
