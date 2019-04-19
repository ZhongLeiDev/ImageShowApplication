package com.example.zl.imageshowapplication.activity.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.activity.settings.AvatarChangeActivity;
import com.example.zl.imageshowapplication.adapter.settings.SettingsAdapter;
import com.example.zl.imageshowapplication.adapter.settings.SettingsViewHolder;
import com.example.zl.imageshowapplication.utils.BcyActivityManager;
import com.example.zl.locallogin.LocalUserHandle;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/10/12.
 * 应用设置页面显示 Activity
 */

public class SettingsActivity extends AppCompatActivity {

    @Bind(R.id.setting_layout_recycler)
    public RecyclerView recyclerView;

    private SettingsAdapter adapter;

    private View.OnClickListener onAvatar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (LocalUserHandle.currentUser() != null) {
                startActivity(new Intent(SettingsActivity.this, AvatarChangeActivity.class));
            } else {
                Toast.makeText(SettingsActivity.this,"登陆后才可以进行头像设置！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener onVersion = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onAbout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

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
        adapter = new SettingsAdapter(this,onAvatar,onVersion,onAbout);
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

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged(); //刷新界面
    }

}
