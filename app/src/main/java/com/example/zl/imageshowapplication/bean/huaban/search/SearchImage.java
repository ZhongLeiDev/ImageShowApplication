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
public class SearchImage {

    private String bucket;
    private String key;
    private int width;
    private int height;
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

    @Override
    public String toString() {
        return "SearchImage{" +
                "bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}