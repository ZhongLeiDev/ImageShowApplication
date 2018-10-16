package com.example.zl.imageshowapplication.adapter.settings;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.config.UILConfig;
import com.example.zl.imageshowapplication.utils.FileSizeUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/10/15.
 * 存储设置
 */

public class ViewHolderStorage extends SettingsViewHolder{

    private static final String TAG = "ViewHolderStorage";

    @Bind(R.id.view_holder_storage_cache_text)
    public TextView cacheTextView;
    @Bind(R.id.view_holder_storage_disk_text)
    public TextView diskTextView;
    @Bind(R.id.view_holder_storage_clear_btn)
    public Button clearButton;

    private MyHandler mHandler = new MyHandler(this);

    public ViewHolderStorage(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);

        setCachePath(UILConfig.CACHEPATH);
        setDownloadPath(UILConfig.DOWNLOADPATH);

        getFileSizeThreadStart();
        setOnClearBtnClick();

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
     */
    public void setOnClearBtnClick() {
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.getInstance().clearDiskCache(); //清除缓存
                getFileSizeThreadStart();
            }
        });
    }

    /**
     * 开启容量自动查询线程
     */
    public void getFileSizeThreadStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String value = FileSizeUtil.getAutoFileOrFilesSize(UILConfig.CACHEPATH);
                Log.i(TAG, "FileSize->" + value);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = value;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    /**
     * 设置文本
     * @param str 文本内容
     */
    public void setClearButtonText(String str) {
        clearButton.setText(str);
    }

    public static ViewHolderStorage create(ViewGroup parent) {
        return new ViewHolderStorage(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.settings_viewholder_storage,parent,false));
    }

    static class MyHandler extends Handler {
        private final WeakReference<ViewHolderStorage> mHolder;

        MyHandler(ViewHolderStorage holder) {
            mHolder = new WeakReference<>(holder);
        }
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 1) {
                mHolder.get().setClearButtonText("清除缓存 (" + msg.obj + ")");
            }
        }
    }

}
