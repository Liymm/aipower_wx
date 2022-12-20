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

    /**
     * 获取该用户所有收货地址
     *
     * @param userId 用户id
     * @return 匹配的收货地址列表
     */
    @GetMapping
    public Result getAll(@RequestHeader(value = "userId") String userId) {
        List<Address> addressList = addressService.list(Wrappers.<Address>lambdaQuery().eq(Address::getUserId, userId));
        return new Result(Code.SUCCESS, addressList);
    }

    /**
     * 添加收货地址
     *
     * @param userId  用户id
     * @param address 地址信息
     * @return 结果
     */
    @PostMapping
    public Result saveAddress(
            @RequestHeader(value = "userId") String userId,
            @RequestBody Address address
    ) {
        address.setUserId(userId);
        boolean success = addressService.save(address);
        addressService.updateDefaultAddress(userId, address.getDefaultAddress() == 1 ? address.getId() : -1L);
        return new Result(success ? Code.SUCCESS : Code.ERR_SYSTEM, null);
    }

    /**
     * 删除地址，根据唯一id
     *
     * @param userId 用户id
     * @param ids    地址id，可多条删除
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public Result deleteAddressByIds(
            @RequestHeader(value = "userId") String userId,
            @PathVariable Long... ids
    ) {
        boolean success = addressService.remove(Wrappers.<Address>lambdaQuery()
                .in(Address::getId, ids)
                .eq(Address::getUserId, userId));
        addressService.updateDefaultAddress(userId, -1L);
        return new Result(success ? Code.SUCCESS : Code.ERR_SYSTEM, null);
    }

    /**
     * 修改收货地址信息
     *
     * @param userId  用户id
     * @param id      地址id
     * @param address 修改信息
     * @return 结果
     */
    @PutMapping("/{id}")
    public Result updateAddressById(
            @RequestHeader(value = "userId") String userId,
            @PathVariable Long id,
            @RequestBody Address address
    ) {
        boolean success = addressService.update(address, Wrappers.lambdaUpdate(Address.class)
                .eq(Address::getUserId, userId)
                .eq(Address::getId, id));
        if (address.getDefaultAddress() == 1)
            addressService.updateDefaultAddress(userId, id);
        return new Result(success ? Code.SUCCESS : Code.ERR_SYSTEM, null);
    }
}
