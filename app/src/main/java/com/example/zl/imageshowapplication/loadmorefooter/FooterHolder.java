package com.example.zl.imageshowapplication.loadmorefooter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.zl.enums.FooterShowType;
import com.example.zl.imageshowapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/9/29.
 * 加载更多提示模块
 */

public class FooterHolder  extends RecyclerView.ViewHolder{

    @Bind(R.id.footer_loading_stub)
    View mLoadingViewstubstub;
    @Bind(R.id.footer_end_stub)
    View mEndViewstub;
    @Bind(R.id.footer_error_stub)
    View mErrorViewstub;

    private static final String TAG = "FooterHolder";

    public FooterHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);

    }

    public void setData(FooterShowType type) {
        Log.i(TAG, "FooterShowType->" + type);
        switch (type) {
            case NORMAL:
                mLoadingViewstubstub.setVisibility(View.GONE);
                mEndViewstub.setVisibility(View.GONE);
                mErrorViewstub.setVisibility(View.GONE);
                break;
            case LOADING:
                mLoadingViewstubstub.setVisibility(View.VISIBLE);
                mEndViewstub.setVisibility(View.GONE);
                mErrorViewstub.setVisibility(View.GONE);
                break;
            case END:
                mLoadingViewstubstub.setVisibility(View.GONE);
                mEndViewstub.setVisibility(View.VISIBLE);
                mErrorViewstub.setVisibility(View.GONE);
                break;
            case ERROR:
                mLoadingViewstubstub.setVisibility(View.GONE);
                mEndViewstub.setVisibility(View.GONE);
                mErrorViewstub.setVisibility(View.VISIBLE);
                break;
        }

        Log.i(TAG, "FooterShowType->" + type + " end.");

    }

    /**
     * 设置加载出错时的操作
     * @param clickListener 事件监听
     */
    public void setOnErrorHandle(View.OnClickListener clickListener) {
        mErrorViewstub.setOnClickListener(clickListener);
    }

}
