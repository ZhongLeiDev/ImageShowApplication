package com.example.zl.imageshowapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.activity.ViewPagerImageViewZQUI;
import com.example.zl.imageshowapplication.adapter.GeekWaterFallAdapter;
import com.example.zl.imageshowapplication.base.BaseFragment;
import com.example.zl.imageshowapplication.bean.geek.GeekImgBean;
import com.example.zl.imageshowapplication.bean.geek.GeekResult;
import com.example.zl.imageshowapplication.myinterface.InfoService;
import com.example.zl.imageshowapplication.myinterface.OnMyItemClickListener;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/3/15.
 */

public class GeekWaterFallFragment extends BaseFragment {

    private static String url = "http://gank.io/api/";
    private Retrofit retrofit;
    private InfoService infoService;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private GeekWaterFallAdapter mAdapter;

    public InfoService getRetroSingleInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()

                    .client(new OkHttpClient())

                    .baseUrl(url)

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
        }
        infoService = retrofit.create(InfoService.class);
        return infoService;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_main;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData() {
        mRecyclerView.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mAdapter = new GeekWaterFallAdapter(mActivity);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMyItemClickListener(new OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                Log.i("GeekWaterFallFragment","URL->" + mAdapter.getList().get(pos).getUrl() + " is pressed!!!");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ViewPagerImageViewZQUI.class);
                intent.putExtra("data", (Serializable)mAdapter.getList());
                intent.putExtra("position", pos);
                startActivity(intent);
            }

            @Override
            public void mLongClick(View v, int pos) {
                Log.i("GeekWaterFallFragment","URL->" + mAdapter.getList().get(pos).getUrl() + " is long pressed!!!");
            }
        });

        initImgData();
    }

    private void initImgData() {

        Call<GeekResult> call = getRetroSingleInstance().getResult(30, 1);
        call.enqueue(new Callback<GeekResult>() {

            public void onResponse(Call<GeekResult> call, Response<GeekResult> response) {
                List<GeekImgBean> list = response.body().getResults();
                System.out.println("call->"+response.body());
                mAdapter.getList().addAll(list);
                mAdapter.getRandomHeight(list);
                mAdapter.notifyDataSetChanged();

            }

            public void onFailure(Call<GeekResult> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

}
