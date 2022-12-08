package com.aipower.domain;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 微信推送过来的数据
 * 微信API ：https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_event_pushes.html#%E5%85%B3%E6%B3%A8/%E5%8F%96%E6%B6%88%E5%85%B3%E6%B3%A8%E4%BA%8B%E4%BB%B6
 */
@Root(name = "xml", strict = false)
public class WxPushMessage {

    //开发者微信号
    @Element(name = "ToUserName")
    private String ToUserName;
    //发送方帐号（一个OpenID）
    @Element(name = "FromUserName")
    private String FromUserName;
    //消息创建时间 （整型）
    @Element(name = "CreateTime", required = false)
    private Long CreateTime;
    //消息类型，event
    @Element(name = "Event", required = false)
    private String Event;
    //事件类型，subscribe
    @Element(name = "MsgType", required = false)
    private String MsgType;

    @Element(name = "CardId", required = false)
    private String CardId;

    @Element(name = "UserCardCode", required = false)
    private String UserCardCode;

    @Element(name = "ModifyBonus", required = false)
    private Integer ModifyBonus;
    //	事件KEY值，qrscene_为前缀，后面为二维码的参数值
    @Element(name = "EventKey", required = false)
    private String EventKey="";

    public void setEventKey(String eventKey) {
        if (eventKey.startsWith("qrscene_")){
            EventKey=eventKey.replace("qrscene_","");
        }else {
            EventKey = eventKey;
        }
    }
    public String getEventKey() {
        return EventKey;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public String getUserCardCode() {
        return UserCardCode;
    }

    public void setUserCardCode(String userCardCode) {
        UserCardCode = userCardCode;
    }

    public Integer getModifyBonus() {
        return ModifyBonus;
    }

    public void setModifyBonus(Integer modifyBonus) {
        ModifyBonus = modifyBonus;
    }

    @Override
    public String toString() {
        return "WxPushMessage{" +
                "ToUserName='" + ToUserName + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", CreateTime=" + CreateTime +
                ", Event='" + Event + '\'' +
                ", MsgType='" + MsgType + '\'' +
                ", CardId='" + CardId + '\'' +
                ", UserCardCode='" + UserCardCode + '\'' +
                ", ModifyBonus=" + ModifyBonus +
                ", EventKey='" + EventKey + '\'' +
                '}';
    }
}
