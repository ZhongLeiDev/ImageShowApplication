package com.example.zl.imageshowapplication.bean.geek;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */

public class GeekResult {

    private String error;
    private List<GeekImgBean> results;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<GeekImgBean> getResults() {
        return results;
    }

    public void setResults(List<GeekImgBean> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GeekResult{" +
                "error='" + error + '\'' +
                ", results=" + results +
                '}';
    }
}
