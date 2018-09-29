package com.example.zl.imageshowapplication.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.zl.commoncomponent.SimpleToolBar;
import com.example.zl.enums.HuaBanFragmentType;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.base.BaseHuaBanImageFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/9/28.
 * HuaBan 画板内容显示 Activity
 */

public class HuaBanBoardItemsShowActivity extends AppCompatActivity {

    private static final String TAG = "HuaBanBoard";
    @Bind(R.id.huaban_search_simple_toolbar)
    SimpleToolBar toolBar;
    @Bind(R.id.huaban_search_container)
    LinearLayout layout;

    private long boardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.layout_huaban_searchresult_activity);

        ButterKnife.bind(this);

        boardId = getIntent().getLongExtra("data",0);

        initView(boardId);

    }

    private void initView(long boardId) {

        toolBar.setMainTitle("画板");

        FragmentManager fragmentManager = getSupportFragmentManager();  //getSupportedFragmentManager才是v4版本的方法
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        BaseHuaBanImageFragment fragment = BaseHuaBanImageFragment.newInstance(
                HuaBanFragmentType.HUA_BAN_FRAGMENT_BOARD,"empty",boardId);
        transaction.add(R.id.huaban_search_container,fragment);

        transaction.commit();

    }

}
