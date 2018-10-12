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

    public static final String BASEPATH = Environment.getExternalStorageDirectory().getPath()
            + File.separator + "CoserImg" + File.separator;

    /**
     * 图片存储缓存路径
     */
    public static final String CACHEPATH = BASEPATH + "CoserImgShowCache" + File.separator;

    /**
     * 图片存储下载路径
     */
    public static final String DOWNLOADPATH = BASEPATH + "CoserImgDownload" + File.separator;

    /**
     * 欢迎页面资源存储路径
     */
    public static final String WELCOMEPATH = BASEPATH + "Config" + File.separator +
            "WelcomeSrc" + File.separator;

    /**
     * 头像资源缓存
     */
    public static final String AVATARPATH = BASEPATH + "Config" + File.separator +
            "Avatar" + File.separator;

    /**
     * UML图片显示相关配置
     */
    public static DisplayImageOptions NORMAL_OPTION = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .showImageOnLoading(R.drawable.icon)
            .showImageForEmptyUri(R.drawable.notfoundloading)
            .showImageOnFail(R.drawable.errorloading)
            .cacheOnDisk(true)
            .build();

    /**
     * UML原图显示相关配置
     */
    public static DisplayImageOptions SHOW_ORI_OPTION = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .showImageForEmptyUri(R.drawable.notfoundloading)
            .showImageOnFail(R.drawable.errorloading)
            .cacheOnDisk(true)
            .build();

}
