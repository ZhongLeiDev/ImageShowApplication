package com.example.zl.imageshowapplication.bean.huaban.search;

/**
 * Created by ZhongLeiDev on 2018/9/29.
 * Bord 中的 Extra 属性
 */

public class Search_board_extra {

    private Search_board_cover cover;

    public Search_board_cover getCover() {
        return cover;
    }

    public void setCover(Search_board_cover cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Search_board_extra{" +
                "cover=" + cover +
                '}';
    }

}
