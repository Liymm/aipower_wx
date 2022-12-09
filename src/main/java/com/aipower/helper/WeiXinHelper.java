package com.aipower.helper;

import com.aipower.domain.Token;
import com.aipower.domain.WxSendData;
import com.aipower.utils.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class WeiXinHelper {
    // 凭证获取（GET）
    public final String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //发送消息
    public final String news_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    //请求ticket
    public final String ticket_url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
    //请求二维码
    public final String QRcode_url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
    //网页授权后获取oppenid
    public final String page_oppenid = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    private static final Logger log = LoggerFactory.getLogger(WeiXinHelper.class);
    static String appid = "wx7d97660308fba004";
    static String secret = "7110ee10356b3e20f7f94f6546ac175d";

    public static String getAppid() {
        return appid;
    }

    public static void setAppid(String appid) {
        WeiXinHelper.appid = appid;
    }

    public static String getSecret() {
        return secret;
    }

    public static void setSecret(String secret) {
        WeiXinHelper.secret = secret;
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送消息
     *
     * @param data
     * @return
     */
    public boolean send(Map<String, Object> data) {
        String token = getTokenBySession();
        if (null != token && !"".equals(token)) {
            String requestUrl = news_url.replace("ACCESS_TOKEN", token);
            String toJSONString = JSONObject.toJSONString(data);
            log.info("***************发消息的参数******************{}", toJSONString);
            String doPostSSLResult = HttpClientUtil.doPostSSL(requestUrl, toJSONString);
            log.info("***************发送消息******************{}", doPostSSLResult);
            JSONObject jsonObject = JSONObject.parseObject(doPostSSLResult);
            if (null != jsonObject) {
                return true;
            }
            return false;
        } else {
            log.error("WxHelper.send() 中 token 为空");
            return false;
        }
    }

    /**
     * 关注微信后推送信息
     *
     * @param fromusername 用户openid
     * @param tousername   微信号id
     * @return
     */
    public String subscribe(String fromusername, String tousername) {
        return this.text(fromusername, tousername, "您的关注，是对我司产品的最大信任")
                .toString();
    }

    private StringBuffer text(String fromusername, String tousername, String content) {
        //单位为秒，不是毫秒
        Long createTime = new Date().getTime() / 1000;
        StringBuffer text = new StringBuffer();
        text.append("<xml>");
        text.append("<ToUserName><![CDATA[" + fromusername + "]]></ToUserName>");
        text.append("<FromUserName><![CDATA[" + tousername + "]]></FromUserName>");
        text.append("<CreateTime><![CDATA[" + createTime + "]]></CreateTime>");
        text.append("<MsgType><![CDATA[text]]></MsgType>");
        text.append("<Content><![CDATA[" + content + "]]></Content>");
        text.append("</xml>");
        return text;
    }

    /**
     * 生成二维码(关注公众号)
     * @return
     */
//    public String  makeQRcodeByUuid(Integer uuid){
//        String tokenBySession = getTokenBySession();
//        if(null != tokenBySession && !"".equals(tokenBySession)){
//            //phone  需要跟 微信关联
//            //封装二维码信息
//            QRcodeDto qRcode = new QRcodeDto();
//            qRcode.setExpire_seconds(7200l);
//            //二维码类型
//            qRcode.setAction_name("QR_STR_SCENE");
//            Map<String, Map<String, Object>> mapMap = new HashMap<>();
//            Map<String, Object> map = new HashMap<>();
//            map.put("scene_str", uuid);
//            mapMap.put("scene", map);
//            qRcode.setAction_info(mapMap);
//            //Map<String, Object> params = JSON.parseObject(JSON.toJSONString(qRcode), Map.class);
//
//            //去获二维码地址
//            QRcodeDto qRcodeDto = getTicket(tokenBySession, JSON.toJSONString(qRcode));
//            //根据二维码地址去保存到本地 并返回本地二维码地址
//            String qRcode2 = getQRcode(qRcodeDto.getTicket());
//            return qRcode2;
//        }else {
//            return "token为空！请检查对接微信平台的方法！";
//        }
//    }

    /**
     * 获取下载二维码地址
     *
     * @param ticket
     * @return
     */
    private String getQRcode(String ticket) {
        String requestUrl = QRcode_url.replace("TICKET", ticket);
        String download = downloadByNet(requestUrl, ticket);
        return download;
    }

    /**
     * 获取Ticket来换取二维码
     * @param tokenBySession
     * @param params
     * @return
     */
//    private QRcodeDto getTicket(String tokenBySession, String params) {
//        QRcodeDto qRcode = null;
//        String requestUrl = ticket_url.replace("ACCESS_TOKEN", tokenBySession);
//        String doPostResult = HttpClientUtil.doPostSSL(requestUrl,params);
//        //String doPostResult = HttpClientUtil.doPost(requestUrl, params);
//        log.info("************获取Ticket来换取二维码***********,{}",doPostResult);
//        JSONObject jsonObject = JSONObject.parseObject(doPostResult);
//        if (null != jsonObject) {
//            try {
//                return JSON.parseObject(doPostResult,QRcodeDto.class);
//            } catch (JSONException e) {
//                qRcode = null;
//                // 获取Ticket失败
//                log.error("获取Ticket失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
//            }
//        }
//        return qRcode;
//    }

    /**
     * 获取 微信 接口的  token（session   session中没有再去微信服务器请求）
     *
     * @return
     */
    public String getTokenBySession() {
        String token = "";
        //去redis中获取token
        token = redisTemplate.opsForValue().get("wx_token");
        System.out.println("token===>" + token);
        if (token != null && !"".equals(token)) {
            return token;
        } else {
            Token tokenDto = getTokenByAppidAndAppsecret();
            System.out.println("tokenDto===" + tokenDto);
            redisTemplate.opsForValue().set("wx_token", tokenDto.getAccessToken(), 5, TimeUnit.MINUTES);
            return tokenDto.getAccessToken();
        }
    }

    /**
     * 直接获取token  ---->>   微信服务器
     * token 失效时间 7100
     *
     * @return
     */
    private Token getTokenByAppidAndAppsecret() {
        Token token = null;
        System.out.println("appid===>" + appid);
        String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", secret);
        // 发起GET请求获取凭证
        String result = HttpClientUtil.doGet(requestUrl);
        JSONObject jsonObject = JSONObject.parseObject(result);
        log.info("获取Token返回结果----->;WxHelper-getTokenByAppidAndAppsecret:" + jsonObject);
        if (null != jsonObject) {
            token = new Token();
            String access_token = jsonObject.getString("access_token");
            System.out.println("access_token===>" + access_token);
            if (null != access_token && !"".equals(access_token)) {
                token.setAccessToken(access_token);
                token.setExpiresIn(jsonObject.getIntValue("expires_in"));
            } else {
                token = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }

    /**
     * 下载二维码
     *
     * @param requestUrl
     * @param ticket
     * @return
     */
    private static String downloadByNet(String requestUrl, String ticket) {
        // 构造URL
        URL url = null;
        try {
            url = new URL(requestUrl);
            // 打开连接
            URLConnection con = url.openConnection();
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[4056];
            // 读取到的数据长度
            int len;
            long l = System.currentTimeMillis();
            // 输出的文件流
            String filename = "qrcode" + "/qr/" + l + ".jpg";  //下载路径及下载图片名称

            File files = new File("qrcode" + "/qr");
            if (!files.exists()) {
                files.mkdirs();// 创建文件根目录
            }
            File file = new File(filename);
            FileOutputStream os = new FileOutputStream(file, true);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
            return "/home/www/VX/qr/" + l + ".jpg";
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, Object> makeWxPostMsg(String first, String remark, String[] keyword) {
        Map<String, Object> data = new HashMap<>();
        data.put("first", new WxSendData(first));
        for (int i = 1; i <= keyword.length; i++) {
            data.put("keyword" + i, new WxSendData(keyword[i - 1]));
        }
        data.put("remark", new WxSendData(remark));
        return data;
    }
}
