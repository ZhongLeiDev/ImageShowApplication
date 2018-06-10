package com.example.zl.imageshowapplication.bean.bcy.retro;

import java.io.Serializable;

/**
 * 相册信息类
 */
public class AlbumInfo implements Serializable{

    private String albumId;

    private String albumCover;

    private String albumAuthor;

    private String albumTag;

    private String albumName;

    private String albumDescription;

    private String albumUrl;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

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
        return "AlbumInfo{" +
                "albumId='" + albumId + '\'' +
                ", albumCover='" + albumCover + '\'' +
                ", albumAuthor='" + albumAuthor + '\'' +
                ", albumTag='" + albumTag + '\'' +
                ", albumName='" + albumName + '\'' +
                ", albumDescription='" + albumDescription + '\'' +
                ", albumUrl='" + albumUrl + '\'' +
                '}';
    }

}
