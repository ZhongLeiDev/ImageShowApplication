package com.example.zl.imageshowapplication.adapter.settings;

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

    @Bind(R.id.view_holder_setting_day_night_button)
    public ToggleButton daynight;
    @Bind(R.id.view_holder_setting_welcome_button)
    public ToggleButton welcomepage;

    public ViewHolderSetting(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);

    }

    /**
     * 设置夜间模式开
     */
    public void setDaynightOn() {
        daynight.setChooseState(true);
    }

    /**
     * 设置夜间模式关
     */
    public void setDaynightOff() {
        daynight.setChooseState(false);
    }

    /**
     * 设置欢迎页开
     */
    public void setWelcomepageOn() {
        welcomepage.setChooseState(true);
    }

    /**
     * 设置欢迎页关
     */
    public void setWelcomepageOff() {
        welcomepage.setChooseState(false);
    }

    public static ViewHolderSetting create(ViewGroup parent) {
        return new ViewHolderSetting(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.settings_viewholder_setting,parent,false));
    }
}
