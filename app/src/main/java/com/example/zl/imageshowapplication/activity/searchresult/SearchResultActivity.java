package com.example.zl.imageshowapplication.activity.searchresult;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.example.zl.imageshowapplication.R;
import com.example.zl.commoncomponent.SimpleToolBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/9/18.
 * 搜索结果显示Activity
 */

public class SearchResultActivity extends AppCompatActivity {

    private static final String TAG = "SearchResultActivity";

    @Bind(R.id.search_simple_toolbar)
    SimpleToolBar mToolBar;
    @Bind(R.id.search_tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.search_view_pager)
    ViewPager mViewPager;

    private String searchTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.layout_searchresult_activity);

        ButterKnife.bind(this);

        searchTag = (String)getIntent().getSerializableExtra("data");

        Log.i(TAG, "SearchTag->" + searchTag);

        initView(searchTag);

        initData();

    }

    private void initView(String tag) {
        mToolBar.setMainTitle(tag);//设置标题

    }

    private void initData() {

    }

}
