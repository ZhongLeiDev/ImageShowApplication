package com.example.zl.locallogin.localenum;

/**
 * Created by ZhongLeiDev on 2019/4/3.
 * 本地方法 CODE 枚举
 */
public enum LocalEnum {
    /**网络连接错误*/
    NET_ERROR(-2, "网络连接错误！"),
    /**注册信息不完整*/
    REGISTER_INFO_MISSING(-3, "注册信息不完整！"),
    /**登录信息不完整*/
    LOGIN_INFO_MISSING(-4, "登录信息不完整！"),
    /**头像信息不完整*/
    AVATAR_INFO_MISSING(-5, "当前账号未登录或头像文件未选择！"),
    /**当前用户为空*/
    CURRENTUSER_NULL(-6, "当前用户为空！"),
    /**待分享的图像列表为空*/
    SHARED_LIST_NULL(-7, "待分享的图片列表为空！"),
    /**未捕获的异常*/
    UNCATCHED_ERROR(-8, "服务器出现未捕获的异常！");
    ;

    private int code;
    private String msg;

    LocalEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
