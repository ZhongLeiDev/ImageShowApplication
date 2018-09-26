/**
  * Copyright 2018 bejson.com 
  */
package com.example.zl.imageshowapplication.bean.huaban.devided;
import java.util.List;

/**
 * Auto-generated: 2018-09-26 8:41:48
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class DevidedFile {

    private long id;
    private String farm;
    private String bucket;
    private String key;
    private String type;
    private int width;
    private int height;
    private int frames;
    private List<DevidedColors> colors;
    private String theme;
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

    public void setColors(List<DevidedColors> colors) {
         this.colors = colors;
     }
     public List<DevidedColors> getColors() {
         return colors;
     }

    public void setTheme(String theme) {
         this.theme = theme;
     }
     public String getTheme() {
         return theme;
     }

    @Override
    public String toString() {
        return "DevidedFile{" +
                "id=" + id +
                ", farm='" + farm + '\'' +
                ", bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", frames=" + frames +
                ", colors=" + colors +
                ", theme='" + theme + '\'' +
                '}';
    }
}