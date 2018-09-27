package com.example.zl.imageshowapplication.adapter.bcy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.bean.bcy.retro.CoserInfo;
import com.example.zl.imageshowapplication.myinterface.LoadMoreListener;
import com.example.zl.imageshowapplication.myinterface.OnMyItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.zl.imageshowapplication.config.UILConfig.NORMAL_OPTION;

/**
 * Created by ZhongLeiDev on 2018/9/19.
 * BCY 作者瀑布流显示 Adapter
 */

public class BcyCoserWaterFallLoadMoreAdapter extends RecyclerView.Adapter<BcyCoserWaterFallLoadMoreAdapter.ViewHolder>{

    private Context mContext;
    private List<CoserInfo> coserInfoList = new ArrayList<>();
    private List<Integer> mHeights;

    /**自定义点击事件*/
    private OnMyItemClickListener listener;

    /**加载更多的监听器*/
    private LoadMoreListener mloadmorelistener;

    /**设置点击事件*/
    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;
    }

    public BcyCoserWaterFallLoadMoreAdapter(Context context, LoadMoreListener loadMoreListener) {
        this.mContext = context;
        this.mloadmorelistener = loadMoreListener;
    }

    public void getRandomHeight(List<CoserInfo> mList){
        if (mHeights == null) {
            mHeights = new ArrayList<>();
        }
        for(int i=0; i < mList.size();i++){
            //对于CoserInfo，将高度设置为固定值250,
            mHeights.add(450);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.author_item_layout, parent, false);
        BcyCoserWaterFallLoadMoreAdapter.ViewHolder viewHolder = new BcyCoserWaterFallLoadMoreAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = mHeights.get(position);
        holder.itemView.setLayoutParams(layoutParams);

        CoserInfo bean = coserInfoList.get(position);
        ImageLoader.getInstance().displayImage(bean.getCoserAvatar(),
                holder.mImageView, NORMAL_OPTION);
        holder.mTextView.setText(bean.getCoserName());

        if (listener!=null) {
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.myClick(v,position);
                }
            });


            // set LongClick
            holder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.mLongClick(v,position);
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return coserInfoList.size();
    }

    public void loadMore() {
        if (mloadmorelistener == null){
            return;
        }
        mloadmorelistener.loadMoreData();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.cpimageView)
        ImageView mImageView;
        @Bind(R.id.cptextView)
        TextView mTextView;

        public ViewHolder(View view){
            //需要设置super
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public List<CoserInfo> getCoserInfoList() {
        return coserInfoList;
    }

}
