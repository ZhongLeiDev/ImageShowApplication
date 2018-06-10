package com.example.zl.imageshowapplication.bean.bcy.retro;

import java.io.Serializable;

/**
 * 图片信息类
 */
public class PictureInfo implements Serializable{

    private String pictureId;

    private String pictureAlbumid;

    private String pictureUrl;

    private String pictureOrigurl;

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureAlbumid() {
        return pictureAlbumid;
    }

    public void setPictureAlbumid(String pictureAlbumid) {
        this.pictureAlbumid = pictureAlbumid;
    }

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
        return "PictureInfo{" +
                "pictureId='" + pictureId + '\'' +
                ", pictureAlbumid='" + pictureAlbumid + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", pictureOrigurl='" + pictureOrigurl + '\'' +
                '}';
    }
}
