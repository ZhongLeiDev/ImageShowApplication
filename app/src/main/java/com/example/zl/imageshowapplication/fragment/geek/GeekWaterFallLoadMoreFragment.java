package com.example.zl.imageshowapplication.fragment.geek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.activity.GeekListPagerImageViewActivity;
import com.example.zl.imageshowapplication.adapter.geek.GeekWaterFallLoadMoreAdapter;
import com.example.zl.imageshowapplication.base.BaseFragment;
import com.example.zl.imageshowapplication.bean.geek.GeekImgBean;
import com.example.zl.imageshowapplication.bean.geek.GeekResult;
import com.example.zl.imageshowapplication.linkanalyzestrategy.retrofits.RetrofitFactory;
import com.example.zl.imageshowapplication.message.BaseMessage;
import com.example.zl.imageshowapplication.message.MsgEnums;
import com.example.zl.imageshowapplication.myinterface.LoadMoreListener;
import com.example.zl.imageshowapplication.myinterface.listenerinstance.GeekLoadMoreScrollListener;
import com.example.zl.imageshowapplication.myinterface.OnMyItemClickListener;
import com.example.zl.imageshowapplication.myinterface.RetrofitInfoService;
import com.example.zl.imageshowapplication.utils.FileDownloadUtil;
import com.example.zl.imageshowapplication.utils.NetWorkUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZhongLeiDev on 2018/3/15.
 * Geek图片瀑布流显示
 */

public class GeekWaterFallLoadMoreFragment extends BaseFragment implements LoadMoreListener {

    private static final String TAG = "GeekFragment";

    private RetrofitInfoService geekInfoService = RetrofitFactory.getGeekRetroSingleInstance();
    private int currentPage = 0;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private GeekWaterFallLoadMoreAdapter mAdapter;
    private boolean isNetWorkConnected = false;

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

        //注册EventBus接收网络状态改变广播通知
        EventBus.getDefault().register(this);

        mRecyclerView.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mRecyclerView.addOnScrollListener(new GeekLoadMoreScrollListener());
        mAdapter = new GeekWaterFallLoadMoreAdapter(mActivity, this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMyItemClickListener(new OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                Log.i(TAG,"URL->" + mAdapter.getList().get(pos).getUrl() + " is pressed!!!");
                Intent intent = new Intent();
                intent.setClass(getSafeActivity(), GeekListPagerImageViewActivity.class);
                intent.putExtra("data", (Serializable)mAdapter.getList());
                intent.putExtra("position", pos);
                startActivity(intent);
            }

            @Override
            public void mLongClick(View v, int pos) {
                showPopupMenu(v,pos);
                Log.i(TAG,"URL->" + mAdapter.getList().get(pos).getUrl() + " is long pressed!!!");
            }
        });

        boolean isNetworkAvailable = NetWorkUtil.isNetworkAvailable(getSafeActivity());

        Log.i(TAG,"NetWorkStatus->" + isNetworkAvailable);

        if (isNetworkAvailable) {
            requestData();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册EventBus
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void updateNetStatusWithTag(BaseMessage msg) {  //处理 EventBus 传输过来的事件
        Log.i(TAG, "NetWorkStatusChanged!msg = " + msg.getExtramsg());
        if (msg.getMsg() == MsgEnums.NET_WIFI_CONNECTED || msg.getMsg() == MsgEnums.NET_MOBILE_CONNECTED) {
            isNetWorkConnected = true;
        } else {
            isNetWorkConnected = false;
        }
//        Toast.makeText(getSafeActivity(), msg.getExtramsg(),Toast.LENGTH_SHORT).show();
    }

    private void requestData() {

        Log.i(TAG, "GeekStartRequestData...");

        Call<GeekResult> call = geekInfoService.getGeekResult(30, currentPage);
        call.enqueue(new Callback<GeekResult>() {

            public void onResponse(Call<GeekResult> call, Response<GeekResult> response) {
                List<GeekImgBean> list = response.body().getResults();
                System.out.println("LoadMoreCall->"+response.body());
                if (list.size()>0) {
                    mAdapter.getList().addAll(list);
//                    mAdapter.getRandomHeight(list);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getSafeActivity(),"没有更多内容！", Toast.LENGTH_LONG).show();
                }

            }

            public void onFailure(Call<GeekResult> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getSafeActivity(), "网络连接错误！", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void loadMoreData() {

        if (isNetWorkConnected) {
           requestData();
           currentPage ++;
           Toast.makeText(getSafeActivity(),"正在加载更多！", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getSafeActivity(),"网络连接错误！", Toast.LENGTH_LONG).show();
        }

    }

    private void showPopupMenu(View view, final int position) {

        PopupMenu popupMenu = new PopupMenu(getSafeActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.geek_menu, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.geek_download:
                        if (FileDownloadUtil.downloadPicture(String.valueOf(mAdapter.getList().get(position).getUrl().hashCode()))) {
                            Toast.makeText(getSafeActivity(),"图片下载成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getSafeActivity(),"error:下载失败！", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.geek_collect:
                        break;
                }
                return true;
            }
        });
    }
}
