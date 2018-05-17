package com.example.zl.imageshowapplication.message;

/**
 * Created by Administrator on 2018/5/15.
 */

public enum MsgEnums {
    NET_WIFI_CONNECTED(0,"WiFi已连接"),
    NET_MOBILE_CONNECTED(1,"移动网络已连接"),
    NET_DISCONNECTED(2,"网络已断开"),
    ;

    private int code;
    private String msg;

    MsgEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



}
