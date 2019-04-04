package com.example.zl.locallogin.bean;

/**
 * Created by ZhongLeiDev on 2019/4/2.
 * ImageShowApplication 用户实例
 */
public class ISUser {

    private String token;
    private String userId;
    private String userName;
    private String mail;
    private String avatarUrl;

    public ISUser() { }

    public ISUser(String token, String userId, String userName, String mail, String avatarUrl) {
        this.token = token;
        this.userId = userId;
        this.userName = userName;
        this.mail = mail;
        this.avatarUrl = avatarUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "ISUser{" +
                "token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", mail='" + mail + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
