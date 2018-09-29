package com.example.zl.imageshowapplication.bean.huaban.transobj;

import java.io.Serializable;

/**
 * Created by ZhongLeiDev on 2018/9/26.
 * HuaBan image 转换类(可序列化)
 */

public class HBImageBean implements Serializable{

    private long pin_id;
    private long board_id;
    private String url;
    private int width;
    private int height;
    private String theme;

    public long getPin_id() {
        return pin_id;
    }

    public void setPin_id(long pin_id) {
        this.pin_id = pin_id;
    }

    public long getBoard_id() {
        return board_id;
    }

    public void setBoard_id(long board_id) {
        this.board_id = board_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "HBImageBean{" +
                "pin_id=" + pin_id +
                ", board_id=" + board_id +
                ", url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", theme='" + theme + '\'' +
                '}';
    }
}
