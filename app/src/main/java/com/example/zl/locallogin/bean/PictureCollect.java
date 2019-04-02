package com.example.zl.locallogin.bean;

/**
 * Created by ZhongLeiDev on 2019/4/2.
 * 图片收藏存储类
 */
public class PictureCollect {

    private String userId;
    private String userName;
    private String collectUrl;
    private String cover;

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

    public String getCollectUrl() {
        return collectUrl;
    }

    public void setCollectUrl(String collectUrl) {
        this.collectUrl = collectUrl;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "PictureCollect{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", collectUrl='" + collectUrl + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
