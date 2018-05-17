package com.example.zl.imageshowapplication.message;

/**
 * Created by Administrator on 2018/5/15.
 */

public class BaseMessage {

    private MsgType msgType;
    private MsgEnums msg;
    private String extramsg;

    public BaseMessage(MsgType msgType, MsgEnums msg, String extramsg) {
        this.msgType = msgType;
        this.msg = msg;
        this.extramsg = extramsg;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public MsgEnums getMsg() {
        return msg;
    }

    public void setMsg(MsgEnums msg) {
        this.msg = msg;
    }

    public String getExtramsg() {
        return extramsg;
    }

    public void setExtramsg(String extramsg) {
        this.extramsg = extramsg;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "msgType=" + msgType +
                ", msg=" + msg +
                ", extramsg='" + extramsg + '\'' +
                '}';
    }
}
