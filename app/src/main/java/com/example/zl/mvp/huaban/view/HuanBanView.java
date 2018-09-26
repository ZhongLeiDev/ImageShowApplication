package com.example.zl.mvp.huaban.view;

import com.example.zl.imageshowapplication.bean.huaban.transobj.HBImageBean;

/**
 * Created by ZhongLeiDev on 2018/9/26.
 * HuaBanImage 显示接口
 */

public interface HuanBanView {

    void onNext(HBImageBean bean);
    void onError(String error);
    void onCompleted(String complete);

}
