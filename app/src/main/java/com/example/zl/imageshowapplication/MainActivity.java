package com.example.zl.imageshowapplication;

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
import android.view.Menu;
import android.view.MenuItem;

import com.example.zl.imageshowapplication.adapter.FragmentAdapter;
import com.example.zl.imageshowapplication.fragment.GeekWaterFallFragment;
import com.example.zl.imageshowapplication.fragment.GeekWaterFallLoadMoreFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private FragmentAdapter mFragmentAdapter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        setSupportActionBar(mToolbar);

        initData();

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

    private void initData(){

        List<String> listStr = new ArrayList<String>();
        listStr.add("主页");
        listStr.add("Geek");
        listStr.add("HuaBan");
        listStr.add("Pivix");

        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(listStr.get(3)));

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new GeekWaterFallFragment());
        fragments.add(new GeekWaterFallLoadMoreFragment());
        fragments.add(new GeekWaterFallFragment());
        fragments.add(new GeekWaterFallFragment());

        mFragmentAdapter = new FragmentAdapter(
                getSupportFragmentManager(), fragments,listStr);
        mViewPager.setAdapter(mFragmentAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapter);

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
}
