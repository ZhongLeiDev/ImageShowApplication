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
public class DevidedExplores {

    private int keyword_id;
    private String name;
    private String urlname;
    private DevidedCover cover;
    private String description;
    private String recommended_users;
    private String creator_id;
    private long created_at;
    private int is_hidden;
    private DevidedTags tags;
    private String theme;
    public void setKeyword_id(int keyword_id) {
         this.keyword_id = keyword_id;
     }
     public int getKeyword_id() {
         return keyword_id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setUrlname(String urlname) {
         this.urlname = urlname;
     }
     public String getUrlname() {
         return urlname;
     }

    public void setCover(DevidedCover cover) {
         this.cover = cover;
     }
     public DevidedCover getCover() {
         return cover;
     }

    public void setDescription(String description) {
         this.description = description;
     }
     public String getDescription() {
         return description;
     }

    public void setRecommended_users(String recommended_users) {
         this.recommended_users = recommended_users;
     }
     public String getRecommended_users() {
         return recommended_users;
     }

    public void setCreator_id(String creator_id) {
         this.creator_id = creator_id;
     }
     public String getCreator_id() {
         return creator_id;
     }

    public void setCreated_at(long created_at) {
         this.created_at = created_at;
     }
     public long getCreated_at() {
         return created_at;
     }

    public void setIs_hidden(int is_hidden) {
         this.is_hidden = is_hidden;
     }
     public int getIs_hidden() {
         return is_hidden;
     }

    public void setTags(DevidedTags tags) {
         this.tags = tags;
     }
     public DevidedTags getTags() {
         return tags;
     }

    public void setTheme(String theme) {
         this.theme = theme;
     }
     public String getTheme() {
         return theme;
     }

    @Override
    public String toString() {
        return "DevidedExplores{" +
                "keyword_id=" + keyword_id +
                ", name='" + name + '\'' +
                ", urlname='" + urlname + '\'' +
                ", cover=" + cover +
                ", description='" + description + '\'' +
                ", recommended_users='" + recommended_users + '\'' +
                ", creator_id='" + creator_id + '\'' +
                ", created_at=" + created_at +
                ", is_hidden=" + is_hidden +
                ", tags=" + tags +
                ", theme='" + theme + '\'' +
                '}';
    }
}