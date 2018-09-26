package com.example.zl.imageshowapplication.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by ZhongLeiDev on 2018/9/26.
 * 一般通用工具
 */
public class CommonUtil {

    /**
     * 获取屏幕的大小
     */
    public static Point getScreenSize(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

}
