package com.aipower.service;

import com.aipower.domain.Address;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AddressService extends IService<Address> {
    Boolean updateDefaultAddress(String userId, Long id);
    Address selectOneByUserId(String userId);
}
