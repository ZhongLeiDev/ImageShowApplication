package com.example.zl.imageshowapplication.config;

import android.os.Environment;

import com.example.zl.imageshowapplication.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.File;

/**
 * Created by ZhongLeiDev on 2018/5/17.
 * UniversalImageLoader 配置类
 */
public class UILConfig {

    /**
     * 图片存储缓存路径
     */
    public static final String CACHEPATH = Environment.getExternalStorageDirectory().getPath()
            + File.separator + "CoserImg" + File.separator + "CoserImgShowCache" + File.separator;

    /**
     * 图片存储下载路径
     */
    public static final String DOWNLOADPATH = Environment.getExternalStorageDirectory().getPath()
            + File.separator + "CoserImg" + File.separator + "CoserImgDownload" + File.separator;

    /**
     * UML图片显示相关配置
     */
    public static DisplayImageOptions NORMAL_OPTION = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .showImageOnLoading(R.drawable.temploading)
            .showImageForEmptyUri(R.drawable.notfoundloading)
            .showImageOnFail(R.drawable.errorloading)
            .cacheOnDisk(true)
            .build();

}
