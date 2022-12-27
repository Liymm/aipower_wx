package com.aipower.controller;

import com.aipower.domain.Address;
import com.aipower.service.AddressService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
     * 获取该用户所有收货地址
     *
     * @param uuid 用户id
     * @return 匹配的收货地址列表
     */
    @RequestMapping("/list/{uuid}")
    public Result getAllList(@PathVariable String uuid) {
        System.out.println("userId==="+uuid);
        List<Address> addressList = addressService.list(Wrappers.<Address>lambdaQuery().eq(Address::getUserId, uuid));
        return new Result(Code.SUCCESS, addressList);
    }

    /**
     * 获取单个地址
     * @param uuid
     * @return
     */
    @RequestMapping("/one/{uuid}")
    public Result getOneByUuid(@PathVariable String uuid){
        System.out.println("uuid=="+uuid);
        Address address = addressService.selectOneByUserId(uuid);
        return new Result(Code.SUCCESS, address);
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

    @PostMapping("/select/{id}")
    public Result selectById(@PathVariable Integer id){
        System.out.println("id=="+id);
        Address byId = addressService.getById(id);
        return new Result(Code.SUCCESS,byId);
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

    @RequestMapping("/v1/update/{index}")
    public Result updateByIndex(@PathVariable Integer index,
                                      @RequestBody Map<String,Object> params){
        System.out.println("index==="+index);//用户唯一标识
        System.out.println("params==="+params);
        String name= (String) params.get("name");//用户名称
        String phone= (String) params.get("phone");//用户手机号
        String province= (String) params.get("province");//省
        String city= (String) params.get("city");//市
        String county= (String) params.get("county");//区
        String addressDetail= (String) params.get("addressDetail");//详细地址
        String group= (String) params.get("group");//分组是否为默认收货地址 0不/1是
        String postalCode= (String) params.get("postalCode");//邮政编码，可不存数据库可不获取
        String areaCode= (String) params.get("areaCode");//地区值

        Address byId = addressService.getById(index);
        byId.setName(name);
        byId.setPhone(phone);
        byId.setAddress(province+city+county+addressDetail);
        byId.setDefaultAddress(Integer.valueOf(group));
        byId.setAreaCode(Integer.valueOf(areaCode));
        addressService.updateById(byId);
        return new Result(Code.SUCCESS,null);
    }

    @RequestMapping("/v1/add/one/{uuid}")
    public Result addByUserId(@PathVariable String uuid,
                              @RequestBody Map<String,Object> params){
        System.out.println("uuid==="+uuid);//用户唯一标识
        System.out.println("params==="+params);
        String name= (String) params.get("name");//用户名称
        String phone= (String) params.get("phone");//用户手机号
        String province= (String) params.get("province");//省
        String city= (String) params.get("city");//市
        String county= (String) params.get("county");//区
        String addressDetail= (String) params.get("addressDetail");//详细地址
        String group= (String) params.get("group");//分组是否为默认收货地址 0不/1是
        String postalCode= (String) params.get("postalCode");//邮政编码，可不存数据库可不获取
        String areaCode= (String) params.get("areaCode");//地区值

        Address address = new Address();
        address.setUserId(uuid);
        address.setAddress(province+city+county+addressDetail);
        address.setPhone(phone);
        address.setName(name);
        address.setDefaultAddress(Integer.valueOf(group));
        address.setAreaCode(Integer.valueOf(areaCode));
        addressService.save(address);

        return new Result(Code.SUCCESS,null);
    }

    @RequestMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        boolean success = addressService.removeById(id);
        return new Result(success ? Code.SUCCESS : Code.ERR_SYSTEM, null);
    }
}
