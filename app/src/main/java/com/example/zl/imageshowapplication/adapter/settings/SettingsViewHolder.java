package com.example.zl.imageshowapplication.adapter.settings;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZhongLeiDev on 2018/10/15.
 * 设置界面 ViewHolder 虚类
 */

public abstract class SettingsViewHolder extends RecyclerView.ViewHolder{
    /**设置头*/
    public static final int VIEW_HOLDER_HEADER = 0;
    /**账号设置项 */
    public static final int VIEW_HOLDER_ACCOUNT = VIEW_HOLDER_HEADER + 1;
    /**存储设置项*/
    public static final int VIEW_HOLDER_STORAGE = VIEW_HOLDER_ACCOUNT + 1;
    /**应用配置项*/
    public static final int VIEW_HOLDER_SETTING = VIEW_HOLDER_STORAGE + 1;
    /**其它设置项*/
    public static final int VIEW_HOLDER_OTHER = VIEW_HOLDER_SETTING + 1;

    public SettingsViewHolder(View itemView) {
        super(itemView);
    }

    public static SettingsViewHolder create(ViewGroup parent, int type) {

        switch (type) {
            case VIEW_HOLDER_HEADER:
                return ViewHolderHeader.create(parent);
            case VIEW_HOLDER_ACCOUNT:
                return ViewHolderAccount.create(parent);
            case VIEW_HOLDER_STORAGE:
                return ViewHolderStorage.create(parent);
            case VIEW_HOLDER_SETTING:
                return ViewHolderSetting.create(parent);
            case VIEW_HOLDER_OTHER:
                return ViewHolderOther.create(parent);
            default:
                return ViewHolderHeader.create(parent);
        }

    }

}
