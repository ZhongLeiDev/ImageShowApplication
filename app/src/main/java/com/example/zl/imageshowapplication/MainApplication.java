package com.example.zl.imageshowapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.zl.imageshowapplication.config.UILConfig;
import com.example.zl.service.NetworkStatusService;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

/**
 * MainApplication
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createDiskCache();
        initImageLoader(getApplicationContext());
        //开启网络状态监听Service
        startService(new Intent(getApplicationContext(), NetworkStatusService.class));
    }

    /**
     *
     * 开源Imageloder的全局配置
     */
    /**
     * 初始化图片加载相关
     *
     * @param context
     */
    private void initImageLoader(Context context) {
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 4);
        MemoryCache memoryCache;
        if (Build.VERSION.SDK_INT >= 9) {
            memoryCache = new LruMemoryCache(memoryCacheSize);
        } else {
            memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 3)
                .memoryCache(memoryCache)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new FileNameGenerator() {
                    @Override
                    public String generate(String imageUri) {
                        return String.valueOf(imageUri.hashCode());
                    }
                })
                .diskCache(new LimitedAgeDiskCache(new File(UILConfig.CACHEPATH),60*60))//自定义缓存路径
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);

        File file = ImageLoader.getInstance().getDiskCache().getDirectory();
        Log.i("MainApplication", "defaultDiskCache:" + file.getAbsolutePath());

    }

    /**
     * 创建图片缓存文件夹
     * @return
     */
    private void createDiskCache( ) {

        File f = new File(UILConfig.CACHEPATH);
        if (!f.exists()){
            f.mkdirs();
        }
        File f1 = new File(UILConfig.DOWNLOADPATH);
        if (!f1.exists()){
            f1.mkdirs();
        }

    }
}
