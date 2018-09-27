package com.example.zl.imageshowapplication.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zl.enums.AlbumFragmentType;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.activity.BcyAlbumPicturesListActivity;
import com.example.zl.imageshowapplication.adapter.bcy.BcyWorksWaterFallLoadMoreAdapter;
import com.example.zl.imageshowapplication.bean.bcy.retro.AlbumInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.linkanalyzestrategy.retrofits.RetrofitFactory;
import com.example.zl.imageshowapplication.message.BaseMessage;
import com.example.zl.imageshowapplication.message.MsgEnums;
import com.example.zl.imageshowapplication.myinterface.listenerinstance.BcyWorksLoadMoreScrollListener;
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
 * 相册显示 Fragment 基类
 */

public class BaseAlbumInfoFragment extends BaseFragment implements LoadMoreListener {

    private static final String TAG = "BaseAlbumInfoFragment";

    private RetrofitInfoService bcyInfoService = RetrofitFactory.getBcyRetroSingleInstance();

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private BcyWorksWaterFallLoadMoreAdapter mAdapter;
    private boolean isNetWorkConnected = false;
    private int currentpage = 0;

    // Fragment 传参参数名
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //参数值
    private AlbumFragmentType fragmentType;
    private String searchTag;

    /**
     * 获取 BaseAlbumFragment 实例
     * @param ftype Fragment 类型
     * @param tag 查询条件
     * @return
     */
    public static BaseAlbumInfoFragment newInstance(AlbumFragmentType ftype, String tag) {
        BaseAlbumInfoFragment fragment = new BaseAlbumInfoFragment();
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

        mRecyclerView.addOnScrollListener(new BcyWorksLoadMoreScrollListener());
        mAdapter = new BcyWorksWaterFallLoadMoreAdapter(mActivity, this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMyItemClickListener(new OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                /*相册需要跳转到相册内容显示Activity再跳转到图片放大显示Activity*/
                Log.i(TAG,"URL->" + mAdapter.getList().get(pos).getAlbumCover() + " is pressed!!!");
                Intent intent = new Intent();
                intent.setClass(getActivity(), BcyAlbumPicturesListActivity.class);
                intent.putExtra("data", (Serializable)mAdapter.getList().get(pos).getAlbumId());
                startActivity(intent);
            }

            @Override
            public void mLongClick(View v, int pos) {
                Log.i(TAG,"URL->" + mAdapter.getList().get(pos).getAlbumCover() + " is long pressed!!!");
            }
        });

        fragmentType = (AlbumFragmentType) getArguments().getSerializable(ARG_PARAM1);
        searchTag = getArguments().getString(ARG_PARAM2);

        if (NetWorkUtil.isNetworkAvailable(getActivity())) {
            requestData(searchTag, currentpage);
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

        Call<ResultVO<List<AlbumInfo>>> call = null;

        switch (fragmentType) {
            case ALBUM_FRAGMENT_RANDOM:
                call = bcyInfoService.getBcyRandomAlbums(30);
                break;
            case ALBUM_FRAGMENT_SEARCH_BY_TAG:
                call = bcyInfoService.getBcySearchAlbums(tag,currentpage,30);
                break;
            case ALBUM_FRAGMENT_SEARCH_BY_DESC:
                call = bcyInfoService.getBcySearchAlbums(tag,currentpage,30);
                break;
            case ALBUM_FRAGMENT_SEARCH_BY_COSERID:
                break;
        }

        call.enqueue(new Callback<ResultVO<List<AlbumInfo>>>() {

            public void onResponse(Call<ResultVO<List<AlbumInfo>>> call, Response<ResultVO<List<AlbumInfo>>> response) {
                if (response.body() != null) {
                    List<AlbumInfo> list = response.body().getData();
                    System.out.println("LoadMoreCall->" + response.body());
                    if (list.size() > 0) {
                        mAdapter.getList().addAll(list);
                        mAdapter.getRandomHeight(list);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "没有更多内容！", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "error:返回内容为空！", Toast.LENGTH_LONG).show();
                }

            }

            public void onFailure(Call<ResultVO<List<AlbumInfo>>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(),"网络连接错误！", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void loadMoreData() {

        if (isNetWorkConnected) {
            requestData(searchTag, currentpage);
            Toast.makeText(getActivity(),"正在加载更多！", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(),"网络连接错误！", Toast.LENGTH_LONG).show();
        }

    }

}
