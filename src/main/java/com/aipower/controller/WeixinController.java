package com.aipower.controller;

import com.aipower.domain.User;
import com.aipower.domain.WxPushMessage;
import com.aipower.helper.WeiXinHelper;
import com.aipower.service.UserService;
import com.aipower.utils.CommonUtil;
import com.aipower.utils.XmlUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
                              @RequestParam(value = "echostr", required = false) String echostr){
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
            if (u != null || !"subscribe".equals(event))
                return "";

            User user = new User();
            user.setName("用户");
            user.setUserId(CommonUtil.generateUserId(wxPushMessage.getFromUserName()));
            user.setGender(0);
            user.setWeixinToken(wxPushMessage.getFromUserName());
            user.setBirthday(new Date());
            userService.insertUser(user);
            String returnMsg = weiXinHelper.subscribe(wxPushMessage.getFromUserName(), wxPushMessage.getToUserName());
            System.out.println("--------------返回微信的消息----------------\n" + returnMsg);
            return returnMsg;
        }

        return "";
    }
}
