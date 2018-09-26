package com.example.zl.imageshowapplication.adapter.huaban;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.bean.huaban.transobj.HBImageBean;
import com.example.zl.imageshowapplication.myinterface.LoadMoreListener;
import com.example.zl.imageshowapplication.myinterface.OnMyItemClickListener;
import com.example.zl.imageshowapplication.utils.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.zl.imageshowapplication.config.UILConfig.NORMAL_OPTION;

/**
 * Created by ZhongLeiDev on 2018/9/26.
 * HuaBan 瀑布流显示 Adapter
 */

public class HuaBanImageWaterFallLoadMoreAdapter extends RecyclerView.Adapter<HuaBanImageWaterFallLoadMoreAdapter.ViewHolder>{

    private Context mContext;
    private List<HBImageBean> mList = new ArrayList<>();
    private int screen_width = 0;

    private OnMyItemClickListener listener;

    private LoadMoreListener loadMoreListener;

    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;
    }

    public HuaBanImageWaterFallLoadMoreAdapter(Context ctx, LoadMoreListener lmListener) {
        mContext = ctx;
        loadMoreListener = lmListener;
        screen_width = CommonUtil.getScreenSize(mContext).x;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_image_text, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        HBImageBean hbean = mList.get(position);
        double scale = (double) hbean.getHeight()/(double)hbean.getWidth();
        layoutParams.height = (int)(screen_width/2 * scale);
        holder.itemView.setLayoutParams(layoutParams);

        ImageLoader.getInstance().displayImage(hbean.getUrl(),
                holder.mImageView, NORMAL_OPTION);

        //----------------------------设置点击事件-------------------------------
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
        return mList.size();
    }

    public void loadMore() {
        if (loadMoreListener == null){
            return;
        }
        loadMoreListener.loadMoreData();
    }

    public List<HBImageBean> getList() {
        return mList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.imageview)
        ImageView mImageView;

        public ViewHolder(View view){
            //需要设置super
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
