package com.example.zl.imageshowapplication.bean.huaban.loadmore;

import com.example.zl.imageshowapplication.bean.huaban.devided.DevidedPins;

import java.util.List;

/**
 * Created by ZhongLeiDev on 2018/9/28.
 * 加载更多所获取的 Json 数据转换为 Bean
 */

public class LoadMorePins {

    private List<DevidedPins> pins;

    public List<DevidedPins> getPins() {
        return pins;
    }

    public void setPins(List<DevidedPins> pins) {
        this.pins = pins;
    }

    @Override
    public String toString() {
        return "LoadMorePins{" +
                "pins=" + pins +
                '}';
    }
}
