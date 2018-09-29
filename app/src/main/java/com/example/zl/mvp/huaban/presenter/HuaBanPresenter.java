package com.example.zl.mvp.huaban.presenter;

import android.content.Context;
import android.util.Log;

import com.example.zl.enums.HuaBanFragmentType;
import com.example.zl.imageshowapplication.bean.huaban.devided.DevidedJsonRootBean;
import com.example.zl.imageshowapplication.bean.huaban.devided.DevidedPins;
import com.example.zl.imageshowapplication.bean.huaban.loadmore.LoadMorePins;
import com.example.zl.imageshowapplication.bean.huaban.search.SearchJsonRootBean;
import com.example.zl.imageshowapplication.bean.huaban.search.SearchPins;
import com.example.zl.imageshowapplication.bean.huaban.transobj.HBImageBean;
import com.example.zl.imageshowapplication.linkanalyzestrategy.retrofits.RetrofitFactory;
import com.example.zl.imageshowapplication.myinterface.RetrofitInfoService;
import com.example.zl.mvp.huaban.view.HuanBanView;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ZhongLeiDev on 2018/9/26.
 * HuaBan Image 显示 MVP 模式中的 Presenter
 */

public class HuaBanPresenter {

    private static final String TAG = "HuaBanPresenter";

    private Context mContext;
    private HuanBanView huanBanView;
    private RetrofitInfoService huabanService;

    public HuaBanPresenter(Context ctx) {
        mContext = ctx;
    }

    public void onCreate() {
        huabanService = RetrofitFactory.getHuaBanRetroSingleInstance();
    }

    public void attachView(HuanBanView view) {
        huanBanView = view;
    }

    /**
     * 获取 Image 初始化资源
     * @param type  资源分类
     * @param keyWord   查询关键词【针对 HuaBanFragmentType.HUABAN_FRAGMENT_SEARCH】
     * @param boardId 画板 ID 【针对 HuaBanFragmentType.HUABAN_FRAGMENT_BOARD】
     */
    public void getHuaBanImageResource(HuaBanFragmentType type, String keyWord, long boardId) {
        if (type == HuaBanFragmentType.HUABAN_FRAGMENT_SEARCH) {

            huabanService.getSearchResultFromHuaBanWithRx(keyWord, 30, 0, "created_at")
                    .flatMap(new Func1<SearchJsonRootBean, Observable<SearchPins>>() {
                        @Override
                        public Observable<SearchPins> call(SearchJsonRootBean searchJsonRootBean) {
                            Log.i(TAG,searchJsonRootBean.toString());
                            return Observable.from(searchJsonRootBean.getPins());
                        }
                    })
                    .map(new Func1<SearchPins, HBImageBean>() {
                        @Override
                        public HBImageBean call(SearchPins searchPins) {
                            HBImageBean bean = new HBImageBean();
                            bean.setPin_id(searchPins.getPin_id());
                            bean.setBoard_id(searchPins.getBoard_id());
                            bean.setUrl("http://img.hb.aicdn.com/" + searchPins.getFile().getKey());
                            bean.setTheme(searchPins.getFile().getTheme());
                            bean.setHeight(searchPins.getFile().getHeight());
                            bean.setWidth(searchPins.getFile().getWidth());
                            return bean;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HBImageBean>() {
                        @Override
                        public void onCompleted() {
                            huanBanView.onCompleted("请求完毕！");
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            huanBanView.onError("请求出错！");
                        }

                        @Override
                        public void onNext(HBImageBean hbImageBean) {
                            if (hbImageBean != null) {
                                huanBanView.onNext(hbImageBean);
                            }
                        }
                    });

        } else if (type == HuaBanFragmentType.HUA_BAN_FRAGMENT_BOARD) {

            huabanService.getBoardPinsFromHuaBanWithRx(boardId, 30)
                    .flatMap(new Func1<LoadMorePins, Observable<DevidedPins>>() {
                        @Override
                        public Observable<DevidedPins> call(LoadMorePins devidedJsonRootBean) {
                            Log.i(TAG, devidedJsonRootBean.toString());
                            return Observable.from(devidedJsonRootBean.getPins());
                        }
                    })
                    .map(new Func1<DevidedPins, HBImageBean>() {
                        @Override
                        public HBImageBean call(DevidedPins devidedPins) {
                            HBImageBean bean = new HBImageBean();
                            bean.setPin_id(devidedPins.getPin_id());
                            bean.setBoard_id(devidedPins.getBoard_id());
                            bean.setUrl("http://img.hb.aicdn.com/" + devidedPins.getFile().getKey());
                            bean.setTheme(devidedPins.getFile().getTheme());
                            bean.setHeight(devidedPins.getFile().getHeight());
                            bean.setWidth(devidedPins.getFile().getWidth());
                            return bean;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HBImageBean>() {
                        @Override
                        public void onCompleted() {
                            huanBanView.onCompleted("请求完毕！");
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            huanBanView.onError("网络请求出错！");
                        }

                        @Override
                        public void onNext(HBImageBean hbImageBean) {
                            if (hbImageBean != null) {
                                huanBanView.onNext(hbImageBean);
                            }
                        }
                    });

        } else {

            Observable<DevidedJsonRootBean> observable = null;

            switch (type) {
                case HUABAN_FRAGMENT_ANIM:
                    observable = huabanService.getAnimFromHuaBanWithRx(30);
                    break;
                case HUABAN_FRAGMENT_BEAUTY:
                    observable = huabanService.getBeautyFromHuaBanWithRx(30);
                    break;
                case HUABAN_FRAGMENT_QUOTES:
                    observable = huabanService.getQuotesFromHuaBanWithRx(30);
                    break;
                case HUABAN_FRAGMENT_PHOTOGRAPHY:
                    observable = huabanService.getPhotographyFromHuaBanWithRx(30);
                    break;
                case HUABAN_FRAGMENT_TRAVELPLACE:
                    observable = huabanService.getTravelplacesFromHuaBanWithRx(30);
                    break;
                case HUABAN_FRAGMENT_ILLUSTRATION:
                    observable = huabanService.getIllustrationFromHuaBanWithRx(30);
                    break;
            }

            if (observable != null) {

                observable
                        .flatMap(new Func1<DevidedJsonRootBean, Observable<DevidedPins>>() {
                            @Override
                            public Observable<DevidedPins> call(DevidedJsonRootBean devidedJsonRootBean) {
                                Log.i(TAG,devidedJsonRootBean.toString());
                                return Observable.from(devidedJsonRootBean.getPins());
                            }
                        })
                        .map(new Func1<DevidedPins, HBImageBean>() {
                            @Override
                            public HBImageBean call(DevidedPins devidedPins) {
                                HBImageBean bean = new HBImageBean();
                                bean.setPin_id(devidedPins.getPin_id());
                                bean.setBoard_id(devidedPins.getBoard_id());
                                bean.setUrl("http://img.hb.aicdn.com/" + devidedPins.getFile().getKey());
                                bean.setTheme(devidedPins.getFile().getTheme());
                                bean.setHeight(devidedPins.getFile().getHeight());
                                bean.setWidth(devidedPins.getFile().getWidth());
                                return bean;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<HBImageBean>() {
                            @Override
                            public void onCompleted() {
                                huanBanView.onCompleted("请求完毕！");
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                huanBanView.onError("网络请求出错！");
                            }

                            @Override
                            public void onNext(HBImageBean hbImageBean) {
                                if (hbImageBean != null) {
                                    huanBanView.onNext(hbImageBean);
                                }
                            }
                        });
            } else {

             huanBanView.onError("查询到结果为空！");

            }

        }
    }

    /**
     * 加载更多数据
     * @param type Fragment 类型
     * @param boardId 画板 ID 【针对 HuaBanFragmentType.HUABAN_FRAGMENT_BOARD】
     * @param lastPinId 最后一 PIN 的 ponId
     * @param keyWord 查询关键词
     * @param pageCount 查询结果的当前页数 【针对 HuaBanFragmentType.HUABAN_FRAGMENT_SEARCH】
     */
    public void getHuaBanImageResource_LoadMore(HuaBanFragmentType type, long boardId, long lastPinId,
                                                String keyWord, int pageCount) {

        if (type == HuaBanFragmentType.HUABAN_FRAGMENT_SEARCH) {

            huabanService.getSearchResultFromHuaBanWithRx(keyWord, 30, pageCount, "created_at")
                    .flatMap(new Func1<SearchJsonRootBean, Observable<SearchPins>>() {
                        @Override
                        public Observable<SearchPins> call(SearchJsonRootBean searchJsonRootBean) {
                            Log.i(TAG,searchJsonRootBean.toString());
                            return Observable.from(searchJsonRootBean.getPins());
                        }
                    })
                    .map(new Func1<SearchPins, HBImageBean>() {
                        @Override
                        public HBImageBean call(SearchPins searchPins) {
                            HBImageBean bean = new HBImageBean();
                            bean.setPin_id(searchPins.getPin_id());
                            bean.setBoard_id(searchPins.getBoard_id());
                            bean.setUrl("http://img.hb.aicdn.com/" + searchPins.getFile().getKey());
                            bean.setTheme(searchPins.getFile().getTheme());
                            bean.setHeight(searchPins.getFile().getHeight());
                            bean.setWidth(searchPins.getFile().getWidth());
                            return bean;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HBImageBean>() {
                        @Override
                        public void onCompleted() {
                            huanBanView.onCompleted("请求完毕！");
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            huanBanView.onError("请求出错！");
                        }

                        @Override
                        public void onNext(HBImageBean hbImageBean) {
                            if (hbImageBean != null) {
                                huanBanView.onNext(hbImageBean);
                            }
                        }
                    });

        } else {

            Observable<LoadMorePins> observable = null;

            switch (type) {
                case HUABAN_FRAGMENT_ANIM:
                    observable = huabanService.getAnimFromHuaBanWithRx_More(30, lastPinId);
                    break;
                case HUABAN_FRAGMENT_BEAUTY:
                    observable = huabanService.getBeautyFromHuaBanWithRx_More(30, lastPinId);
                    break;
                case HUABAN_FRAGMENT_QUOTES:
                    observable = huabanService.getQuotesFromHuaBanWithRx_More(30, lastPinId);
                    break;
                case HUABAN_FRAGMENT_PHOTOGRAPHY:
                    observable = huabanService.getPhotographyFromHuaBanWithRx_More(30, lastPinId);
                    break;
                case HUABAN_FRAGMENT_TRAVELPLACE:
                    observable = huabanService.getTravelplacesFromHuaBanWithRx_More(30, lastPinId);
                    break;
                case HUABAN_FRAGMENT_ILLUSTRATION:
                    observable = huabanService.getIllustrationFromHuaBanWithRx_More(30, lastPinId);
                    break;
                case HUA_BAN_FRAGMENT_BOARD:
                    observable = huabanService.getBoardPinsFromHuaBanWithRx_More(boardId, 30, lastPinId);
            }

            if (observable != null) {

                observable
                        .flatMap(new Func1<LoadMorePins, Observable<DevidedPins>>() {
                            @Override
                            public Observable<DevidedPins> call(LoadMorePins devidedJsonRootBean) {
                                Log.i(TAG, devidedJsonRootBean.toString());
                                return Observable.from(devidedJsonRootBean.getPins());
                            }
                        })
                        .map(new Func1<DevidedPins, HBImageBean>() {
                            @Override
                            public HBImageBean call(DevidedPins devidedPins) {
                                HBImageBean bean = new HBImageBean();
                                bean.setPin_id(devidedPins.getPin_id());
                                bean.setBoard_id(devidedPins.getBoard_id());
                                bean.setUrl("http://img.hb.aicdn.com/" + devidedPins.getFile().getKey());
                                bean.setTheme(devidedPins.getFile().getTheme());
                                bean.setHeight(devidedPins.getFile().getHeight());
                                bean.setWidth(devidedPins.getFile().getWidth());
                                return bean;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<HBImageBean>() {
                            @Override
                            public void onCompleted() {
                                huanBanView.onCompleted("请求完毕！");
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                huanBanView.onError("网络请求出错！");
                            }

                            @Override
                            public void onNext(HBImageBean hbImageBean) {
                                if (hbImageBean != null) {
                                    huanBanView.onNext(hbImageBean);
                                }
                            }
                        });
            } else {

                huanBanView.onError("查询到结果为空！");

            }
        }
    }

}
