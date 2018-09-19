package com.example.zl.imageshowapplication.adapter.bcy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.bean.bcy.retro.PictureInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.example.zl.imageshowapplication.config.UILConfig.NORMAL_OPTION;

/**
 * Created by ZhongLeiDev on 2018/9/7.
 * BCY 单个相册里面包含的所有图片显示 Adapter
 */

public class BcyAlbumPicturesListAdapter extends BaseAdapter {

    private List<PictureInfo> pictureInfoList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater layoutInflater;

    public BcyAlbumPicturesListAdapter(Context context) {
        mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
    }

    public List<PictureInfo> getPictureInfoList() {
        return pictureInfoList;
    }

    @Override
    public int getCount() {
        return pictureInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return pictureInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View layout = layoutInflater.inflate(R.layout.album_pictures_list_item,null);
        ImageView imageView = layout.findViewById(R.id.picture_item);
        PictureInfo pictureInfo = pictureInfoList.get(position);
        ImageLoader.getInstance().displayImage(pictureInfo.getPictureUrl(),
                imageView, NORMAL_OPTION);
        return layout;
    }

}
