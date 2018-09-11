package com.example.zl.imageshowapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.bean.geek.GeekImgBean;
import com.example.zl.imageshowapplication.widget.TounChImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.example.zl.imageshowapplication.config.UILConfig.NORMAL_OPTION;

/**
 * Created by zhangqie on 2017/6/20.
 * Geek滑动相册显示Activity
 *
 */

public class GeekListPagerImageViewActivity extends AppCompatActivity {


    ViewPager viewPager;
    int currentpos = 0;


    private List<GeekImgBean> urlList = new ArrayList<>();

    ImageView[] mImageViews;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_layout);

        urlList =  (ArrayList<GeekImgBean>) getIntent().getSerializableExtra("data");
        currentpos = getIntent().getIntExtra("position", 0);

        initView();
    }

    private void initView(){
        mImageViews= new ImageView[urlList.size()];
        viewPager= findViewById(R.id.img_viewpager);
        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //可以使用其他的ImageView 控件
                TounChImageView tounChImageView=new TounChImageView(GeekListPagerImageViewActivity.this);


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

                ImageLoader.getInstance().displayImage(urlList.get(position).getUrl(),
                        tounChImageView, NORMAL_OPTION);

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
