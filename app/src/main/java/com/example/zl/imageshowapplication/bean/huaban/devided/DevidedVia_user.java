/**
  * Copyright 2018 bejson.com 
  */
package com.example.zl.imageshowapplication.bean.huaban.devided;

/**
 * Auto-generated: 2018-09-26 8:41:48
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class DevidedVia_user {

    private long user_id;
    private String username;
    private String urlname;
    private long created_at;
    private DevidedAvatar avatar;
    private DevidedVia_user_extra extra;
    public void setUser_id(long user_id) {
         this.user_id = user_id;
     }
     public long getUser_id() {
         return user_id;
     }

    public void setUsername(String username) {
         this.username = username;
     }
     public String getUsername() {
         return username;
     }

    public void setUrlname(String urlname) {
         this.urlname = urlname;
     }
     public String getUrlname() {
         return urlname;
     }

    public void setCreated_at(long created_at) {
         this.created_at = created_at;
     }
     public long getCreated_at() {
         return created_at;
     }

    public void setAvatar(DevidedAvatar avatar) {
         this.avatar = avatar;
     }
     public DevidedAvatar getAvatar() {
         return avatar;
     }

    public void setExtra(DevidedVia_user_extra extra) {
         this.extra = extra;
     }
     public DevidedVia_user_extra getExtra() {
         return extra;
     }

    @Override
    public String toString() {
        return "DevidedVia_user{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", urlname='" + urlname + '\'' +
                ", created_at=" + created_at +
                ", avatar=" + avatar +
                ", extra='" + extra + '\'' +
                '}';
    }
}