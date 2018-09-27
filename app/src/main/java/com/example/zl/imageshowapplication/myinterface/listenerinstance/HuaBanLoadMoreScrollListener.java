package com.example.zl.imageshowapplication.myinterface.listenerinstance;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.example.zl.imageshowapplication.adapter.huaban.HuaBanImageWaterFallLoadMoreAdapter;

/**
 * Created by ZhongLeiDev on 2018/9/26.
 * StaggeredGridLayoutManager 滚动监听器，用于判断是否滚动到最后
 */

public class HuaBanLoadMoreScrollListener extends RecyclerView.OnScrollListener{

    boolean isSlidingToLast = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        final StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        final HuaBanImageWaterFallLoadMoreAdapter adapter = (HuaBanImageWaterFallLoadMoreAdapter) recyclerView.getAdapter();

        // 当不滚动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {

            handle(manager,adapter);

        }
    }

    /**
     * 滚动处理方法
     * @param manager
     * @param adapter
     */
    private void handle(StaggeredGridLayoutManager manager, HuaBanImageWaterFallLoadMoreAdapter adapter) {
        //获取最后一个完全显示的ItemPosition
        int[] lastVisiblePositions = manager.findLastVisibleItemPositions(new int[manager.getSpanCount()]);
        int lastVisiblePos = getMaxElem(lastVisiblePositions);
        int totalItemCount = manager.getItemCount();

        Log.i("HuaBanloadMoreListener", "spanCount->" + manager.getSpanCount() +
                ", lastPos->" + lastVisiblePos +
                ", totalItemCount->" + totalItemCount +
                ", slidingToLast->" + isSlidingToLast);

        // 判断是否滚动到底部,[totalItemCount-3]这个值是经验值,因为最后的可见元素位置编号与元素总数量一般
        //都是不一样的
        if (lastVisiblePos  >= (totalItemCount - 3) && isSlidingToLast) {
            //加载更多功能的代码
            adapter.loadMore();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
        if(dy > 0){
            //大于0表示，正在向下滚动
            isSlidingToLast = true;
        }else{
            //小于等于0 表示停止或向上滚动
            isSlidingToLast = false;
        }

    }

    private int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i]>maxVal)
                maxVal = arr[i];
        }
        return maxVal;
    }

}
