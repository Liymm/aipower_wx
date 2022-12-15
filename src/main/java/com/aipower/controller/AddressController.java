package com.aipower.controller;

import com.aipower.domain.Address;
import com.aipower.service.AddressService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public Result getAll(@RequestHeader(value = "userId") String userId) {
        List<Address> addressList = addressService.list(Wrappers.<Address>lambdaQuery().eq(Address::getUserId, userId));
        return new Result(addressList);
    }

    @PostMapping
    public Result saveAddress(
            @RequestHeader(value = "userId") String userId,
            @RequestBody Address address
    ) {
        address.setUserId(userId);
        boolean success = addressService.save(address);
        addressService.updateDefaultAddress(userId, -1L);
        return new Result(success ? 200 : Code.SYSTEM_ERR, success ? "" : "出错了");
    }

    @DeleteMapping("/{id}")
    public Result deleteAddressById(
            @RequestHeader(value = "userId") String userId,
            @PathVariable Long id
    ) {
        boolean success = addressService.remove(Wrappers.<Address>lambdaQuery()
                .eq(Address::getId, id)
                .eq(Address::getUserId, userId));
        addressService.updateDefaultAddress(userId, -1L);
        return new Result(success ? 200 : Code.SYSTEM_ERR, success ? "" : "出错了");
    }

    @PutMapping("/{id}")
    public Result updateAddressById(
            @RequestHeader(value = "userId") String userId,
            @PathVariable Long id,
            @RequestBody Address address
    ) {
        boolean success = addressService.update(address, Wrappers.lambdaUpdate(Address.class)
                .eq(Address::getUserId, userId)
                .eq(Address::getId, id));
        addressService.updateDefaultAddress(userId, id);
        return new Result(success ? 200 : Code.SYSTEM_ERR, success ? "" : "出错了");
    }
}
