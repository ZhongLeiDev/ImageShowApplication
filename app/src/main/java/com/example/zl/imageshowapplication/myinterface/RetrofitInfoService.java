package com.example.zl.imageshowapplication.myinterface;

import com.example.zl.imageshowapplication.bean.bcy.retro.AlbumInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.CoserInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.PictureInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.bean.geek.GeekResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
     * @param pageSize  每页显示的结果条数
     * @return
     */
    @GET("pictureinfo/randompicture")
    Call<ResultVO<List<PictureInfo>>> getBcyRandomPictures(
        @Query("pageSize") Integer pageSize
    );

    /**
     * 获取 BcyPicture 搜索结果
     * @param searchTag 查询的条件
     * @param pageCount 结果页数
     * @param pageSize 每页显示的条数
     * @return
     */
    @GET("pictureinfo/")
    Call<ResultVO<List<PictureInfo>>> getBcySearchPictures(
            @Query("searchTag") String searchTag,
            @Query("pageCount") Integer pageCount,
            @Query("pageSize") Integer pageSize
    );

    /**
     * 获取 BcyAlbum 随机推荐结果
     * @param pageSize  结果条数
     * @return
     */
    @GET("albuminfo/randomalbum")
    Call<ResultVO<List<AlbumInfo>>> getBcyRandomAlbums(
            @Query("pageSize") Integer pageSize
    );

    /**
     * 获取 BcyAlbum 查询结果
     * @param searchTag 查询的条件
     * @param pageCount 结果页数
     * @param pageSize 每页显示的条数
     * @return
     */
    @GET("albuminfo/")
    Call<ResultVO<List<AlbumInfo>>> getBcySearchAlbums(
            @Query("searchTag") String searchTag,
            @Query("pageCount") Integer pageCount,
            @Query("pageSize") Integer pageSize
    );

    /**
     * 获取 BcyCoser 随机推荐结果
     * @param pageSize
     * @return
     */
    @GET("coserinfo/")
    Call<ResultVO<List<CoserInfo>>> getBcyRandomCosers(
            @Query("pageSize") Integer pageSize
    );

    /**
     * 获取 BcyCoser 查询结果
     * @param searchTag 查询的条件
     * @param pageCount 结果页数
     * @param pageSize 每页显示的条数
     * @return
     */
    @GET("coserinfo/")
    Call<ResultVO<List<CoserInfo>>> getBcySearchCosers(
            @Query("searchTag") String searchTag,
            @Query("pageCount") Integer pageCount,
            @Query("pageSize") Integer pageSize
    );


    /**
     * Bcy 相册查找
     * @param albumKey  关键字
     * @param pageCount 页数
     * @param pageSize  每页的结果条数
     * @return
     */
    @POST("albuminfo/searchalbum")
    Observable<ResultVO<List<AlbumInfo>>> searchBcyAlbumsWithRx(
            @Query("albumKey") String albumKey,
            @Query("pageCount") String pageCount,
            @Query("pageSize") String pageSize
    );


}
