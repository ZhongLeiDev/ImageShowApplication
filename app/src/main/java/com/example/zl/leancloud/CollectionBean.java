package com.example.zl.leancloud;

import java.io.Serializable;

/**
 * Created by ZhongLeiDev on 2018/10/9.
 * Collection Bean
 */

public class CollectionBean implements Serializable{

    private String url;
    private String cover;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "CollectionBean{" +
                "url='" + url + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
