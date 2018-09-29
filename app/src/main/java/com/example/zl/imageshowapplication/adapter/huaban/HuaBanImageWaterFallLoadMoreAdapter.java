package com.example.zl.imageshowapplication.adapter.huaban;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.bean.huaban.transobj.HBImageBean;
import com.example.zl.imageshowapplication.loadmorefooter.FooterHolder;
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

public class HuaBanImageWaterFallLoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<HBImageBean> mList = new ArrayList<>();
    private int screen_width = 0;
    private int scrren_height = 0;

    private OnMyItemClickListener listener;

    private LoadMoreListener loadMoreListener;

    /*----------------加载更多模块----------------*/
    private final int NORMALLAYOUT = 0;
    private final int FOOTERLAYOUT = 1;
    public FooterHolder mFooterHolder;

    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;
    }

    public HuaBanImageWaterFallLoadMoreAdapter(Context ctx, LoadMoreListener lmListener) {
        mContext = ctx;
        loadMoreListener = lmListener;
        screen_width = CommonUtil.getScreenSize(mContext).x;
        scrren_height = CommonUtil.getScreenSize(mContext).y;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) {
            return FOOTERLAYOUT;
        } else {
            return NORMALLAYOUT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == NORMALLAYOUT) {
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_image_text, parent, false);
            return new HuaBanViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.footer_loadmore_layout, parent, false);
            mFooterHolder = new FooterHolder(view);
            return mFooterHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof HuaBanViewHolder) {
            HBImageBean hbean = mList.get(position);

        /*-------------根据图片宽高进行显示ImageView的缩放,UIL默认自带此功能-------------
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        double scale = (double) hbean.getHeight()/(double)hbean.getWidth();
        layoutParams.height = (int)(screen_width/2 * scale);
        holder.itemView.setLayoutParams(layoutParams);
        */

            ImageLoader.getInstance().displayImage(hbean.getUrl(),
                    ((HuaBanViewHolder) holder).mImageView, NORMAL_OPTION);

            //----------------------------设置点击事件-------------------------------
            if (listener != null) {
                ((HuaBanViewHolder) holder).mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.myClick(v, position);
                    }
                });


                // set LongClick
                ((HuaBanViewHolder) holder).mImageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        listener.mLongClick(v, position);
                        return true;
                    }
                });
            }
        } else if (holder instanceof FooterHolder) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true); //设置 footer 占据整个屏幕宽度
            holder.itemView.setLayoutParams(p);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
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

    public static class HuaBanViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.imageview)
        ImageView mImageView;

        public HuaBanViewHolder(View view){
            //需要设置super
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
