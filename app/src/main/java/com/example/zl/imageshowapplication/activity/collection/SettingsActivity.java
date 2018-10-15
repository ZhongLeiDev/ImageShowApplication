package com.example.zl.imageshowapplication.activity.collection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.Window;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.adapter.settings.SettingsAdapter;
import com.example.zl.imageshowapplication.adapter.settings.SettingsViewHolder;
import com.example.zl.imageshowapplication.utils.BcyActivityManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/10/12.
 * 应用设置页面显示 Activity
 */

public class SettingsActivity extends AppCompatActivity {

    @Bind(R.id.setting_layout_recycler)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_layout);

        ButterKnife.bind(this);

        //将当前 Activity 纳入管理
        BcyActivityManager.getActivityManager().addActivity(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.nav_setting));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);

        recyclerView.setLayoutManager(layoutManager);
        SettingsAdapter adapter = new SettingsAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.addViewHolderType(
                SettingsViewHolder.VIEW_HOLDER_HEADER,
                SettingsViewHolder.VIEW_HOLDER_ACCOUNT,
                SettingsViewHolder.VIEW_HOLDER_HEADER,
                SettingsViewHolder.VIEW_HOLDER_STORAGE,
                SettingsViewHolder.VIEW_HOLDER_HEADER,
                SettingsViewHolder.VIEW_HOLDER_SETTING,
                SettingsViewHolder.VIEW_HOLDER_HEADER,
                SettingsViewHolder.VIEW_HOLDER_OTHER
        );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
