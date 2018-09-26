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
public class DevidedCover {

    private String farm;
    private String bucket;
    private String key;
    private String type;
    private int width;
    private int height;
    private int frames;
    private long file_id;
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

    public void setWidth(int width) {
         this.width = width;
     }
     public int getWidth() {
         return width;
     }

    public void setHeight(int height) {
         this.height = height;
     }
     public int getHeight() {
         return height;
     }

    public void setFrames(int frames) {
         this.frames = frames;
     }
     public int getFrames() {
         return frames;
     }

    public void setFile_id(long file_id) {
         this.file_id = file_id;
     }
     public long getFile_id() {
         return file_id;
     }

    @Override
    public String toString() {
        return "DevidedCover{" +
                "farm='" + farm + '\'' +
                ", bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", frames=" + frames +
                ", file_id=" + file_id +
                '}';
    }
}