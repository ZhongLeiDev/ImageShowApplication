package com.example.zl.imageshowapplication.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.zl.imageshowapplication.config.UILConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by ZhongLeiDev on 2018/10/11.
 * 头像选择工具类
 */

public class AvatarSelectUtil {

    /**从相册选择*/
    public static final int SCAN_OPEN_PHONE = 0;
    /**拍照选择*/
    public static final int PHONE_CAMERA = 1;
    /**裁剪图片*/
    public static final int PHONE_CROP = 2;

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 复制头像文件
     * @param srcpath 源路径
     * @param despath 目标路径
     * @return
     */
    public static boolean copyAvatar(String srcpath, String despath) {
        File src = new File(srcpath);
        if (!src.exists()) {
            return false;
        }
        File des = new File(despath);
        if (des.exists() && des.delete()) {
            try {
                des.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            input = new FileInputStream(src);
            output = new FileOutputStream(des);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (input != null && output != null) {
                try {
                    input.close();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return true;
    }

    /**
     * 根据 SrcId 设置头像
     * @param ctx 上下文
     * @param background 背景ImageView
     * @param avatar 头像ImageView
     * @param resId 资源文件id
     */
    public static void setAvatarWithSrcId(Context ctx, ImageView background, ImageView avatar, int resId) {
        Glide.with(ctx).load(resId)  //模糊特效
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(background);
        Glide.with(ctx).load(resId)  //圆形显示
                .apply(RequestOptions.bitmapTransform(new CircleCrop())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(avatar);
    }

    /**
     * 根据图片路径设置头像，包含头像设置和背景设置
     * @param ctx 上下文
     * @param background 背景ImageView
     * @param avatar 头像ImageView
     * @param filepath 资源文件路径
     */
    public static boolean setAvatarWithPath(Context ctx, ImageView background, ImageView avatar, String filepath) {
        File avatarFile = new File(filepath);
        if (avatarFile.exists()) {
            Glide.with(ctx).load(avatarFile)  //模糊特效
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(25))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true))
                    .into(background);
            Glide.with(ctx).load(avatarFile)  //圆形显示
                    .apply(RequestOptions.bitmapTransform(new CircleCrop())
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true))
                    .into(avatar);
            return true;
        } else {
            Log.i("setAvatarWithPath", "avatarFile not exist!");
            return false;
        }
    }

    /**
     * 根据头像路径设置头像，不包含背景
     * @param ctx 上下文
     * @param avatar 头像ImageView
     * @param filepath 头像文件路径
     * @return
     */
    public static boolean setAvatarWithPath1(Context ctx, ImageView avatar, String filepath) {
        File avatarFile = new File(filepath);
        if (avatarFile.exists()) {
            Glide.with(ctx).load(avatarFile)  //圆形显示
                    .apply(RequestOptions.bitmapTransform(new CircleCrop())
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true))
                    .into(avatar);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据用户名构建头像存储路径
     * @param username 用户名
     * @return
     */
    public static String buildAvatarPath(String username) {
        return UILConfig.AVATARPATH + MD5Utils.getStringMD5(username) + ".png";
    }

}
