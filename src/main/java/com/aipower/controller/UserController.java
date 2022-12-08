package com.aipower.controller;

import com.aipower.domain.User;
import com.aipower.domain.WxPushMessage;
import com.aipower.helper.WeiXinHelper;
import com.aipower.service.UserService;
import com.aipower.utils.CommonUtil;
import com.aipower.utils.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/sys")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private WeiXinHelper weiXinHelper;

    @GetMapping("/users/{userId}")
    public User getUserByUserId(@PathVariable String userId) {
        return userService.getUserByUserId(userId);
    }

    @PostMapping("/wx")
    public String wxCallback(@RequestBody String body) {
        WxPushMessage wxPushMessage = (WxPushMessage) XmlUtil.fromXml(body, WxPushMessage.class);
        System.out.println("--------------微信推送的消息----------------\n" + wxPushMessage);
        if (wxPushMessage != null) {
            User user = new User();
            user.setName("用户");
            user.setUserId(CommonUtil.generateUserId(wxPushMessage.getFromUserName()));
            user.setGender(0);
            user.setWeixinToken(wxPushMessage.getFromUserName());
            user.setBirthday(new Date());
            userService.insertUser(user);
            return weiXinHelper.subscribe(wxPushMessage.getFromUserName(), wxPushMessage.getToUserName());
        }

        return "";
    }
}
