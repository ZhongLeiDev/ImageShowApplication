package com.example.zl.imageshowapplication.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.bean.bcy.retro.PictureInfo;
import com.example.zl.imageshowapplication.utils.FileDownloadUtil;
import com.example.zl.imageshowapplication.widget.TextProgressBar;
import com.example.zl.imageshowapplication.widget.ZoomImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import static com.example.zl.imageshowapplication.config.UILConfig.NORMAL_OPTION;
import static com.example.zl.imageshowapplication.config.UILConfig.SHOW_ORI_OPTION;

/**
 * Created by ZhongLeiDev on 2018/6/12.
 * 单张图片显示Activity
 *
 */

public class SingleImgShowActivity extends AppCompatActivity {

    ZoomImageView zoomImageView;
    Button btn;
    TextProgressBar progressBar;
    PictureInfo pictureInfo; //待显示的Picture

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleimgshow_layout);

        pictureInfo = (PictureInfo) getIntent().getSerializableExtra("data");

        initView();
    }

    private void initView(){

        zoomImageView = findViewById(R.id.singleimgview);
        ImageLoader.getInstance().displayImage(pictureInfo.getPictureUrl(),
                zoomImageView, NORMAL_OPTION);

        progressBar = findViewById(R.id.singleprogressBar);
        progressBar.setText("点击加载原图");
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.getInstance().displayImage(pictureInfo.getPictureOrigurl(),
                        zoomImageView, SHOW_ORI_OPTION, new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {
                                progressBar.setText("开始加载原图...");
                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                progressBar.setText("原图加载失败！");
                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                progressBar.setText("原图已加载成功！");
                            }

                            @Override
                            public void onLoadingCancelled(String imageUri, View view) {
                                progressBar.setText("已取消加载！");
                            }
                        }, new ImageLoadingProgressListener() {
                            @Override
                            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                                progressBar.setProgress((int) (((double) current / (double) total) * 100));
                            }
                        });
            }
        });

        btn = findViewById(R.id.singlebtndownload);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FileDownloadUtil.downloadPicture(String.valueOf(pictureInfo.getPictureOrigurl().hashCode()))) {
                    Toast.makeText(SingleImgShowActivity.this, "原始图片存储成功！", Toast.LENGTH_SHORT).show();
                } else if (FileDownloadUtil.downloadPicture(String.valueOf(pictureInfo.getPictureUrl().hashCode()))) {
                    Toast.makeText(SingleImgShowActivity.this, "显示图片存储成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SingleImgShowActivity.this, "存储失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
