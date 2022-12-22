package com.aipower.controller;

import com.aipower.domain.Order;
import com.aipower.domain.User;
import com.aipower.domain.WxPushMessage;
import com.aipower.helper.WeiXinHelper;
import com.aipower.service.UserService;
import com.aipower.utils.CommonUtil;
import com.aipower.utils.XmlUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class WeixinController {
    @Autowired
    private UserService userService;

    @Autowired
    private WeiXinHelper weiXinHelper;

    @GetMapping("/wx")
    public String wxSignature(@RequestParam(value = "signature", required = false) String signature,
                              @RequestParam(value = "timestamp", required = false) String timestamp,
                              @RequestParam(value = "nonce", required = false) String nonce,
                              @RequestParam(value = "echostr", required = false) String echostr) {
        System.out.println(" 微信服务端返回的数据 用于校验 微信配置中心的网址是否正确: >>>" + signature + "\t" + timestamp + "\t" + nonce + "\t" + echostr);
        System.out.println("开始签名验证：" + " PARAM VAL: >>>" + signature + "\t" + timestamp + "\t" + nonce + "\t" + echostr);
        if (StringUtils.isNotEmpty(signature) && StringUtils.isNotEmpty(timestamp)
                && StringUtils.isNotEmpty(nonce) && StringUtils.isNotEmpty(echostr)) {
            String sTempStr = "";
            if (StringUtils.isNotEmpty(signature)) {
                System.out.println("验证成功：-----------：" + sTempStr);
                return echostr;
            } else {
                System.out.println("验证失败：-----------：00000");
                return "-1";
            }
        } else {
            System.out.println("验证失败：-----------：11111");
            return "-1";
        }
    }

    @PostMapping("/wx")
    public String wxCallback(@RequestBody String body) {
        WxPushMessage wxPushMessage = (WxPushMessage) XmlUtil.fromXml(body, WxPushMessage.class);
        System.out.println("--------------微信推送的消息----------------\n" + wxPushMessage);
        if (wxPushMessage != null) {
            String fromUserName = wxPushMessage.getFromUserName();
            // 事件类型，subscribe->订阅  unsubscribe取消
            String event = wxPushMessage.getEvent();
            User u = userService.getUserByWXToken(fromUserName);
            String returnMsg = weiXinHelper.subscribe(wxPushMessage.getFromUserName(), wxPushMessage.getToUserName());
            if (u != null) {
                if ("subscribe".equals(event))
                    return returnMsg;
                return "";
            }

            User user = new User();
            user.setName("用户");
            user.setUserId(CommonUtil.generateUserId(wxPushMessage.getFromUserName()));
            user.setGender(0);
            user.setWeixinToken(wxPushMessage.getFromUserName());
            user.setBirthday(new Date());
            userService.insertUser(user);
            System.out.println("--------------返回微信的消息----------------\n" + returnMsg);
            return returnMsg;
        }

        return "";
    }

    @PostMapping("/template/pay")
    public Result sendWxPayMsg(@RequestHeader("userId") String userId,
                               @RequestBody Order order) {
        User user = userService.getUserByUserId(userId);
        Map<String, Object> sendMsg = new HashMap<>();

        String toUserToken = user.getWeixinToken();
        sendMsg.put("touser", toUserToken);
        sendMsg.put("template_id", "Rg7zG7YBcr4MeUhdipnf6BvYvedFuDd2yJ-KrWkORck");

        Map<String, Object> map = WeiXinHelper.makeWxPostMsg(
                "您有一个未付款订单，请尽快支付",
                "点击查看详情",
                new String[]{
                        order.getProductName(),
                        order.getCouponPrice() + "元",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateTime()),
                        order.getQuantity() + ""
                });
        sendMsg.put("data", map);
        sendMsg.put("url","https://shop.myaipower.com");
        weiXinHelper.send(sendMsg);
        return new Result(Code.SUCCESS, sendMsg);
    }

}
