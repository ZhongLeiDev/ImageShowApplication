package com.example.zl.imageshowapplication.myinterface;

import android.view.View;

/**
 * Created by ZhongLeiDev on 2018/05/17
 * 自定义RecycleView的Item点击事件接口
 */
public interface OnMyItemClickListener {

    void myClick(View v,int pos);
    void mLongClick(View v, int pos);

}
