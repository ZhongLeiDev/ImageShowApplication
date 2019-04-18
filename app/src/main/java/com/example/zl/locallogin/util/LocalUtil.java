package com.example.zl.locallogin.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.example.zl.locallogin.bean.ISUser;

/**
 * Created by ZhongLeiDev on 2019/4/4.
 * 工具类
 */
public class LocalUtil {

    private LocalUtil() { }

    /**
     * 持久化存储 User 对象
     * @param user
     */
    public static void saveCurrentUser(ISUser user, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("token", user.getToken());
        edit.putString("userId", user.getUserId());
        edit.putString("userName", user.getUserName());
        edit.putString("mail", user.getMail());
        edit.putString("avatarUrl", user.getAvatarUrl());
        edit.apply();
    }

    /**
     * User 对象读取
     * @param context
     * @return
     */
    public static ISUser getCurrentUser(Context context) {
        ISUser user = new ISUser();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        user.setToken(prefs.getString("token", ""));
        user.setUserId(prefs.getString("userId", ""));
        user.setUserName(prefs.getString("userName", ""));
        user.setMail(prefs.getString("mail", ""));
        user.setAvatarUrl(prefs.getString("avatarUrl", ""));
        return user;
    }

}
