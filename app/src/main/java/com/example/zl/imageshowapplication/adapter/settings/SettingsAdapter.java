package com.example.zl.imageshowapplication.adapter.settings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.utils.AvatarSelectUtil;
import com.example.zl.locallogin.LocalUserHandle;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by ZhongLeiDev on 2018/10/15.
 * 设置页面的 RecycleView 绑定的 Adapter
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsViewHolder>{

    private static final String TAG = "SettingAdapter";

    private List<Integer> viewHolderTypes = new ArrayList<>();
    private SparseArrayCompat<Integer> titleIndexs = new SparseArrayCompat<>();
    private Context mContext;

    //点击操作监听事件
    private View.OnClickListener onAvatarClickListener;
    private View.OnClickListener onVersionClickListener;
    private View.OnClickListener onAboutClickListener;

    public SettingsAdapter(Context ctx, View.OnClickListener onAvatar,
                           View.OnClickListener onVersion, View.OnClickListener onAbout) {
        this.mContext = ctx;
        this.onAvatarClickListener = onAvatar;
        this.onVersionClickListener = onVersion;
        this.onAboutClickListener = onAbout;
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
        } else if (holder instanceof ViewHolderAccount) {
            if (LocalUserHandle.currentUser() != null){
                AvatarSelectUtil.setAvatarWithPath1(
                        mContext,((ViewHolderAccount) holder).getAvatarImageView(),
                        AvatarSelectUtil.buildAvatarPath(LocalUserHandle.currentUser().getUserName()));
                ((ViewHolderAccount) holder).setAccount(LocalUserHandle.currentUser().getUserName());
            } else {
                Glide.with(mContext).load(R.drawable.default_user)  //圆形显示
                        .transition(withCrossFade())    //渐隐特效显示
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(((ViewHolderAccount) holder).getAvatarImageView());
                ((ViewHolderAccount) holder).setAccount("未设置");
            }

            ((ViewHolderAccount) holder).setOnAvatarClick(onAvatarClickListener);

        } else if (holder instanceof ViewHolderOther) {
            ((ViewHolderOther) holder).setOnVersionClick(onVersionClickListener);
            ((ViewHolderOther) holder).setOnAboutClick(onAboutClickListener);
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
