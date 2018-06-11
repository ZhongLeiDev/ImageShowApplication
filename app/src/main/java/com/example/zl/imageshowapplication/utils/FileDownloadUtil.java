package com.example.zl.imageshowapplication.utils;

import com.example.zl.imageshowapplication.config.UILConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by ZhongLeiDev on 2018/6/11.
 */

public class FileDownloadUtil {

    private FileDownloadUtil() {

    }

    /**
     * 下载图片的方法
     * @param hashcose 传入图片链接的hashcode(基于UniversalImageLoader存储机制)
     * @return
     */
    public static boolean downloadPicture(String hashcose) {
        File fromFile = new File(UILConfig.CACHEPATH + hashcose);
        File toFile = new File(UILConfig.DOWNLOADPATH + hashcose + ".jpg");
        if(!fromFile.exists()) {
            return false;
        }
        if (!toFile.exists()) {
            try {
                toFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        FileInputStream ins = null;
        FileOutputStream out = null;
        try {
            ins = new FileInputStream(fromFile);
            out = new FileOutputStream(toFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        byte[] b = new byte[1024];
        int n=0;
        try {
            while((n=ins.read(b))!=-1){
                out.write(b, 0, n);
            }
            ins.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
