package com.example.zl.locallogin.bean;

/**
 * Created by ZhongLeiDev on 2019/4/2.
 * 分享图片存储类
 */
public class PictureShared {

    private String userId;
    private String userName;
    private String sharedUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSharedUrl() {
        return sharedUrl;
    }

    public void setSharedUrl(String sharedUrl) {
        this.sharedUrl = sharedUrl;
    }

    @Override
    public String toString() {
        return "PictureShared{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", sharedUrl='" + sharedUrl + '\'' +
                '}';
    }
}
