package com.example.zl.imageshowapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.adapter.bcy.BcyAlbumPicturesListAdapter;
import com.example.zl.imageshowapplication.bean.bcy.retro.PictureInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.bean.geek.GeekImgBean;
import com.example.zl.imageshowapplication.linkanalyzestrategy.retrofits.RetrofitFactory;
import com.example.zl.imageshowapplication.myinterface.RetrofitInfoService;
import com.example.zl.imageshowapplication.widget.TounChImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.zl.imageshowapplication.config.UILConfig.NORMAL_OPTION;

/**
 * Created by ZhongLeiDev on 2018-05-17.
 * BCY 类型的 BcyAlbum 中的图片集合显示 Activity
 * ListView显示一个相册中的所有图片
 */

public class BcyAlbumPicturesListActivity extends AppCompatActivity {

    private BcyAlbumPicturesListAdapter mAdapter;
//    @Bind(R.id.album_pictures_list)
    private ListView piclistView;
    /**从上层传过来的相册Id，用于根据相册Id查询相册图片列表*/
    private String albumId;
    private RetrofitInfoService bcyInfoService = RetrofitFactory.getBcyRetroSingleInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_pictures_list);

        albumId = (String)getIntent().getSerializableExtra("data");

        initView();

        getData(albumId);

    }

    private void initView(){
        piclistView = findViewById(R.id.album_pictures_list);
        mAdapter = new BcyAlbumPicturesListAdapter(this);
        piclistView.setAdapter(mAdapter);
        piclistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ItemClicked","picUrl->" + mAdapter.getPictureInfoList().get(position).getPictureUrl());
            }
        });

    }

    /**
     * 获取相册图片列表数据
     */
    private void getData(String albumId) {
        Call<ResultVO<List<PictureInfo>>> call = bcyInfoService.getBcyRandomPictures(15);
        call.enqueue(new Callback<ResultVO<List<PictureInfo>>>() {

            public void onResponse(Call<ResultVO<List<PictureInfo>>> call, Response<ResultVO<List<PictureInfo>>> response) {
                List<PictureInfo> list = response.body().getData();
                System.out.println("LoadMoreCall->"+response.body());
                if (list.size()>0) {
                    mAdapter.getPictureInfoList().addAll(list);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(BcyAlbumPicturesListActivity.this,"没有更多内容！", Toast.LENGTH_LONG).show();
                }

            }

            public void onFailure(Call<ResultVO<List<PictureInfo>>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(BcyAlbumPicturesListActivity.this,"网络连接错误！", Toast.LENGTH_LONG).show();
            }
        });
    }
}
