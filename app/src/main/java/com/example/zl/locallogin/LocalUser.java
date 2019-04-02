package com.example.zl.locallogin;

import com.example.zl.locallogin.bean.ISUser;

/**
 * Created by ZhongLeiDev on 2019/4/2.
 * 用户数据方法类
 */
public class LocalUser {

    private static ISUser currentUser;

    public static void setCurrentUser(ISUser user) {
        currentUser = user;
    }

    public static ISUser currentUser() {
        return currentUser;
    }

}
