package com.example.zl.imageshowapplication.activity.searchresult;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.widget.SimpleToolBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/9/18.
 * 搜索结果显示Activity
 */

public class SearchResultActivity extends AppCompatActivity {

    @Bind(R.id.simple_toolbar)
    SimpleToolBar mToolBar;
    @Bind(R.id.search_tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.search_view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_searchresult_activity);

        ButterKnife.bind(this);

    }

}
