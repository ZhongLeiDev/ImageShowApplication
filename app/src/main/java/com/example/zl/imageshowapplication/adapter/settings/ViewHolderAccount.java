package com.example.zl.imageshowapplication.adapter.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.utils.AvatarSelectUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/10/15.
 * 账号设置
 */

public class ViewHolderAccount extends SettingsViewHolder {

    @Bind(R.id.view_holder_account_image)
    public ImageView avatar;
    @Bind(R.id.view_holder_account_text)
    public TextView account;

    public ViewHolderAccount(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);

    }

    /**
     * 设置头像事件监听
     * @param listener 事件监听
     */
    public void setOnAvatarClick(View.OnClickListener listener) {
        avatar.setOnClickListener(listener);
    }

    /**
     * 获取头像设置 ImageView
     * @return
     */
    public ImageView getAvatarImageView() {
        return avatar;
    }

    /**
     * 设置账号名称
     * @return
     */
    public void setAccount(String username) {
        account.setText(username);
    }

    public static ViewHolderAccount create(ViewGroup parent) {
        return new ViewHolderAccount(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.settings_viewholder_account,parent,false));
    }

}
