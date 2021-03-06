package com.example.zl.imageshowapplication.adapter.geek;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.bean.geek.GeekImgBean;
import com.example.zl.imageshowapplication.myinterface.OnMyItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.zl.imageshowapplication.config.UILConfig.NORMAL_OPTION;

/**
 * Created by Administrator on 2016/5/31 0031.
 *
 *
 */
public class GeekWaterFallAdapter extends RecyclerView.Adapter<GeekWaterFallAdapter.ViewHolder> {

    private Context mContext;
    private List<GeekImgBean> mList = new ArrayList<>();
//    private List<Integer> mHeights;

    /**自定义点击事件*/
    private OnMyItemClickListener listener;

    /**设置点击事件*/
    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;
    }

    public GeekWaterFallAdapter(Context context){
        this.mContext = context;
    }

//    public void getRandomHeight(List<GeekImgBean> mList){
//        mHeights = new ArrayList<>();
//        for(int i=0; i < mList.size();i++){
//            //随机的获取一个范围为200-600直接的高度
//            mHeights.add((int)(300+ Math.random()*400));
//        }
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_image_text, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
//        layoutParams.height = mHeights.get(position);
        holder.itemView.setLayoutParams(layoutParams);

        GeekImgBean bean = mList.get(position);
        ImageLoader.getInstance().displayImage(bean.getUrl(),
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

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.imageview)
        ImageView mImageView;

        public ViewHolder(View view){
            //需要设置super
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public List<GeekImgBean> getList() {
        return mList;
    }

}
