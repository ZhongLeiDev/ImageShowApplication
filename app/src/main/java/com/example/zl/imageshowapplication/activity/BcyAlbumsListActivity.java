package com.example.zl.imageshowapplication.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.zl.commoncomponent.SimpleToolBar;
import com.example.zl.enums.AlbumFragmentType;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.base.BaseAlbumInfoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/9/19.
 * BCY 以列表显示相册的 Activity
 */

public class BcyAlbumsListActivity extends AppCompatActivity {

    private static final String TAG = "BcyAlbumListActivity";
    @Bind(R.id.album_simple_toolbar)
    SimpleToolBar simpleToolBar;
    @Bind(R.id.albumslist)
    LinearLayout linearLayout;

    private String searchTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.albums_list_layout);

        ButterKnife.bind(this);

        searchTag = (String)getIntent().getSerializableExtra("data");

        Log.i(TAG, "SearchTag->" + searchTag);

        initView(searchTag);

        initData();

    }

    private void initView(String tag) {

        simpleToolBar.setMainTitle(tag);//设置标题


        //添加 FragmentManager 和 FragmentTranscation 实例
//        FragmentManager fragmentManager = getFragmentManager(); //不是v4包下的方法
        FragmentManager fragmentManager = getSupportFragmentManager();  //getSupportedFragmentManager才是v4版本的方法
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //用 add 方法添加 Fragment
        BaseAlbumInfoFragment fragment = BaseAlbumInfoFragment.newInstance(
                AlbumFragmentType.ALBUM_FRAGMENT_SEARCH_BY_COSERID,searchTag);
        transaction.add(R.id.albumslist, fragment);

        transaction.commit();
    }

    private void initData() {

    }

}
