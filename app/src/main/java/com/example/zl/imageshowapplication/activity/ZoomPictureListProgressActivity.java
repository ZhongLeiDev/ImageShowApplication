package com.example.zl.imageshowapplication.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.bean.bcy.retro.PictureInfo;
import com.example.zl.imageshowapplication.widget.TextProgressBar;
import com.example.zl.imageshowapplication.widget.TounChImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.zl.imageshowapplication.config.UILConfig.NORMAL_OPTION;

/**
 * Created by ZhongLeiDev on 2018/6/11.
 */

public class ZoomPictureListProgressActivity extends AppCompatActivity {

    ViewPager viewPager;
    TextProgressBar progressBar;
    TounChImageView currentTouchImageView;
    int imageviewposition = 0;
    int currentpos = 0;


    private List<PictureInfo> urlList = new ArrayList<>();

    ImageView[] mImageViews;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_progress_layout);

        urlList =  (ArrayList<PictureInfo>) getIntent().getSerializableExtra("data");
        currentpos = getIntent().getIntExtra("position", 0);

        initView();
    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setText("点击加载原图");
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.getInstance().displayImage(urlList.get(imageviewposition).getPictureOrigurl(),
                        currentTouchImageView, NORMAL_OPTION, new ImageLoadingListener() {
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
                                progressBar.setProgress((int)(((double)current/(double)total)*100));
                            }
                        });
            }
        });
        mImageViews = new ImageView[urlList.size()];
        viewPager = (ViewPager) findViewById(R.id.img_viewpager);
        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //可以使用其他的ImageView 控件
                TounChImageView tounChImageView = new TounChImageView(ZoomPictureListProgressActivity.this);
                currentTouchImageView = tounChImageView;
                imageviewposition = position;

                /*---------------------------------UniversalImageLoader代替Picasso--------------------
                try {
                    Picasso.with(GeekListPagerImageViewActivity.this).load(mImgs[position])
                            .placeholder(R.mipmap.img1)//默认显示的图片
                           // .resize(500,300)//控制图片高度，不添加则自适应
                            .error(R.mipmap.ic_launcher)//加载时出现错误显示的图片
                            .into(tounChImageView);
                } catch (Exception e) {

                }
                -------------------------------------------------------------------------------------------*/

                ImageLoader.getInstance().displayImage(urlList.get(position).getPictureUrl(),
                        tounChImageView, NORMAL_OPTION);

//                ImageLoader.getInstance().displayImage(urlList.get(position).getPictureOrigurl(), //显示原图
//                        tounChImageView, NORMAL_OPTION);

                //单击返回
                tounChImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                container.addView(tounChImageView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mImageViews[position] = tounChImageView;
                return tounChImageView;
            }

            @Override
            public int getCount() {
                return urlList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViews[position]);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }
        });

        viewPager.setCurrentItem(currentpos);

    }

}
