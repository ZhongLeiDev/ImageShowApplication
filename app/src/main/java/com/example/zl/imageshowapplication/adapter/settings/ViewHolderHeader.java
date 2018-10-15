package com.example.zl.imageshowapplication.adapter.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zl.imageshowapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/10/15.
 * 设置头
 */

public class ViewHolderHeader extends SettingsViewHolder {

    private static final String[] sTitles = new String[]{"账号设置", "存储设置", "功能设置", "其它"};
    private static final int[] sIcons = {
            R.drawable.settings_account,
            R.drawable.settings_storage,
            R.drawable.settings_app,
            R.drawable.settings_other};

    @Bind(R.id.view_holder_header_image)
    public ImageView headerImageView;
    @Bind(R.id.view_holder_header_text)
    public TextView headerTextView;

    public ViewHolderHeader(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);

    }

    /**
     * 设置标题
     * @param index 当前设置的位置,从 1 开始
     */
    public void setHeader(int index) {
        headerImageView.setImageResource(sIcons[index-1]);
        headerTextView.setText(sTitles[index-1]);
    }

    public static ViewHolderHeader create(ViewGroup parent) {
        return new ViewHolderHeader(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.settings_viewholder_header, parent, false));
    }

}
