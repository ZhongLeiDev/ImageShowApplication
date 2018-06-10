package com.example.zl.imageshowapplication.bean.bcy;

/**
 * Created by ZhongLeiDev on 2018/5/17.
 * BCY 的 BcyAlbum 类型，用于 BCY 的 Retrofit 方法
 */

public class BcyAlbum {

    private String albumCover;

    private String albumAuthor;

    private String albumTag;

    private String albumName;

    private String albumDescription;

    private String albumUrl;

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }

    public String getAlbumAuthor() {
        return albumAuthor;
    }

    public void setAlbumAuthor(String albumAuthor) {
        this.albumAuthor = albumAuthor;
    }

    public String getAlbumTag() {
        return albumTag;
    }

    public void setAlbumTag(String albumTag) {
        this.albumTag = albumTag;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumDescription() {
        return albumDescription;
    }

    public void setAlbumDescription(String albumDescription) {
        this.albumDescription = albumDescription;
    }

    public String getAlbumUrl() {
        return albumUrl;
    }

    public void setAlbumUrl(String albumUrl) {
        this.albumUrl = albumUrl;
    }

    @Override
    public String toString() {
        return "BcyAlbum{" +
                "albumCover='" + albumCover + '\'' +
                ", albumAuthor='" + albumAuthor + '\'' +
                ", albumTag='" + albumTag + '\'' +
                ", albumName='" + albumName + '\'' +
                ", albumDescription='" + albumDescription + '\'' +
                ", albumUrl='" + albumUrl + '\'' +
                '}';
    }
}
