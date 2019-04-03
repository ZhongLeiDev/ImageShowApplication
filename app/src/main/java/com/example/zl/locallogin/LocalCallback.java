package com.example.zl.locallogin;

import com.example.zl.locallogin.bean.LocalError;

/**
 * Created by ZhongLeiDev on 2019/4/2.
 * 本地回调类
 */
public abstract class LocalCallback<T> {

    public LocalCallback() {

    }

    /**回调操作*/
    public abstract void done(T t, LocalError e);

}
