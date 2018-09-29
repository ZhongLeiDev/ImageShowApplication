package com.example.zl.imageshowapplication.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zl.enums.AuthorFragmentType;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.activity.BcyAlbumsListActivity;
import com.example.zl.imageshowapplication.adapter.bcy.BcyCoserWaterFallLoadMoreAdapter;
import com.example.zl.imageshowapplication.bean.bcy.retro.CoserInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.linkanalyzestrategy.retrofits.RetrofitFactory;
import com.example.zl.imageshowapplication.message.BaseMessage;
import com.example.zl.imageshowapplication.message.MsgEnums;
import com.example.zl.imageshowapplication.myinterface.LoadMoreListener;
import com.example.zl.imageshowapplication.myinterface.OnMyItemClickListener;
import com.example.zl.imageshowapplication.myinterface.RetrofitInfoService;
import com.example.zl.imageshowapplication.myinterface.listenerinstance.BcyCosersLoadMoreScrollListener;
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
 * Created by ZhongLeiDev on 2018/9/19.
 * 作者显示 Fragment 基类
 */

public class BaseCoserInfoFragment extends BaseFragment implements LoadMoreListener {

    private static final String TAG = "BaseCoserInfoFragment";
    private RetrofitInfoService bcyInfoService = RetrofitFactory.getBcyRetroSingleInstance();

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private BcyCoserWaterFallLoadMoreAdapter mAdapter;
    private boolean isNetWorkConnected = false;
    private int currentpage = 0;

    // Fragment 传参参数名
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //参数值
    private AuthorFragmentType fragmentType;
    private String searchTag;

    /**
     * 获取 BaseCoserFragment 实例
     * @param ftype Fragment 类型
     * @param tag 查询条件
     * @return
     */
    public static BaseCoserInfoFragment newInstance(AuthorFragmentType ftype, String tag) {
        BaseCoserInfoFragment fragment = new BaseCoserInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,ftype);
        args.putString(ARG_PARAM2,tag);
        fragment.setArguments(args);
        return fragment;
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

        //注册EventBus接收网络状态改变广播通知
        EventBus.getDefault().register(this);

        mRecyclerView.setLayoutManager(new
                StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addOnScrollListener(new BcyCosersLoadMoreScrollListener());
        mAdapter = new BcyCoserWaterFallLoadMoreAdapter(mActivity,this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMyItemClickListener(new OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                /*作者需要跳转到作者作品列表显示Activity再跳转到作品内容显示Activity*/
                Log.i(TAG,"URL->" + mAdapter.getCoserInfoList().get(pos).getCoserName() + " is pressed!!!");
                Intent intent = new Intent();
                intent.setClass(getSafeActivity(), BcyAlbumsListActivity.class);
                intent.putExtra("data", (Serializable)mAdapter.getCoserInfoList().get(pos).getCoserId());
                startActivity(intent);
            }

            @Override
            public void mLongClick(View v, int pos) {
                Log.i(TAG,"URL->" + mAdapter.getCoserInfoList().get(pos).getCoserName() + " is long pressed!!!");
            }
        });

        fragmentType = (AuthorFragmentType) getArguments().getSerializable(ARG_PARAM1);
        searchTag = getArguments().getString(ARG_PARAM2);

        if (NetWorkUtil.isNetworkAvailable(getSafeActivity())) {
            requestData(searchTag, currentpage);
        }

    }

    private void requestData(String tag, int currentpage) {
        Call<ResultVO<List<CoserInfo>>> call = null;
        switch (fragmentType) {
            case AUTHOR_FRAGMENT_RANDOM:
                call = bcyInfoService.getBcyRandomCosers(24);
                break;
            case AUTHOR_FRAGMENT_SEARCH_BY_NAME:
                call = bcyInfoService.getBcySearchCosers(searchTag,currentpage,24);
                break;
            case AUTHOR_FRAGMENT_SEARCH_BY_DESC:
                break;
            case AUTHOR_FRAGMENT_SEARCH_BY_ADDRESS:
                break;
            case AUTHOR_FRAGMENT_SEARCH_BY_ID:
                break;
        }

        call.enqueue(new Callback<ResultVO<List<CoserInfo>>>() {

            public void onResponse(Call<ResultVO<List<CoserInfo>>> call, Response<ResultVO<List<CoserInfo>>> response) {
                if (response.body() != null) {
                    List<CoserInfo> list = response.body().getData();
                    System.out.println("LoadMoreCall->" + response.body());
                    if (list.size() > 0) {
                        mAdapter.getCoserInfoList().addAll(list);
//                        mAdapter.getRandomHeight(list);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getSafeActivity(), "没有更多内容！", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getSafeActivity(), "error:返回内容为空！", Toast.LENGTH_LONG).show();
                }

            }

            public void onFailure(Call<ResultVO<List<CoserInfo>>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getSafeActivity(),"网络连接错误！", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void loadMoreData() {
        if (isNetWorkConnected) {
            requestData(searchTag, currentpage);
            Toast.makeText(getSafeActivity(),"正在加载更多！", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getSafeActivity(),"网络连接错误！", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册EventBus
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)   // sticky = true,设置粘性事件,后注册的接收器也能够接收到注册之前的最后一个事件
    public void updateNetStatusWithTag(BaseMessage msg) {  //处理 EventBus 传输过来的事件
        Log.i(TAG, "NetWorkStatusChanged!msg = " + msg.getExtramsg());
        if (msg.getMsg() == MsgEnums.NET_WIFI_CONNECTED || msg.getMsg() == MsgEnums.NET_MOBILE_CONNECTED) {
            isNetWorkConnected = true;
        } else {
            isNetWorkConnected = false;
        }
//        Toast.makeText(getSafeActivity(), msg.getExtramsg(),Toast.LENGTH_SHORT).show();
    }

}
