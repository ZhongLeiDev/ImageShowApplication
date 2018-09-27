package com.example.zl.imageshowapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zl.enums.AlbumFragmentType;
import com.example.zl.enums.HuaBanFragmentType;
import com.example.zl.enums.PictureFragmentType;
import com.example.zl.imageshowapplication.activity.searchresult.HuaBanSearchResultActivity;
import com.example.zl.imageshowapplication.activity.searchresult.SearchResultActivity;
import com.example.zl.imageshowapplication.adapter.common.FragmentAdapter;
import com.example.zl.imageshowapplication.base.BaseAlbumInfoFragment;
import com.example.zl.imageshowapplication.base.BaseHuaBanImageFragment;
import com.example.zl.imageshowapplication.base.BasePictureInfoFragment;
import com.example.zl.imageshowapplication.fragment.bcy.BcyWorksWaterFallLoadMoreFragment;
import com.example.zl.imageshowapplication.fragment.geek.GeekWaterFallFragment;
import com.example.zl.imageshowapplication.fragment.geek.GeekWaterFallLoadMoreFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 入口 Activity
 */
public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private DrawerLayout mDrawerLayout;
    private FragmentAdapter mFragmentAdapter;

    @Bind(R.id.main_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.main_tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.main_viewpager)
    ViewPager mViewPager;
    @Bind(R.id.main_edt_search)
    EditText mEditTextSearch;
    @Bind(R.id.main_image_backdrop)
    ImageView mImageBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        setSupportActionBar(mToolbar);

        initData();

        initSearchView();

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.menu3);  //设置 ActionBar 的 Home 键
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView!=null) {
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            Snackbar.make(mDrawerLayout,item.getTitle() + "pressed", Snackbar.LENGTH_LONG).show();
                            item.setChecked(true);
                            mDrawerLayout.closeDrawers();
                            return true;
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*Activity退出后清除缓存*/
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();
        Log.i("MainActivity", "OnDestroy...");
    }

    private void initSearchView() {
        mEditTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && mEditTextSearch.getText().toString().length()!=0) {
                    startSearchActivity(mEditTextSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });
        mEditTextSearch.setOnTouchListener(this);

    }

    /**
     * 开启 BCY 搜索 Activity
     * @param searchTag 查询条件
     */
    private void startSearchActivity(String searchTag) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SearchResultActivity.class);
        intent.putExtra("data", (Serializable)searchTag);
        startActivity(intent);
    }

    /**
     * 开启 HuaBan 搜索 Activity
     * @param searchTag 查询条件
     */
    private void startHuaBanSearchActivity(String searchTag) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, HuaBanSearchResultActivity.class);
        intent.putExtra("data", (Serializable)searchTag);
        startActivity(intent);
    }

    private void initData(){

        List<String> listStr = new ArrayList<String>();
        listStr.add("主页");
        listStr.add("Geek");
        listStr.add("Works");
        listStr.add("HuaBan1");
        listStr.add("HuaBan2");
        listStr.add("HuaBan3");

        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(4)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(5)));

        List<Fragment> fragments = new ArrayList<Fragment>();
//        fragments.add(new BcyPicturesWaterFallLoadMoreFragment());
        fragments.add(BasePictureInfoFragment.newInstance(PictureFragmentType.PICTURE_FRAGMENT_RANDOM,"empty"));
        fragments.add(new GeekWaterFallLoadMoreFragment());
//        fragments.add(new BcyWorksWaterFallLoadMoreFragment());
        fragments.add(BaseAlbumInfoFragment.newInstance(AlbumFragmentType.ALBUM_FRAGMENT_RANDOM,"empty"));
        fragments.add(BaseHuaBanImageFragment.newInstance(HuaBanFragmentType.HUABAN_FRAGMENT_ILLUSTRATION,"empty"));
        fragments.add(BaseHuaBanImageFragment.newInstance(HuaBanFragmentType.HUABAN_FRAGMENT_ANIM,"empty"));
        fragments.add(BaseHuaBanImageFragment.newInstance(HuaBanFragmentType.HUABAN_FRAGMENT_BEAUTY,"empty"));

        mFragmentAdapter = new FragmentAdapter(
                getSupportFragmentManager(), fragments,listStr);
        mViewPager.setAdapter(mFragmentAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                /*extView.setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom)*/
                Drawable drawableRight = mEditTextSearch.getCompoundDrawables()[2];
                if (drawableRight != null
                        && event.getRawX() <= (mEditTextSearch.getRight() + drawableRight.getBounds().width())
                        && mEditTextSearch.getText().toString().length()!=0) {
                    if (mTabLayout.getSelectedTabPosition()<3) {
                        startSearchActivity(mEditTextSearch.getText().toString());
                    } else {
                        startHuaBanSearchActivity(mEditTextSearch.getText().toString());
                    }
                    v.performClick();
                    return true;
                }
                break;
        }
        return false;
    }
}
