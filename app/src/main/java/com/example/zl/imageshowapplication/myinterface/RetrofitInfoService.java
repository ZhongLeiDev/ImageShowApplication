package com.example.zl.imageshowapplication.myinterface;

import com.example.zl.imageshowapplication.bean.bcy.retro.AlbumInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.CoserInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.PictureInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.bean.geek.GeekResult;
import com.example.zl.imageshowapplication.bean.huaban.devided.DevidedJsonRootBean;
import com.example.zl.imageshowapplication.bean.huaban.loadmore.LoadMorePins;
import com.example.zl.imageshowapplication.bean.huaban.search.SearchJsonRootBean;

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

    /**
     * HuaBan Anim 推荐
     * @param limit 数量限制
     * @return
     */
    @GET("favorite%2Fanime")
    Observable<DevidedJsonRootBean> getAnimFromHuaBanWithRx(
            @Query("limit") int limit
    );

    /**
     * HuaBan Anim 推荐,加载更多
     * @param limit 数量限制
     * @param lastPinId 最后一 PIN 的 PinId
     * @return
     */
    @GET("favorite%2Fanime")
    Observable<LoadMorePins> getAnimFromHuaBanWithRx_More(
            @Query("limit") int limit,
            @Query("max") long lastPinId
    );

    /**
     * HuaBan Quotes 推荐
     * @param limit 数量限制
     * @return
     */
    @GET("favorite%2Fquotes")
    Observable<DevidedJsonRootBean> getQuotesFromHuaBanWithRx(
            @Query("limit") int limit
    );

    /**
     * HuaBan Quotes 推荐,加载更多
     * @param limit 数量限制
     * @param lastPinId 最后一 PIN 的 PinId
     * @return
     */
    @GET("favorite%2Fquotes")
    Observable<LoadMorePins> getQuotesFromHuaBanWithRx_More(
            @Query("limit") int limit,
            @Query("max") long lastPinId
    );

    /**
     * HuaBan Photography 推荐
     * @param limit 数量限制
     * @return
     */
    @GET("favorite%2Fphotography")
    Observable<DevidedJsonRootBean> getPhotographyFromHuaBanWithRx(
            @Query("limit") int limit
    );

    /**
     * HuaBan Photography 推荐,加载更多
     * @param limit 数量限制
     * @param lastPinId 最后一 PIN 的 PinId
     * @return
     */
    @GET("favorite%2Fphotography")
    Observable<LoadMorePins> getPhotographyFromHuaBanWithRx_More(
            @Query("limit") int limit,
            @Query("max") long lastPinId
    );

    /**
     * HuaBan TravelPlaces 推荐
     * @param limit 数量限制
     * @return
     */
    @GET("favorite%2Ftravel_places")
    Observable<DevidedJsonRootBean> getTravelplacesFromHuaBanWithRx(
            @Query("limit") int limit
    );

    /**
     * HuaBan TravelPlaces 推荐,加载更多
     * @param limit 数量限制
     * @param lastPinId 最后一 PIN 的 PinId
     * @return
     */
    @GET("favorite%2Ftravel_places")
    Observable<LoadMorePins> getTravelplacesFromHuaBanWithRx_More(
            @Query("limit") int limit,
            @Query("max") long lastPinId
    );

    /**
     * HuaBan Beauty 推荐
     * @param limit 数量限制
     * @return
     */
    @GET("favorite%2Fbeauty")
    Observable<DevidedJsonRootBean> getBeautyFromHuaBanWithRx(
            @Query("limit") int limit
    );

    /**
     * HuaBan Beauty 推荐,加载更多
     * @param limit 数量限制
     * @param lastPinId 最后一 PIN 的 PinId
     * @return
     */
    @GET("favorite%2Fbeauty")
    Observable<LoadMorePins> getBeautyFromHuaBanWithRx_More(
            @Query("limit") int limit,
            @Query("max") long lastPinId
    );

    /**
     * HuaBan Illustration 推荐
     * @param limit 数量限制
     * @return
     */
    @GET("favorite%2Fillustration")
    Observable<DevidedJsonRootBean> getIllustrationFromHuaBanWithRx(
            @Query("limit") int limit
    );

    /**
     * HuaBan Illustration 推荐,加载更多
     * @param limit 数量限制
     * @param lastPinId 最后一 PIN 的 PinId
     * @return
     */
    @GET("favorite%2Fillustration")
    Observable<LoadMorePins> getIllustrationFromHuaBanWithRx_More(
            @Query("limit") int limit,
            @Query("max") long lastPinId
    );

    /**
     * 根据 BoardId 查询到的 Pins
     * @param boardId 待查询的 Board 的 ID
     * @param limit 数量限制
     * @return
     */
    @GET("boards/{boardId}/pins")
    Observable<LoadMorePins> getBoardPinsFromHuaBanWithRx(
            @Path("boardId") long boardId,
            @Query("limit") int limit
    );

    /**
     * HuaBan 根据 BoardId 查询到的 Pins,加载更多
     * @param boardId 待查询的 Board 的 ID
     * @param limit 数量限制
     * @param lastPinId 最后一 PIN 的 PinId
     * @return
     */
    @GET("boards/{boardId}/pins")
    Observable<LoadMorePins> getBoardPinsFromHuaBanWithRx_More(
            @Path("boardId") long boardId,
            @Query("limit") int limit,
            @Query("max") long lastPinId
    );

    /**
     * HuaBan 关键词查询结果
     * @param keyWord 查询关键词
     * @param pageSize 每页的结果条数
     * @param pageCount 查询第几页的结果
     * @param sortType 排序方式
     * @return
     */
    @GET("search")
    Observable<SearchJsonRootBean> getSearchResultFromHuaBanWithRx(
            @Query(value = "q",encoded = true) String keyWord,
            @Query("per_page") int pageSize,
            @Query("page") int pageCount,
            @Query("sort") String sortType
    );

}
