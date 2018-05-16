package com.example.zl.imageshowapplication.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zl.imageshowapplication.Message.BaseMessage;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.activity.ViewPagerImageViewZQUI;
import com.example.zl.imageshowapplication.adapter.GeekWaterFallLoadMoreAdapter;
import com.example.zl.imageshowapplication.base.BaseFragment;
import com.example.zl.imageshowapplication.bean.geek.GeekImgBean;
import com.example.zl.imageshowapplication.bean.geek.GeekResult;
import com.example.zl.imageshowapplication.broadcast.NetBroadCast;
import com.example.zl.imageshowapplication.myinterface.InfoService;
import com.example.zl.imageshowapplication.myinterface.LoadMoreListener;
import com.example.zl.imageshowapplication.myinterface.LoadMoreScrollListener;
import com.example.zl.imageshowapplication.myinterface.MsgNotifyReceiver;
import com.example.zl.imageshowapplication.myinterface.OnMyItemClickListener;
import com.example.zl.imageshowapplication.utils.NetWorkUtils;

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

public class GeekWaterFallLoadMoreFragment extends BaseFragment implements LoadMoreListener,MsgNotifyReceiver {

    private static String url = "http://gank.io/api/";
    private Retrofit retrofit;
    private InfoService infoService;
    private int currentPage = 0;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private GeekWaterFallLoadMoreAdapter mAdapter;
    private NetBroadCast mNetworkStateReceiver = new NetBroadCast();
    private boolean isNetWorkConnected = false;
    private boolean isFragmentyInit = false;

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

        //注册网络状态监听广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(mNetworkStateReceiver, filter);
        mNetworkStateReceiver.setNotifyReceiver(this);

        mRecyclerView.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mRecyclerView.addOnScrollListener(new LoadMoreScrollListener());
        mAdapter = new GeekWaterFallLoadMoreAdapter(mActivity, this);
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

        if (NetWorkUtils.isNetworkAvailable(getActivity())) {
            isFragmentyInit = true;
            requestData();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mNetworkStateReceiver);
    }

    private void requestData() {

        Call<GeekResult> call = getRetroSingleInstance().getResult(30, currentPage);
        call.enqueue(new Callback<GeekResult>() {

            public void onResponse(Call<GeekResult> call, Response<GeekResult> response) {
                List<GeekImgBean> list = response.body().getResults();
                System.out.println("LoadMoreCall->"+response.body());
                if (list.size()>0) {
                    mAdapter.getList().addAll(list);
                    mAdapter.getRandomHeight(list);
                    mAdapter.notifyDataSetChanged();
                } else {
//                    Log.i("LoadMore","没有更多内容！");
                    Toast.makeText(getActivity(),"没有更多内容！", Snackbar.LENGTH_LONG).show();
                }

            }

            public void onFailure(Call<GeekResult> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    @Override
    public void loadMoreData() {

        if (isNetWorkConnected) {
           requestData();
           currentPage ++;
            Toast.makeText(getActivity(),"正在加载更多！", Snackbar.LENGTH_LONG).show();
        } else {
//            Log.i("LoadMore","网络连接错误！");
            Toast.makeText(getActivity(),"网络连接错误！", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public void handleMsg(BaseMessage msg) {
        switch (msg.getMsgType()) {
            case NET:
                switch (msg.getMsg()) {
                    case NET_WIFI_CONNECTED:
                        isNetWorkConnected = true;
                        Log.i("MainActivity","NET_WIFI_CONNECTED！");
                        if (!isFragmentyInit) {
                           requestData();
                        }
                        break;
                    case NET_MOBILE_CONNECTED:
                        isNetWorkConnected = true;
                        Toast.makeText(getActivity(),msg.getExtramsg(),Toast.LENGTH_SHORT).show();
                        Log.i("MainActivity","NET_MOBILE_CONNECTED！");
                        break;
                    case NET_DISCONNECTED:
                        isNetWorkConnected = false;
                        Toast.makeText(getActivity(),msg.getExtramsg(),Toast.LENGTH_SHORT).show();
                        Log.i("MainActivity","NET_DISCONNECTED！");
                        break;
                }
                break;
        }
    }
}
