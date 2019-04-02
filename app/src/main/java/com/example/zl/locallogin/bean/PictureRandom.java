package com.example.zl.locallogin.bean;

/**
 * Created by ZhongLeiDev on 2019/4/2.
 * 图片随机访问存储类
 */
public class PictureRandom {

    private String picId;

    private String picUrl;

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "PictureRandom{" +
                "picId='" + picId + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }
}
