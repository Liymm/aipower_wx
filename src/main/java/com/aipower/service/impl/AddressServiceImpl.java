package com.aipower.service.impl;

import com.aipower.dao.AddressDao;
import com.aipower.domain.Address;
import com.aipower.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService {
}
