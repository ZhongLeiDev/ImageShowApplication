package com.example.zl.locallogin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.loadmorefooter.FooterHolder;
import com.example.zl.imageshowapplication.myinterface.LoadMoreListener;
import com.example.zl.imageshowapplication.myinterface.OnMyItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.zl.imageshowapplication.config.UILConfig.NORMAL_OPTION;

/**
 * Created by ZhongLeiDev on 2018/10/9.
 * 收藏显示 Adapter
 */

public class CollectionFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<CollectionBean> mList = new ArrayList<>();

    private OnMyItemClickListener listener;

    private LoadMoreListener loadMoreListener;

    private View.OnClickListener onErrorClick;

    /*----------------加载更多模块----------------*/
    private final int NORMALLAYOUT = 0;
    private final int FOOTERLAYOUT = 1;
    public FooterHolder mFooterHolder;

    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;
    }

    /**
     * 构造函数
     * @param ctx 上下文
     * @param lmListener 加载更多监听器
     * @param onErrorClickListener 错误事件监听器
     */
    public CollectionFragmentAdapter(Context ctx, LoadMoreListener lmListener,
                                               View.OnClickListener onErrorClickListener) {
        mContext = ctx;
        loadMoreListener = lmListener;
        onErrorClick = onErrorClickListener;
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
            return new CollectionViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.footer_loadmore_layout, parent, false);
            mFooterHolder = new FooterHolder(view);
            mFooterHolder.setOnErrorHandle(onErrorClick);
            return mFooterHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof CollectionViewHolder) {
            CollectionBean bean = mList.get(position);
            ImageLoader.getInstance().displayImage(bean.getCover(),
                    ((CollectionViewHolder) holder).mImageView,NORMAL_OPTION);

            if (listener != null) {
                ((CollectionViewHolder) holder).mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.myClick(v, position);
                    }
                });


                // set LongClick
                ((CollectionViewHolder) holder).mImageView.setOnLongClickListener(new View.OnLongClickListener() {
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

    public List<CollectionBean> getList() {
        return mList;
    }

    public static class CollectionViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.imageview)
        ImageView mImageView;

        public CollectionViewHolder(View view){
            //需要设置super
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
