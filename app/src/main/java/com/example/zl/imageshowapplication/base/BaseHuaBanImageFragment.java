package com.example.zl.imageshowapplication.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zl.enums.HuaBanFragmentType;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.activity.HuaBanSingleImgShowActivity;
import com.example.zl.imageshowapplication.adapter.huaban.HuaBanImageWaterFallLoadMoreAdapter;
import com.example.zl.imageshowapplication.bean.huaban.transobj.HBImageBean;
import com.example.zl.imageshowapplication.message.BaseMessage;
import com.example.zl.imageshowapplication.message.MsgEnums;
import com.example.zl.imageshowapplication.myinterface.LoadMoreListener;
import com.example.zl.imageshowapplication.myinterface.OnMyItemClickListener;
import com.example.zl.imageshowapplication.myinterface.listenerinstance.HuaBanLoadMoreScrollListener;
import com.example.zl.imageshowapplication.utils.NetWorkUtil;
import com.example.zl.mvp.huaban.presenter.HuaBanPresenter;
import com.example.zl.mvp.huaban.view.HuanBanView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/9/26.
 * HuaBanFragment 基类
 */

public class BaseHuaBanImageFragment extends BaseFragment implements LoadMoreListener {

    private static final String TAG = "HuaBanFragment";

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private HuaBanImageWaterFallLoadMoreAdapter mAdapter;

    private boolean isNetWorkConnected = false;
    private int currentpage = 0;

    // Fragment 传参参数名
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //参数值
    private HuaBanFragmentType fragmentType;
    private String searchTag;

    /** HuaBan Image 处理逻辑 Presenter 类*/
    private HuaBanPresenter huaBanPresenter;
    /** HuaBan Image 显示逻辑 View 类*/
    private HuanBanView huanBanView = new HuanBanView() {
        @Override
        public void onNext(HBImageBean bean) {
            mAdapter.getList().add(bean);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onError(String error) {
            Toast.makeText(getActivity(), error,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCompleted(String complete) {
            Toast.makeText(getActivity(), complete,Toast.LENGTH_SHORT).show();
        }
    };

    public static BaseHuaBanImageFragment newInstance(HuaBanFragmentType type, String tag) {

        BaseHuaBanImageFragment fragment = new BaseHuaBanImageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,type);
        args.putString(ARG_PARAM2,tag);
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void loadMoreData() {
        Log.i(TAG,"Start Load More ... ！");
        if (isNetWorkConnected) {
            currentpage ++;//查询下一页
            requestData(searchTag, currentpage);
            Toast.makeText(getActivity(),"正在加载更多！", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(),"网络连接错误！", Toast.LENGTH_LONG).show();
        }
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

        huaBanPresenter = new HuaBanPresenter(getActivity());
        huaBanPresenter.onCreate();
        huaBanPresenter.attachView(huanBanView);

        //注册EventBus接收网络状态改变广播通知
        EventBus.getDefault().register(this);

        mRecyclerView.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addOnScrollListener(new HuaBanLoadMoreScrollListener());
        mAdapter = new HuaBanImageWaterFallLoadMoreAdapter(mActivity, this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMyItemClickListener(new OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
//                Toast.makeText(getActivity(), mAdapter.getList().get(pos).getTheme(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(getActivity(), HuaBanSingleImgShowActivity.class);
                intent.putExtra("data", mAdapter.getList().get(pos));
                startActivity(intent);
            }

            @Override
            public void mLongClick(View v, int pos) {

            }
        });

        fragmentType = (HuaBanFragmentType) getArguments().getSerializable(ARG_PARAM1);
        searchTag = getArguments().getString(ARG_PARAM2);

        if (NetWorkUtil.isNetworkAvailable(getActivity())) {
            requestData(searchTag,currentpage);
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
        Toast.makeText(getActivity(), msg.getExtramsg(),Toast.LENGTH_SHORT).show();
    }

    private void requestData(String tag, int currentpage) {
        huaBanPresenter.getHuaBanImageResource(fragmentType,tag,currentpage);
    }

}
