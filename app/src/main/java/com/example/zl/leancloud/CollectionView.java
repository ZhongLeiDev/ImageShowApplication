package com.example.zl.leancloud;

import java.util.List;

/**
 * Created by ZhongLeiDev on 2018/10/9.
 * 收藏功能的显示接口
 */

public interface CollectionView {

    /**收藏出错*/
    void onError(String error);
    /**收藏成功*/
    void onSucceed();
    /**收藏查询完毕*/
    void onQueryCompleted(List<CollectionBean> beanList);

}
