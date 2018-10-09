package com.example.zl.leancloud;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVSaveOption;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
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
     * @param userId 执行收藏操作的用户Id
     * @param pinUrl 图片链接
     */
    public void collectPins(final String userId, final String pinUrl) {

        AVQuery<AVObject> startQuery = new AVQuery<>("Pins_Collection");
        startQuery.whereEqualTo("UserId", userId);
        AVQuery<AVObject> endQuery = new AVQuery<>("Pins_Collection");
        endQuery.whereEqualTo("Url", pinUrl);

        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(startQuery, endQuery));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> pinList, AVException e) {
                if (e == null) {
                    if (pinList.size() == 0) {
                        AVObject pin = new AVObject("Pins_Collection");
                        pin.put("UserId", userId);
                        pin.put("Url", pinUrl);
                        pin.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    Log.i("collectPins", "pinUrl->" + pinUrl + " 收藏成功！");
                                    collectionView.onSucceed();
                                } else {
                                    e.printStackTrace();
                                    collectionView.onError("收藏失败！");
                                }
                            }
                        });
                    } else {
                        Log.i("collectPins", "pinUrl->" + pinUrl + " 已存在！");
                        collectionView.onError("目标收藏已存在！");
                    }
                } else {
                    e.printStackTrace();
                    collectionView.onError("收藏失败！");
                }
            }
        });
    }

    /**
     * 图片相册收藏方法
     * @param userId 执行收藏操作的用户Id
     * @param boardId 相册Id
     * @param cover 相册封面
     */
    public void collectBoard(final String userId, final String boardId, final String cover) {
        AVQuery<AVObject> startQuery = new AVQuery<>("Board_Collection");
        startQuery.whereEqualTo("UserId", userId);
        AVQuery<AVObject> endQuery = new AVQuery<>("Board_Collection");
        endQuery.whereEqualTo("Url", boardId);

        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(startQuery, endQuery));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {

                    if (list.size() == 0) {
                        AVObject board = new AVObject("Board_Collection");
                        board.put("UserId", userId);
                        board.put("Url", boardId);
                        board.put("BoardCover", cover);
                        board.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    Log.i("collectBoard", "boardId->" + boardId + " 收藏成功！");
                                    collectionView.onSucceed();
                                } else {
                                    e.printStackTrace();
                                    collectionView.onError("收藏失败！");
                                }
                            }
                        });
                    } else {
                        Log.i("collectBoard", "boardId->" + boardId + " 已存在！");
                        collectionView.onError("目标收藏已存在！");
                    }

                } else {
                    e.printStackTrace();
                    collectionView.onError("收藏失败！");
                }
            }
        });
    }

    /**
     * 作者收藏方法
     * @param userId 执行收藏操作的用户Id
     * @param authorId 作者Id
     * @param cover 作者头像
     */
    public void collectAuthor(final String userId, final String authorId, final String cover) {
        AVQuery<AVObject> startQuery = new AVQuery<>("Author_Collection");
        startQuery.whereEqualTo("UserId", userId);
        AVQuery<AVObject> endQuery = new AVQuery<>("Author_Collection");
        endQuery.whereEqualTo("Url", authorId);

        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(startQuery, endQuery));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {

                    if (list.size() == 0) {
                        AVObject author = new AVObject("Author_Collection");
                        author.put("UserId", userId);
                        author.put("Url", authorId);
                        author.put("AuthorCover", cover);
                        author.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    Log.i("collectAuthor", "authorId->" + authorId + " 收藏成功！");
                                    collectionView.onSucceed();
                                } else {
                                    e.printStackTrace();
                                    collectionView.onError("收藏失败！");
                                }
                            }
                        });
                    } else {
                        Log.i("collectAuthor", "authorId->" + authorId + " 已存在！");
                        collectionView.onError("目标收藏已存在！");
                    }

                } else {
                    e.printStackTrace();
                    collectionView.onError("收藏失败！");
                }
            }
        });
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
                queryPins(userId, pageCount);
                break;
            case COLLECTON_TYPE_BOARD:
                queryBoard(userId, pageCount);
                break;
            case COLLECTON_TYPE_AUTHOR:
                queryAuthor(userId, pageCount);
                break;
        }
    }

    /**
     * 查询收藏的图片
     * @param userId 用户 Id
     * @param pageCount 查询第几页的页标
     */
    private void queryPins(final String userId, final int pageCount) {
        AVQuery<AVObject> query = new AVQuery<>("Pins_Collection");
        query.whereEqualTo("UserId", userId);
        query.limit(30);// 最多返回 30 条结果
        query.skip(30 * pageCount);// 跳过 30 * pageCount 条结果,即跳过 pageCount 页
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    List<CollectionBean> beanList = new ArrayList<>();
                    for (AVObject obj : list) {
                        CollectionBean bean = new CollectionBean();
                        bean.setUrl(obj.getString("Url"));
                        bean.setCover(obj.getString("Url"));
                        beanList.add(bean);
                    }
                    collectionView.onQueryCompleted(beanList);
                } else {
                    e.printStackTrace();
                    collectionView.onError("查询出错！");
                }
            }
        });

    }

    /**
     * 查询收藏的画板
     * @param userId 用户 Id
     * @param pageCount 查询第几页的页标
     */
    private void queryBoard(final String userId, final int pageCount) {
        AVQuery<AVObject> query = new AVQuery<>("Board_Collection");
        query.whereEqualTo("UserId", userId);
        query.limit(30);// 最多返回 30 条结果
        query.skip(30 * pageCount);// 跳过 30 * pageCount 条结果,即跳过 pageCount 页
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    List<CollectionBean> beanList = new ArrayList<>();
                    for (AVObject obj : list) {
                        CollectionBean bean = new CollectionBean();
                        bean.setUrl(obj.getString("Url"));
                        bean.setCover(obj.getString("BoardCover"));
                        beanList.add(bean);
                    }
                    collectionView.onQueryCompleted(beanList);
                } else {
                    e.printStackTrace();
                    collectionView.onError("查询出错！");
                }
            }
        });
    }

    /**
     * 查询收藏的作者
     * @param userId 用户 Id
     * @param pageCount 查询第几页的页标
     */
    private void queryAuthor(final String userId, final int pageCount) {
        AVQuery<AVObject> query = new AVQuery<>("Author_Collection");
        query.whereEqualTo("UserId", userId);
        query.limit(30);// 最多返回 30 条结果
        query.skip(30 * pageCount);// 跳过 30 * pageCount 条结果,即跳过 pageCount 页
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    List<CollectionBean> beanList = new ArrayList<>();
                    for (AVObject obj : list) {
                        CollectionBean bean = new CollectionBean();
                        bean.setUrl(obj.getString("Url"));
                        bean.setCover(obj.getString("AuthorCover"));
                        beanList.add(bean);
                    }
                    collectionView.onQueryCompleted(beanList);
                } else {
                    e.printStackTrace();
                    collectionView.onError("查询出错！");
                }
            }
        });
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
                deletePin(userId,bean.getUrl());
                break;
            case COLLECTON_TYPE_BOARD:
                deleteBoard(userId,bean.getUrl());
                break;
            case COLLECTON_TYPE_AUTHOR:
                deleteAuthor(userId,bean.getUrl());
                break;
        }
    }

    private void deletePin(String userId, String url) {
        AVQuery<AVObject> query = new AVQuery<>("Pins_Collection");
        query.whereEqualTo("UserId", userId);
        query.whereEqualTo("Url", url);
        query.deleteAllInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    collectionView.onSucceed();
                } else {
                    collectionView.onError("图片收藏删除失败！");
                }
            }
        });
    }

    private void deleteBoard(String userId, String url) {
        AVQuery<AVObject> query = new AVQuery<>("Board_Collection");
        query.whereEqualTo("UserId", userId);
        query.whereEqualTo("Url", url);
        query.deleteAllInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    collectionView.onSucceed();
                } else {
                    collectionView.onError("画板收藏删除失败！");
                }
            }
        });
    }

    private void deleteAuthor(String userId, String url) {
        AVQuery<AVObject> query = new AVQuery<>("Board_Collection");
        query.whereEqualTo("UserId", userId);
        query.whereEqualTo("Url", url);
        query.deleteAllInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    collectionView.onSucceed();
                } else {
                    collectionView.onError("Author 收藏删除失败！");
                }
            }
        });
    }

}
