package com.example.zl.imageshowapplication.myinterface;

import com.example.zl.imageshowapplication.bean.geek.GeekResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ZhongLeiDev on 2018/4/10.
 * Retrofit使用的相关方法调用接口
 */

public interface RetrofitInfoService {

    /**
     * 不使用 RxJava 的一般调用方法
     * @param size
     * @param page
     * @return
     */
    @GET("data/福利/{size}/{page}")
    Call<GeekResult> getGeekResult(
            @Path("size") Integer size,
            @Path("page") Integer page
    );

    /**
     * 使用 RxJava 返回 Observable 对象
     * @param size
     * @param page
     * @return
     */
    @GET("data/福利/{size}/{page}")
    Observable<GeekResult> getGeekResultWithRx(
            @Path("size") Integer size,
            @Path("page") Integer page
    );

}
