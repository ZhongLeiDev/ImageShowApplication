package com.example.zl.imageshowapplication.adapter.settings;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zl.commoncomponent.ToggleButton;
import com.example.zl.imageshowapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/10/15.
 * 设置项
 */

public class ViewHolderSetting extends SettingsViewHolder {

    private static final String TAG = "ViewHolderSetting";

    @Bind(R.id.view_holder_setting_day_night_button)
    public ToggleButton daynight;
    @Bind(R.id.view_holder_setting_welcome_button)
    public ToggleButton welcomepage;

    public ViewHolderSetting(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);

        setDaynightOnTouch();
        setWelcomepageOnTouch();

    }

    /**
     * 设置夜间模式按钮点击事件监听
     */
    public void setDaynightOnTouch() {
        daynight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"daynight clicked!");
                daynight.setChooseState();
                if (daynight.isChoosed()) {
                    Log.i(TAG,"daynight is choosed!");
                } else {
                    Log.i(TAG,"daynight is unchoosed!");
                }
            }
        });
    }

    /**
     * 设置欢迎页按钮点击事件监听
     */
    public void setWelcomepageOnTouch() {
        welcomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"welcomepage clicked!");
                welcomepage.setChooseState();
                if (welcomepage.isChoosed()) {
                    Log.i(TAG,"welcomepage is choosed!");
                } else {
                    Log.i(TAG,"welcomepage is unchoosed!");
                }
            }
        });
    }

    public static ViewHolderSetting create(ViewGroup parent) {
        return new ViewHolderSetting(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.settings_viewholder_setting,parent,false));
    }
}
