package com.example.zl.imageshowapplication.adapter.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.zl.imageshowapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/10/15.
 * 其它设置
 */

public class ViewHolderOther extends SettingsViewHolder {

    @Bind(R.id.view_holder_other_version)
    public LinearLayout versionLayout;
    @Bind(R.id.view_holder_other_about)
    public LinearLayout aboutLayout;

    public ViewHolderOther(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);

    }

    /**
     * 设置版本检查点击事件
     * @param listener
     */
    public void setOnVersionClick(View.OnClickListener listener) {
        versionLayout.setOnClickListener(listener);
    }

    /**
     * 设置关于点击事件
     * @param listener
     */
    public void setOnAboutClick(View.OnClickListener listener) {
        aboutLayout.setOnClickListener(listener);
    }

    public static ViewHolderOther create(ViewGroup parent) {
        return new ViewHolderOther(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.settings_viewholder_others,parent,false));
    }

}
