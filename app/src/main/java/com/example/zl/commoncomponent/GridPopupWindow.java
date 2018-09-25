package com.example.zl.commoncomponent;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.zl.imageshowapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhongLeiDev on 2018/9/21.
 * 自定义九宫格模式的 PopupWindow
 */

public class GridPopupWindow {

    private PopupWindow popupWindow;
    private Context mContext;
    private GridView gridView;
    public SimpleAdapter adapter;
    public List<Map<String, Object>> dataList;
    private View contentView;

    private int[] icon;
    private String[] icon_name;

    /**
     * 构造函数
     * @param ctx 上下文
     * @param icons 显示图标资源
     * @param icon_names 显示图标名称
     */
    public GridPopupWindow(Context ctx, int[] icons, String[] icon_names) {
        this.mContext = ctx;
        this.icon = icons;
        this.icon_name = icon_names;
        init();
    }

    private void init() {
        // 一个自定义的布局，作为显示的内容
        contentView = LayoutInflater.from(mContext).inflate(
                R.layout.popupwindow_layout, null);

        gridView = contentView.findViewById(R.id.popup_grid_view);

        //初始化数据
        initData();

        String[] from = {"img", "text"};
        int[] to = {R.id.popup_item_imgview, R.id.popup_item_textview};

        adapter = new SimpleAdapter(mContext, dataList, R.layout.popup_item, from, to);

        gridView.setAdapter(adapter);

        // 设置按钮的点击事件
        Button button = contentView.findViewById(R.id.popup_btn_cancel);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "cancel is pressed",
                        Toast.LENGTH_SHORT).show();
                popupDismiss();
            }
        });

        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.popupwindow_anim);
        popupWindow.setTouchable(true);


    }

    private void initData() {
        dataList = new ArrayList<>();
        for (int i=0; i<icon.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", icon[i]);
            map.put("text", icon_name[i]);
            dataList.add(map);
        }

    }

    /**
     * 设置事件监听
     * @param listener
     */
    public void setOnGridViewItemClick(AdapterView.OnItemClickListener listener) {

        gridView.setOnItemClickListener(listener);

    }

    /**
     * 显示 popupWindow
     */
    public void popupShow() {
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.5f);
    }

    /**
     * popupWindow 消失
     */
    public void popupDismiss() {
        popupWindow.dismiss();
        backgroundAlpha(1.0f);
    }

    /**
     * 设置屏幕变暗
     * @param bgAlpha 取值范围 0.0~1.0，取值 1.0 时 恢复正常
     */
    private void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity)mContext).getWindow().setAttributes(lp);
    }


}
