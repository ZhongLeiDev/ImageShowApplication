package com.example.zl.imageshowapplication.myinterface;

import android.view.View;

/**
 * 自定义RecycleView的Item点击事件接口
 */
public interface OnMyItemClickListener {

    void myClick(View v,int pos);
    void mLongClick(View v, int pos);

}
