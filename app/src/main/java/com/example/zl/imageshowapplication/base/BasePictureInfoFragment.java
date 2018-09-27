package com.example.zl.imageshowapplication.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zl.enums.PictureFragmentType;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.activity.ZoomPictureListProgressActivity;
import com.example.zl.imageshowapplication.adapter.bcy.BcyPicturesWaterFallLoadMoreAdapter;
import com.example.zl.imageshowapplication.bean.bcy.retro.PictureInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.linkanalyzestrategy.retrofits.RetrofitFactory;
import com.example.zl.imageshowapplication.message.BaseMessage;
import com.example.zl.imageshowapplication.message.MsgEnums;
import com.example.zl.imageshowapplication.myinterface.listenerinstance.BcyPicturesLoadMoreScrollListener;
import com.example.zl.imageshowapplication.myinterface.LoadMoreListener;
import com.example.zl.imageshowapplication.myinterface.OnMyItemClickListener;
import com.example.zl.imageshowapplication.myinterface.RetrofitInfoService;
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
 * 图片显示 Fragment 基类
 */

public class BasePictureInfoFragment extends BaseFragment implements LoadMoreListener {
    private static final String TAG = "BasePictureInfoFragment";

    private RetrofitInfoService bcyInfoService = RetrofitFactory.getBcyRetroSingleInstance();

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private BcyPicturesWaterFallLoadMoreAdapter mAdapter;
    private boolean isNetWorkConnected = false;
    private int currentpage = 0;

    // Fragment 传参参数名
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //参数值
    private PictureFragmentType fragmentType;
    private String searchTag;

    /**
     * 获取 BasePictureFragment 实例
     * @param ftype Fragment 类型
     * @param tag 查询条件
     * @return
     */
    public static BasePictureInfoFragment newInstance(PictureFragmentType ftype, String tag) {
        BasePictureInfoFragment fragment = new BasePictureInfoFragment();
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
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mRecyclerView.addOnScrollListener(new BcyPicturesLoadMoreScrollListener());
        mAdapter = new BcyPicturesWaterFallLoadMoreAdapter(mActivity, this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMyItemClickListener(new OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                Log.i(TAG,"URL->" + mAdapter.getList().get(pos).getPictureUrl() + " is pressed!!!");
                Intent intent = new Intent();
//                intent.setClass(getActivity(), BcyPicturesListPagerImageViewActivity.class);
                intent.setClass(getActivity(), ZoomPictureListProgressActivity.class);
                intent.putExtra("data", (Serializable)mAdapter.getList());
                intent.putExtra("position", pos);
                startActivity(intent);
            }

            @Override
            public void mLongClick(View v, int pos) {
                Log.i(TAG,"URL->" + mAdapter.getList().get(pos).getPictureUrl() + " is long pressed!!!");
            }
        });

        fragmentType = (PictureFragmentType) getArguments().getSerializable(ARG_PARAM1);
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
//        Toast.makeText(getActivity(), msg.getExtramsg(),Toast.LENGTH_SHORT).show();
    }

    private void requestData(String tag, int currentpage) {

        Call<ResultVO<List<PictureInfo>>> call = null;

        switch (fragmentType) {
            case PICTURE_FRAGMENT_RANDOM:
                call = bcyInfoService.getBcyRandomPictures(30);
                break;
            case PICTURE_FRAGMENT_SEARCH_BY_TAG:
                call = bcyInfoService.getBcySearchPictures(tag, currentpage,30);
                break;
            case PICTURE_FRAGMENT_SEARCH_BY_DESC:
                break;
        }

        call.enqueue(new Callback<ResultVO<List<PictureInfo>>>() {

            public void onResponse(Call<ResultVO<List<PictureInfo>>> call, Response<ResultVO<List<PictureInfo>>> response) {
                if (response.body() != null) {
                    List<PictureInfo> list = response.body().getData();
                    System.out.println("LoadMoreCall->" + response.body());
                    if (list.size() > 0) {
                        mAdapter.getList().addAll(list);
//                        mAdapter.getRandomHeight(list);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "没有更多内容！", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "error:返回内容为空！", Toast.LENGTH_LONG).show();
                }

            }

            public void onFailure(Call<ResultVO<List<PictureInfo>>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(),"网络连接错误！", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void loadMoreData() {

        if (isNetWorkConnected) {
            currentpage ++;//查询下一页
            requestData(searchTag, currentpage);
            Toast.makeText(getActivity(),"正在加载更多！", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(),"网络连接错误！", Toast.LENGTH_LONG).show();
        }

    }
}
