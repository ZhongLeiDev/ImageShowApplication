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
public class SearchAvatar {

    private long id;
    private String farm;
    private String bucket;
    private String key;
    private String type;
    private String height;
    private String width;
    private String frames;
    public void setId(long id) {
         this.id = id;
     }
     public long getId() {
         return id;
     }

    public void setFarm(String farm) {
         this.farm = farm;
     }
     public String getFarm() {
         return farm;
     }

    public void setBucket(String bucket) {
         this.bucket = bucket;
     }
     public String getBucket() {
         return bucket;
     }

    public void setKey(String key) {
         this.key = key;
     }
     public String getKey() {
         return key;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setHeight(String height) {
         this.height = height;
     }
     public String getHeight() {
         return height;
     }

    public void setWidth(String width) {
         this.width = width;
     }
     public String getWidth() {
         return width;
     }

    public void setFrames(String frames) {
         this.frames = frames;
     }
     public String getFrames() {
         return frames;
     }

    @Override
    public String toString() {
        return "DevidedAvatar{" +
                "id=" + id +
                ", farm='" + farm + '\'' +
                ", bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", height='" + height + '\'' +
                ", width='" + width + '\'' +
                ", frames='" + frames + '\'' +
                '}';
    }
}