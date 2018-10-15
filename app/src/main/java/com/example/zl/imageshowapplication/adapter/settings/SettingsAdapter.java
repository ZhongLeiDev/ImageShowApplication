package com.example.zl.imageshowapplication.adapter.settings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhongLeiDev on 2018/10/15.
 * 设置页面的 RecycleView 绑定的 Adapter
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsViewHolder>{

    private static final String TAG = "SettingAdapter";

    private List<Integer> viewHolderTypes = new ArrayList<>();
    private SparseArrayCompat<Integer> titleIndexs = new SparseArrayCompat<>();
    private Context mContext;

    public SettingsAdapter(Context ctx) {
        this.mContext = ctx;
    }

    @Override
    public int getItemViewType(int position) {
        return viewHolderTypes.get(position);
    }

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SettingsViewHolder.create(parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
        if (holder instanceof ViewHolderHeader) {
            ((ViewHolderHeader) holder).setHeader(titleIndexs.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return viewHolderTypes.size();
    }

    public void addViewHolderType(int... type) {
        for (int i = 0; i < type.length; i++) {
            if (type[i] == SettingsViewHolder.VIEW_HOLDER_HEADER) {
                titleIndexs.put(i, titleIndexs.size() + 1);
            }
            viewHolderTypes.add(type[i]);
        }
        notifyDataSetChanged();
    }

}
