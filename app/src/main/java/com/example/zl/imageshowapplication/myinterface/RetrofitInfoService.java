package com.example.zl.imageshowapplication.myinterface;

import com.example.zl.imageshowapplication.bean.bcy.retro.AlbumInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.PictureInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.bean.geek.GeekResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ZhongLeiDev on 2018/4/10.
 * Retrofit使用的相关方法调用接口
 */

public interface RetrofitInfoService {

    /**
     * 不使用 RxJava 的一般调用方法(Geekl)
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
     * 使用 RxJava 返回 Observable 对象(Geekl)
     * @param size
     * @param page
     * @return
     */
    @GET("data/福利/{size}/{page}")
    Observable<GeekResult> getGeekResultWithRx(
            @Path("size") Integer size,
            @Path("page") Integer page
    );

    /**
     * 获取 BcyPicture 随机推荐结果
     * @param pageSize
     * @return
     */
    @GET("pictureinfo/randompicture")
    Call<ResultVO<List<PictureInfo>>> getBcyRandomPictures(
        @Query("pageSize") Integer pageSize
    );

    /**
     * 获取 BcyAlbum 随机推荐结果
     * @param pageSize
     * @return
     */
    @GET()
    Call<ResultVO<List<AlbumInfo>>> getBcyRandomAlbums(
            @Query("pageSize") Integer pageSize
    );

}
