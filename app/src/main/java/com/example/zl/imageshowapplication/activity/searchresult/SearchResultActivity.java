package com.example.zl.imageshowapplication.activity.searchresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.zl.enums.AlbumFragmentType;
import com.example.zl.enums.AuthorFragmentType;
import com.example.zl.imageshowapplication.R;
import com.example.zl.commoncomponent.SimpleToolBar;
import com.example.zl.imageshowapplication.activity.RandomSinglePictureShowActivity;
import com.example.zl.imageshowapplication.adapter.common.FragmentAdapter;
import com.example.zl.imageshowapplication.base.BaseAlbumInfoFragment;
import com.example.zl.imageshowapplication.base.BaseCoserInfoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/9/18.
 * BCY 搜索结果显示 Activity
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
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.layout_searchresult_activity);

        ButterKnife.bind(this);

        searchTag = (String)getIntent().getSerializableExtra("data");

        Log.i(TAG, "SearchTag->" + searchTag);

        initView();

        initData();

    }

    private void initView() {

        mToolBar.setMainTitle(searchTag);//设置标题

        mToolBar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchResultActivity.this, RandomSinglePictureShowActivity.class));
            }
        });

    }

    private void initData() {

        List<String> listStr = new ArrayList<String>();
        listStr.add("byCoser");
        listStr.add("byTag");
        listStr.add("ByDesc");

        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(2)));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(BaseCoserInfoFragment.newInstance(AuthorFragmentType.AUTHOR_FRAGMENT_SEARCH_BY_NAME,searchTag));
        fragments.add(BaseAlbumInfoFragment.newInstance(AlbumFragmentType.ALBUM_FRAGMENT_SEARCH_BY_TAG,searchTag));
        fragments.add(BaseAlbumInfoFragment.newInstance(AlbumFragmentType.ALBUM_FRAGMENT_SEARCH_BY_DESC,searchTag));

        mFragmentAdapter = new FragmentAdapter(
                getSupportFragmentManager(), fragments,listStr);
        mViewPager.setAdapter(mFragmentAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapter);

    }

}
