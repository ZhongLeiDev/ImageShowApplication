package com.example.zl.leancloud;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.example.zl.enums.FooterShowType;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.activity.HuaBanBoardItemsShowActivity;
import com.example.zl.imageshowapplication.activity.HuaBanSingleImgShowActivity;
import com.example.zl.imageshowapplication.adapter.huaban.HuaBanImageWaterFallLoadMoreAdapter;
import com.example.zl.imageshowapplication.base.BaseFragment;
import com.example.zl.imageshowapplication.bean.huaban.transobj.HBImageBean;
import com.example.zl.imageshowapplication.message.BaseMessage;
import com.example.zl.imageshowapplication.message.MsgEnums;
import com.example.zl.imageshowapplication.myinterface.LoadMoreListener;
import com.example.zl.imageshowapplication.myinterface.OnMyItemClickListener;
import com.example.zl.imageshowapplication.myinterface.listenerinstance.CollectionScrollListener;
import com.example.zl.imageshowapplication.utils.NetWorkUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/10/9.
 * 收藏基础显示 Fragment
 */

public class CollectionBaseFragment extends BaseFragment implements LoadMoreListener {

    private static final String TAG = "CollectionBaseFragment";

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private CollectionFragmentAdapter mAdapter;

    /**当前页面角标*/
    private int currentPageIndex = 0;
    /**当前点击的 Item 位置*/
    private int currentPosition = -1;

    private boolean isNetWorkConnected = false;

    // Fragment 传参参数名
    private static final String ARG_PARAM1 = "param1";

    private CollectionType collectionType;

    /**错误事件监听器*/
    private View.OnClickListener onErrorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dataLoad();
        }
    };

    private CollectionPresenter collectionPresenter;

    private CollectionView collectionView = new CollectionView() {
        @Override
        public void onError(String error) {
            if (error.contains("查询")) {
                setState(FooterShowType.ERROR); //查询失败
            } else {
                Toast.makeText(getSafeActivity(),error,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onSucceed() {
            mAdapter.getList().remove(getCurrentPosition());
            mAdapter.notifyDataSetChanged();
            Toast.makeText(getSafeActivity(),"删除成功！",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onQueryCompleted(List<CollectionBean> beanList) {

            if (beanList.size() == 0) {
                setState(FooterShowType.END);   //没有更多数据
            } else {
                mAdapter.getList().addAll(beanList);
                mAdapter.notifyDataSetChanged();
                setState(FooterShowType.NORMAL);    //加载完成，正常显示
            }

        }
    };

    /**加载更多模块初始显示状态为数据正常显示状态*/
    protected FooterShowType mState = FooterShowType.NORMAL;

    /**设置底部加载更多的样式*/
    protected void setState(FooterShowType type) {
        mState = type;
        getSafeActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                changeAdapterState();
            }
        });
    }

    /**改变底部加载更多模块的样式*/
    protected void changeAdapterState() {
        if (mAdapter != null && mAdapter.mFooterHolder != null) {
            mAdapter.mFooterHolder.setData(mState);
        }

    }

    private void setCurrentPosition(int position) {
        this.currentPosition = position;
    }

    private int getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * 获取 CollectionBaseFragment 实例
     * @param type 收藏显示 Fragment 类型
     * @return
     */
    public static CollectionBaseFragment newInstance(CollectionType type) {
        CollectionBaseFragment fragment = new CollectionBaseFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void loadMoreData() {
        if (isNetWorkConnected) {
            currentPageIndex ++;//查询下一页
            dataLoad();
        } else {
            setState(FooterShowType.ERROR);
            Toast.makeText(getSafeActivity(),"网络连接错误！", Toast.LENGTH_LONG).show();
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

        collectionPresenter = new CollectionPresenter();
        collectionPresenter.onAttachView(collectionView);

        //注册EventBus接收网络状态改变广播通知
        EventBus.getDefault().register(this);

        mRecyclerView.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addOnScrollListener(new CollectionScrollListener());
        mAdapter = new CollectionFragmentAdapter(mActivity, this, onErrorListener);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMyItemClickListener(new OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                handleClick(pos);
            }

            @Override
            public void mLongClick(View v, int pos) {
                setCurrentPosition(pos);
                showPopupMenu(v,pos);
            }
        });

        collectionType = (CollectionType) getArguments().getSerializable(ARG_PARAM1);

        if (NetWorkUtil.isNetworkAvailable(getSafeActivity())) {
            dataLoad();
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
    }

    /**
     * 加载数据
     */
    private void dataLoad() {
        setState(FooterShowType.LOADING);   //加载更多
        collectionPresenter.queryCollections(collectionType, AVUser.getCurrentUser().getObjectId(),currentPageIndex);
    }

    /**
     * 点击事件处理
     * @param position 当前点击位置
     */
    private void handleClick(int position) {
        switch (collectionType) {
            case COLLECTON_TYPE_PIN:
                Intent intent = new Intent();
                intent.setClass(getSafeActivity(), HuaBanSingleImgShowActivity.class);
                HBImageBean bean = new HBImageBean();
                bean.setUrl(mAdapter.getList().get(position).getUrl());
                intent.putExtra("data",bean);
                startActivity(intent);
                break;
            case COLLECTON_TYPE_BOARD:
                Intent bintent = new Intent();
                bintent.setClass(getSafeActivity(), HuaBanBoardItemsShowActivity.class);
                bintent.putExtra("data", Long.parseLong(mAdapter.getList().get(position).getUrl()));
                startActivity(bintent);
                break;
            case COLLECTON_TYPE_AUTHOR:
                //TODO 添加作者收藏处理逻辑
                break;
        }
    }

    /**
     * 显示 PopupMenu
     * @param view 显示的位置
     */
    private void showPopupMenu(View view, final int position) {

        PopupMenu popupMenu = new PopupMenu(getSafeActivity(),view);
        popupMenu.getMenuInflater().inflate(R.menu.huaban_menu,popupMenu.getMenu());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popupMenu.setGravity(Gravity.CENTER); //设置显示位置
        }

        popupMenu.getMenu().findItem(R.id.huaban_go_board).setVisible(false);
        popupMenu.getMenu().findItem(R.id.huaban_collect).setVisible(false);
        popupMenu.getMenu().findItem(R.id.huaban_collect_board).setVisible(false);
        popupMenu.getMenu().findItem(R.id.bcy_collect_coser).setVisible(false);

        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                collectionPresenter.deleteCollection(
                        collectionType,AVUser.getCurrentUser().getObjectId(),
                        mAdapter.getList().get(position));
                return true;
            }
        });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // 控件消失时的事件
            }
        });

    }

}
