package com.example.zl.imageshowapplication.fragment.geek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.activity.GeekListPagerImageViewActivity;
import com.example.zl.imageshowapplication.adapter.geek.GeekWaterFallAdapter;
import com.example.zl.imageshowapplication.base.BaseFragment;
import com.example.zl.imageshowapplication.bean.geek.GeekImgBean;
import com.example.zl.imageshowapplication.bean.geek.GeekResult;
import com.example.zl.imageshowapplication.linkanalyzestrategy.retrofits.RetrofitFactory;
import com.example.zl.imageshowapplication.myinterface.RetrofitInfoService;
import com.example.zl.imageshowapplication.myinterface.OnMyItemClickListener;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/3/15.
 * Geek 瀑布流 fragment
 */

public class GeekWaterFallFragment extends BaseFragment {

    private RetrofitInfoService geekInfoService = RetrofitFactory.getGeekRetroSingleInstance();

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private GeekWaterFallAdapter mAdapter;

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
                intent.setClass(getSafeActivity(), GeekListPagerImageViewActivity.class);
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

        Call<GeekResult> call = geekInfoService.getGeekResult(30, 1);
        call.enqueue(new Callback<GeekResult>() {

            public void onResponse(Call<GeekResult> call, Response<GeekResult> response) {
                List<GeekImgBean> list = response.body().getResults();
                System.out.println("call->"+response.body());
                mAdapter.getList().addAll(list);
//                mAdapter.getRandomHeight(list);
                mAdapter.notifyDataSetChanged();

            }

            public void onFailure(Call<GeekResult> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

}
