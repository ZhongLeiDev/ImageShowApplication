package com.example.zl.imageshowapplication.bean.bcy;

/**
 * Created by ZhongLeiDev on 2018/5/17.
 * BCY 的 BcyPicture 类型，用于 BCY 的 Retrofit 方法
 */

public class BcyPicture {

    private String pictureUrl;

    private String pictureOrigurl;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureOrigurl() {
        return pictureOrigurl;
    }

    public void setPictureOrigurl(String pictureOrigurl) {
        this.pictureOrigurl = pictureOrigurl;
    }

    @Override
    public String toString() {
        return "BcyPicture{" +
                "pictureUrl='" + pictureUrl + '\'' +
                ", pictureOrigurl='" + pictureOrigurl + '\'' +
                '}';
    }
}
