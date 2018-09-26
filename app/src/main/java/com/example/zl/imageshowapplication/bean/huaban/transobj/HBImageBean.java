package com.example.zl.imageshowapplication.bean.huaban.transobj;

/**
 * Created by ZhongLeiDev on 2018/9/26.
 * HuaBan image 转换类
 */

public class HBImageBean {

    private long board_id;
    private String url;
    private int width;
    private int height;
    private String theme;


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
                "board_id=" + board_id +
                ", url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", theme='" + theme + '\'' +
                '}';
    }
}
