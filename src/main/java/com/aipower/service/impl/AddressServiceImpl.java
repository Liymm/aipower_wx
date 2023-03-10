package com.aipower.service.impl;

import com.aipower.dao.AddressDao;
import com.aipower.domain.Address;
import com.aipower.service.AddressService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public Boolean updateDefaultAddress(String userId, Long id) {
        List<Address> addressList = list(Wrappers.lambdaQuery(Address.class)
                .eq(Address::getUserId, userId)
                .eq(Address::getDefaultAddress, 1));

        List<Long> ids = addressList.stream().map(Address::getId).collect(Collectors.toList());
        boolean clearAllSet = false;
        boolean setTrue = false;

        if (id > 0) {
            if (ids.size() > 0) {

                clearAllSet = update(Wrappers.<Address>lambdaUpdate()
                        .eq(Address::getUserId, userId)
                        .in(Address::getId, ids)
                        .set(Address::getDefaultAddress, 0));
            } else
                clearAllSet = true;
            setTrue = update(Wrappers.<Address>lambdaUpdate()
                    .eq(Address::getUserId, userId)
                    .eq(Address::getId, id)
                    .set(Address::getDefaultAddress, 1));
        } else {
            if (ids.size() > 1) {
                clearAllSet = update(Wrappers.<Address>lambdaUpdate()
                        .eq(Address::getUserId, userId)
                        .in(Address::getId, ids.remove(0))
                        .set(Address::getDefaultAddress, 0));
                setTrue = true;
            }
        }

        return clearAllSet && setTrue;
    }

    @Override
    public Address selectOneByUserId(String userId) {
        QueryWrapper wrapper=new QueryWrapper<Address>()
                .eq("user_id",userId)
                .eq("default_address",1);
        Address address=baseMapper.selectOne(wrapper);
        return address;
    }
}
