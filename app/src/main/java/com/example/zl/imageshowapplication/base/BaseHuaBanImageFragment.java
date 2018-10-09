package com.example.zl.imageshowapplication.base;

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
import com.example.zl.enums.HuaBanFragmentType;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.activity.HuaBanBoardItemsShowActivity;
import com.example.zl.imageshowapplication.activity.HuaBanSingleImgShowActivity;
import com.example.zl.imageshowapplication.adapter.huaban.HuaBanImageWaterFallLoadMoreAdapter;
import com.example.zl.imageshowapplication.bean.huaban.transobj.HBImageBean;
import com.example.zl.imageshowapplication.message.BaseMessage;
import com.example.zl.imageshowapplication.message.MsgEnums;
import com.example.zl.imageshowapplication.myinterface.LoadMoreListener;
import com.example.zl.imageshowapplication.myinterface.OnMyItemClickListener;
import com.example.zl.imageshowapplication.myinterface.listenerinstance.HuaBanLoadMoreScrollListener;
import com.example.zl.imageshowapplication.utils.NetWorkUtil;
import com.example.zl.imageshowapplication.mvp.huaban.presenter.HuaBanPresenter;
import com.example.zl.imageshowapplication.mvp.huaban.view.HuanBanView;
import com.example.zl.leancloud.CollectionBean;
import com.example.zl.leancloud.CollectionPresenter;
import com.example.zl.leancloud.CollectionView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

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
    private long lastPinId = 0;

    // Fragment 传参参数名
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    //参数值
    private HuaBanFragmentType fragmentType;
    private String searchTag;
    private long boardId;
    /**元素数量，如果两次查询完成之后元素数量不变，则可以判断为没有更多数据状态*/
    private long itemCount = 0;
    /**数据是否初始化*/
    private boolean isInited = false;

    /** HuaBan Image 处理逻辑 Presenter 类*/
    private HuaBanPresenter huaBanPresenter;

    /**收藏处理逻辑 Presenter 类*/
    private CollectionPresenter collectionPresenter;

    /**错误事件监听器*/
    private View.OnClickListener onErrorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isInited) {
                dataLoadMore();
            } else {
                dataLoadInit();
            }
        }
    };

    /** HuaBan Image 显示逻辑 View 类*/
    private HuanBanView huanBanView = new HuanBanView() {
        @Override
        public void onNext(HBImageBean bean) {
            mAdapter.getList().add(bean);
//            mAdapter.notifyDataSetChanged();  //全局刷新
            mAdapter.notifyItemChanged(mAdapter.getItemCount()-2,0);//局部刷新
            lastPinId = bean.getPin_id();   //存储最后一 PIN 的 pinId
        }

        @Override
        public void onError(String error) {
            setState(FooterShowType.ERROR);
//            Toast.makeText(getSafeActivity(), error,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCompleted(String complete) {
            if (!isInited) {
                isInited = true;
            }
            if (mAdapter.getItemCount() != itemCount) {
                setState(FooterShowType.NORMAL);    //加载完成，正常显示
            } else {
                setState(FooterShowType.END);   //没有更多数据
            }
            itemCount = mAdapter.getItemCount();
        }
    };

    /**收藏显示逻辑 View 类*/
    private CollectionView collectionView = new CollectionView() {
        @Override
        public void onError(String error) {
            Toast.makeText(getSafeActivity(), error,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSucceed() {
            Toast.makeText(getSafeActivity(), "收藏成功！",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onQueryCompleted(List<CollectionBean> beanList) {

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

    /**
     * 获取 BaseHuaBanImageFragment 实例
     * @param type Fragment 类型
     * @param tag 查询条件【针对 HuaBanFragmentType.HUABAN_FRAGMENT_SEARCH, 其它类型可以填任意值】
     * @param boardId 查询画册 ID 【HuaBanFragmentType.HUABAN_FRAGMENT_BOARD, 其它类型可填任意值】
     * @return
     */
    public static BaseHuaBanImageFragment newInstance(HuaBanFragmentType type, String tag, long boardId) {

        BaseHuaBanImageFragment fragment = new BaseHuaBanImageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,type);
        args.putString(ARG_PARAM2,tag);
        args.putLong(ARG_PARAM3,boardId);
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void loadMoreData() {
        Log.i(TAG,"Start Load More ... ！");
        if (isNetWorkConnected) {
            currentpage ++;//查询下一页
            dataLoadMore();
//            Toast.makeText(getSafeActivity(),"正在加载更多！", Toast.LENGTH_LONG).show();
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

        huaBanPresenter = new HuaBanPresenter(getSafeActivity());
        huaBanPresenter.onCreate();
        huaBanPresenter.attachView(huanBanView);

        collectionPresenter = new CollectionPresenter();
        collectionPresenter.onAttachView(collectionView);

        //注册EventBus接收网络状态改变广播通知
        EventBus.getDefault().register(this);
        mRecyclerView.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addOnScrollListener(new HuaBanLoadMoreScrollListener());
        mAdapter = new HuaBanImageWaterFallLoadMoreAdapter(mActivity, this, onErrorListener);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMyItemClickListener(new OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
//                Toast.makeText(getSafeActivity(), mAdapter.getList().get(pos).getTheme(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(getSafeActivity(), HuaBanSingleImgShowActivity.class);
                intent.putExtra("data", mAdapter.getList().get(pos));
                startActivity(intent);
            }

            @Override
            public void mLongClick(View v, int pos) {
                showPopupMenu(v,pos);
            }
        });

        fragmentType = (HuaBanFragmentType) getArguments().getSerializable(ARG_PARAM1);
        searchTag = getArguments().getString(ARG_PARAM2);
        boardId = getArguments().getLong(ARG_PARAM3);

        if (NetWorkUtil.isNetworkAvailable(getSafeActivity())) {
            dataLoadInit();
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

    /**
     * 初始化数据加载
     */
    private void dataLoadInit() {
        setState(FooterShowType.LOADING);   //加载更多
        huaBanPresenter.getHuaBanImageResource(fragmentType,searchTag,boardId);
    }

    /**
     * 加载更多
     */
    private void dataLoadMore() {
        setState(FooterShowType.LOADING);   //加载更多
        huaBanPresenter.getHuaBanImageResource_LoadMore(fragmentType,boardId,lastPinId,searchTag,currentpage);
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

        switch (fragmentType) { //配置在不同的 Fragment 显示不同的选项
            case HUABAN_FRAGMENT_BOARD:
                popupMenu.getMenu().findItem(R.id.huaban_go_board).setVisible(false);
                popupMenu.getMenu().findItem(R.id.bcy_collect_coser).setVisible(false);
                popupMenu.getMenu().findItem(R.id.collection_delete).setVisible(false);
                break;
            default:
                popupMenu.getMenu().findItem(R.id.huaban_collect_board).setVisible(false);
                popupMenu.getMenu().findItem(R.id.bcy_collect_coser).setVisible(false);
                popupMenu.getMenu().findItem(R.id.collection_delete).setVisible(false);
                break;
        }

        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.huaban_go_board:
                        Intent intent = new Intent();
                        intent.setClass(getSafeActivity(), HuaBanBoardItemsShowActivity.class);
                        intent.putExtra("data", mAdapter.getList().get(position).getBoard_id());
                        startActivity(intent);
                        break;
                    case R.id.huaban_collect:
                        if (AVUser.getCurrentUser() != null) {
                            collectionPresenter.collectPins(AVUser.getCurrentUser().getObjectId(),
                                    mAdapter.getList().get(position).getUrl());
                        } else {
                            Toast.makeText(getSafeActivity(),"请登录后再使用图片收藏功能！",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.huaban_collect_board:
                        if (AVUser.getCurrentUser() != null) {
                            collectionPresenter.collectBoard(AVUser.getCurrentUser().getObjectId(),
                                    String.valueOf(mAdapter.getList().get(position).getBoard_id()),
                                    mAdapter.getList().get(position).getUrl());
                        } else {
                            Toast.makeText(getSafeActivity(),"请登录后再使用画板收藏功能！",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
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
