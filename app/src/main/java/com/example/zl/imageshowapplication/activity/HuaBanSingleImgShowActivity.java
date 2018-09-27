package com.example.zl.imageshowapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zl.commoncomponent.TextProgressBar;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.bean.huaban.transobj.HBImageBean;
import com.example.zl.imageshowapplication.utils.FileDownloadUtil;
import com.example.zl.imageshowapplication.widget.ZoomImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import static com.example.zl.imageshowapplication.config.UILConfig.NORMAL_OPTION;

/**
 * Created by ZhongLeiDev on 2018/9/27.
 * HuaBan 单 Image 显示 Activity
 */

public class HuaBanSingleImgShowActivity extends AppCompatActivity {

    ZoomImageView zoomImageView;
    Button btn;
    TextProgressBar progressBar;
    HBImageBean hbImageBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleimgshow_layout);

        hbImageBean = (HBImageBean) getIntent().getSerializableExtra("data");

        initView();

    }

    private void initView() {
        zoomImageView = findViewById(R.id.singleimgview);
        ImageLoader.getInstance().displayImage(hbImageBean.getUrl(),
                zoomImageView, NORMAL_OPTION);

        progressBar = findViewById(R.id.singleprogressBar);
        progressBar.setText(String.valueOf(hbImageBean.getBoard_id()));

        btn = findViewById(R.id.singlebtndownload);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (FileDownloadUtil.downloadPicture(String.valueOf(hbImageBean.getUrl().hashCode()))) {
                    Toast.makeText(HuaBanSingleImgShowActivity.this, "图片存储成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HuaBanSingleImgShowActivity.this, "存储失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
