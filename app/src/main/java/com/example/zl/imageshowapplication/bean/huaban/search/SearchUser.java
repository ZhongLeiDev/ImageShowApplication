/**
  * Copyright 2018 bejson.com 
  */
package com.example.zl.imageshowapplication.bean.huaban.search;

/**
 * Auto-generated: 2018-09-26 8:13:40
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SearchUser {

    private long user_id;
    private String username;
    private String urlname;
    private long created_at;
    private SearchAvatar avatar;
    private Search_user_extra extra;
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

    public void setAvatar(SearchAvatar avatar) {
         this.avatar = avatar;
     }
     public SearchAvatar getAvatar() {
         return avatar;
     }

    public void setExtra(Search_user_extra extra) {
         this.extra = extra;
     }
     public Search_user_extra getExtra() {
         return extra;
     }

    @Override
    public String toString() {
        return "SearchUser{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", urlname='" + urlname + '\'' +
                ", created_at=" + created_at +
                ", avatar=" + avatar +
                ", extra='" + extra + '\'' +
                '}';
    }
}