package com.example.zl.locallogin.bean;

/**
 * Created by ZhongLeiDev on 2019/4/2.
 * 定义异常存储类
 */
public class LocalError {

    private int code;
    private String msg;

    public LocalError(int code, String msg) {
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

    @Override
    public String toString() {
        return "LocalError{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
