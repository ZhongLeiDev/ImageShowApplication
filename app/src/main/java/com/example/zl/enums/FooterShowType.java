package com.example.zl.enums;

/**
 * Created by ZhongLeiDev on 2018/9/29.
 * 加载更多模块的显示样式
 */

public enum FooterShowType {
    /**数据正常显示状态，footer 不显示*/
    NORMAL,
    /**数据正在加载状态*/
    LOADING,
    /**数据加载结束状态,没有更多数据*/
    END,
    /**数据加载出错状态*/
    ERROR
}
