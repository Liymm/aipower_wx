package com.aipower.service.impl;

import com.aipower.dao.AddressDao;
import com.aipower.domain.Address;
import com.aipower.service.AddressService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService {
    @Override
    public Boolean updateDefaultAddress(String userId, Long id) {
        boolean clearAllSet = update(Wrappers.<Address>lambdaUpdate()
                .eq(Address::getUserId, userId)
                .set(Address::getDefaultAddress, false));
        boolean setTrue = false;
        if (id > 0) {
            setTrue = update(Wrappers.<Address>lambdaUpdate()
                    .eq(Address::getUserId, userId)
                    .eq(Address::getId, id)
                    .set(Address::getDefaultAddress, true));
        }
        List<Address> addressList = list(Wrappers.lambdaQuery(Address.class)
                .eq(Address::getUserId, userId)
                .eq(Address::getDefaultAddress, true));
        if (addressList.isEmpty()) {
            boolean setDefault = update(Wrappers.<Address>lambdaUpdate()
                    .eq(Address::getUserId, userId)
                    .orderByDesc(Address::getId)
                    .set(Address::getDefaultAddress, true)
                    .last("limit 1"));
            return clearAllSet && setTrue && setDefault;
        }

        return clearAllSet && setTrue;
    }
}
