package com.example.zl.imageshowapplication.activity.collection;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.example.zl.commoncomponent.SimpleToolBar;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.adapter.common.FragmentAdapter;
import com.example.zl.imageshowapplication.utils.BcyActivityManager;
import com.example.zl.leancloud.CollectionBaseFragment;
import com.example.zl.leancloud.CollectionType;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/10/9.
 * 收藏显示Activity
 */

public class CollectionShowActivity extends AppCompatActivity {

    private static final String TAG = "CollectionShow";

    @Bind(R.id.search_simple_toolbar)
    SimpleToolBar mToolBar;
    @Bind(R.id.search_tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.search_view_pager)
    ViewPager mViewPager;

    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.layout_searchresult_activity);

        //将当前 Activity 纳入管理
        BcyActivityManager.getActivityManager().addActivity(this);

        ButterKnife.bind(this);

        initView();

        initData();

    }

    private void initView() {

        mToolBar.setMainTitle(getString(R.string.nav_collection));//设置标题

        mToolBar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void initData() {

        List<String> listStr = new ArrayList<String>();
        listStr.add("图片");
        listStr.add("画板");
        listStr.add("作者");

        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(2)));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(CollectionBaseFragment.newInstance(CollectionType.COLLECTON_TYPE_PIN));
        fragments.add(CollectionBaseFragment.newInstance(CollectionType.COLLECTON_TYPE_BOARD));
        fragments.add(CollectionBaseFragment.newInstance(CollectionType.COLLECTON_TYPE_AUTHOR));

        mFragmentAdapter = new FragmentAdapter(
                getSupportFragmentManager(), fragments,listStr);
        mViewPager.setAdapter(mFragmentAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

    }

}
