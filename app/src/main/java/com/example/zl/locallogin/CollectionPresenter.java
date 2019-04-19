package com.example.zl.locallogin;

import android.util.Log;

import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.locallogin.bean.LocalError;
import com.example.zl.locallogin.bean.PictureCollect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhongLeiDev on 2018/10/8.
 * 收藏功能逻辑类
 */
public class CollectionPresenter {

    private static final String TAG = "CollectionPresenter";
    private CollectionView collectionView;

    public CollectionPresenter() {

    }

    public void onAttachView(CollectionView view) {
        this.collectionView = view;
    }

    /**
     * 单个图片收藏方法
     * @param pinUrl 图片链接
     */
    public void collectPins( final String pinUrl) {

        LocalUserHandle.collectPin(LocalUserHandle.currentUser(), pinUrl, pinUrl, new LocalCallback<ResultVO>() {
            @Override
            public void done(ResultVO resultVO, LocalError e) {
                if (e == null) {
                    Log.i("collectPins", "pinUrl->" + pinUrl + " 收藏成功！");
                    collectionView.onSucceed();
                } else {
                    collectionView.onError("PIN 收藏失败！" + e.getMsg());
                }
            }
        });
    }

    /**
     * 图片相册收藏方法
     * @param boardId 相册Id
     * @param cover 相册封面
     */
    public void collectBoard(final String boardId, final String cover) {

        LocalUserHandle.collectBoard(LocalUserHandle.currentUser(), boardId, cover, new LocalCallback<ResultVO>() {
            @Override
            public void done(ResultVO resultVO, LocalError e) {
                if (e == null) {
                    Log.i("collectBoard", "boardId->" + boardId + " 收藏成功！");
                    collectionView.onSucceed();
                } else {
                    collectionView.onError("BOARD 收藏失败！" + e.getMsg());
                }
            }
        });
    }

    /**
     * 作者收藏方法
     * @param authorId 作者Id
     * @param cover 作者头像
     */
    public void collectAuthor(final String authorId, final String cover) {
        //TODO
        collectionView.onError("暂不支持此操作！");
    }

    /**
     * 根据查询类型查询对应的收藏数据
     * @param type 查询的类型
     * @param userId 用户 Id
     * @param pageCount 当前查询的页面页标
     */
    public void queryCollections(CollectionType type, String userId, int pageCount) {
        switch (type) {
            case COLLECTON_TYPE_PIN:
                queryPins(pageCount);
                break;
            case COLLECTON_TYPE_BOARD:
                queryBoard(pageCount);
                break;
            case COLLECTON_TYPE_AUTHOR:
                queryAuthor(pageCount);
                break;
        }
    }

    /**
     * 查询收藏的图片
     * @param pageCount 查询第几页的页标
     */
    private void queryPins(final int pageCount) {

        LocalUserHandle.getCollectedPins(LocalUserHandle.currentUser(), pageCount, new LocalCallback<ResultVO<List<PictureCollect>>>() {
            @Override
            public void done(ResultVO<List<PictureCollect>> listResultVO, LocalError e) {
                if (e == null) {
                    List<CollectionBean> beanList = new ArrayList<>();
                    for (PictureCollect obj : listResultVO.getData()) {
                        CollectionBean bean = new CollectionBean();
                        bean.setUrl(obj.getCollectUrl());
                        bean.setCover(obj.getCover());
                        beanList.add(bean);
                    }
                    collectionView.onQueryCompleted(beanList);
                } else {
                    collectionView.onError("查询 PIN 出错！" + e.getMsg());
                }
            }
        });

    }

    /**
     * 查询收藏的画板
     * @param pageCount 查询第几页的页标
     */
    private void queryBoard(final int pageCount) {

        LocalUserHandle.getCollectedBoards(LocalUserHandle.currentUser(), pageCount, new LocalCallback<ResultVO<List<PictureCollect>>>() {
            @Override
            public void done(ResultVO<List<PictureCollect>> listResultVO, LocalError e) {
                if (e == null) {
                    List<CollectionBean> beanList = new ArrayList<>();
                    for (PictureCollect obj : listResultVO.getData()) {
                        CollectionBean bean = new CollectionBean();
                        bean.setUrl(obj.getCollectUrl());
                        bean.setCover(obj.getCover());
                        beanList.add(bean);
                    }
                    collectionView.onQueryCompleted(beanList);
                } else {
                    collectionView.onError("查询 BOARD 出错！" + e.getMsg());
                }
            }
        });
    }

    /**
     * 查询收藏的作者
     * @param pageCount 查询第几页的页标
     */
    private void queryAuthor(final int pageCount) {
        //TODO
        collectionView.onError("暂不支持此操作！");
    }

    /**
     * 删除收藏的方法
     * @param type 当前显示的 CollectionType
     * @param userId 用户 Id
     * @param bean 待删除的 Item
     */
    public void deleteCollection(CollectionType type, String userId, CollectionBean bean) {
        switch (type) {
            case COLLECTON_TYPE_PIN:
                deletePin(bean.getUrl());
                break;
            case COLLECTON_TYPE_BOARD:
                deleteBoard(bean.getUrl());
                break;
            case COLLECTON_TYPE_AUTHOR:
                deleteAuthor(bean.getUrl());
                break;
        }
    }

    private void deletePin(String url) {
        //TODO
        collectionView.onError("暂不支持此操作！");
    }

    private void deleteBoard(String url) {
        //TODO
        collectionView.onError("暂不支持此操作！");
    }

    private void deleteAuthor(String url) {
        //TODO
        collectionView.onError("暂不支持此操作！");
    }

}
