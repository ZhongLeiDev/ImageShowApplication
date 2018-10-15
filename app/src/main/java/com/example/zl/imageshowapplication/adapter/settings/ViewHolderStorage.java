package com.example.zl.imageshowapplication.adapter.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zl.imageshowapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/10/15.
 * 存储设置
 */

public class ViewHolderStorage extends SettingsViewHolder{

    @Bind(R.id.view_holder_storage_cache_text)
    public TextView cacheTextView;
    @Bind(R.id.view_holder_storage_disk_text)
    public TextView diskTextView;
    @Bind(R.id.view_holder_storage_clear_btn)
    public Button clearButton;

    public ViewHolderStorage(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);

    }

    /**
     * 设置缓存路径
     * @param cachePath 缓存路径名称
     */
    public void setCachePath(String cachePath) {
        cacheTextView.setText(cachePath);
    }

    /**
     * 设置图片存储路径
     * @param downloadPath 存储路径名称
     */
    public void setDownloadPath(String downloadPath) {
        diskTextView.setText(downloadPath);
    }

    /**
     * 设置清除按钮点击事件
     * @param listener onClickListener
     */
    public void setOnClearBtnClick(View.OnClickListener listener) {
        clearButton.setOnClickListener(listener);
    }

    public static ViewHolderStorage create(ViewGroup parent) {
        return new ViewHolderStorage(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.settings_viewholder_storage,parent,false));
    }

}
