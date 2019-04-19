package com.example.zl.imageshowapplication.myinterface;

import com.example.zl.imageshowapplication.bean.bcy.retro.AlbumInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.CoserInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.PictureInfo;
import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.bean.geek.GeekResult;
import com.example.zl.imageshowapplication.bean.huaban.devided.DevidedJsonRootBean;
import com.example.zl.imageshowapplication.bean.huaban.loadmore.LoadMorePins;
import com.example.zl.imageshowapplication.bean.huaban.search.SearchJsonRootBean;
import com.example.zl.locallogin.bean.ISUser;
import com.example.zl.locallogin.bean.PictureCollect;
import com.example.zl.locallogin.bean.PictureRandom;
import com.example.zl.locallogin.bean.PictureShared;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
     * @param size  页面容量
     * @param page  当前页面
     * @return
     */
    @GET("data/福利/{size}/{page}")
    Call<GeekResult> getGeekResult(
            @Path("size") Integer size,
            @Path("page") Integer page
    );

    /**
     * 使用 RxJava 返回 Observable 对象(Geekl)
     * @param size  页面容量
     * @param page  当前页面
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

    /**
     * 注册
     * @param userName  用户名
     * @param passWord  用户密码
     * @param file  头像文件
     * @param mail  邮箱
     * @return
     */
    @Multipart
    @POST("user_login_field/register")
    Call<ResultVO<ISUser>> register(@Part("userName") String userName,
                                    @Part("passWord") String passWord,
                                    @Part MultipartBody.Part file,
                                    @Part("mail") String mail);

    /**
     * 登录
     * @param userName  用户名
     * @param passWord  密码
     * @param token token
     * @return
     */
    @POST("user_login_field/login")
    Call<ResultVO<ISUser>> login(@Query("userName") String userName,
                                 @Query("passWord") String passWord,
                                 @Query("token") String token);

    /**
     * 注销
     * @param token token
     * @return
     */
    @POST("user_login_field/logout")
    Call<ResultVO<ISUser>> logout(@Query("token") String token);

    /**
     * 更改头像
     * @param username  用户名
     * @param file  头像文件
     * @return
     */
    @Multipart
    @POST("user_login_field/change_avatar")
    Call<ResultVO<String>> changeAvatar(@Part("userName") String username,
                                     @Part MultipartBody.Part file);

    /**
     * 随机获取图片
     * @param pageSize  单页显示数量
     * @return
     */
    @GET("user_handle_field/random_pictures")
    Call<ResultVO<List<PictureRandom>>> randomPictures(
            @Query("pageSize") int pageSize);

    /**
     * 随机获取单张图片
     * @return
     */
    @GET("user_handle_field/random_siglepic")
    Call<ResultVO<List<PictureRandom>>> randomSinglePicture();

    /**
     * 收藏 PIN
     * @param token 用户 token
     * @param userId    用户 Id
     * @param userName  用户名
     * @param pinUrl    图片源链接
     * @param cover   图片封面
     * @return
     */
    @POST("user_handle_field/collect_pin")
    Call<ResultVO> collectPin(@Query("token") String token,
                              @Query("userId") String userId,
                              @Query("userName") String userName,
                              @Query("pinUrl") String pinUrl,
                              @Query("cover") String cover);

    /**
     * 收藏 Board
     * @param token 用户 token
     * @param userId    用户 Id
     * @param userName  用户名
     * @param boardUrl  画板源链接
     * @param cover   画板封面
     * @return
     */
    @POST("user_handle_field/collect_board")
    Call<ResultVO> collectBoard(@Query("token") String token,
                                @Query("userId") String userId,
                                @Query("userName") String userName,
                                @Query("boardUrl") String boardUrl,
                                @Query("cover") String cover);

    /**
     * 获取用户收藏的 PIN
     * @param token 用户 token
     * @param userName  用户名
     * @param pageCount 查询第几页
     * @param pageSize  每页容量
     * @return
     */
    @GET("user_handle_field/get_pins")
    Call<ResultVO<List<PictureCollect>>> getCollectPins(
            @Query("token") String token,
            @Query("userName") String userName,
            @Query("pageCount") int pageCount,
            @Query("pageSize") int pageSize);

    /**
     * 获取用户收藏的 BOARD
     * @param token 用户 token
     * @param userName  用户名
     * @param pageCount 查询第几页
     * @param pageSize  每页容量
     * @return
     */
    @GET("user_handle_field/get_boards")
    Call<ResultVO<List<PictureCollect>>> getCollectBoards(
            @Query("token") String token,
            @Query("userName") String userName,
            @Query("pageCount") int pageCount,
            @Query("pageSize") int pageSize);

    /**
     * 获取用户分享的图片
     * @param token 用户 token
     * @param userName  用户名
     * @param pageCount 查询第几页
     * @param pageSize  每页容量
     * @return
     */
    @GET("user_handle_field/get_shared")
    Call<ResultVO<List<PictureShared>>> getSharedPictures(
            @Query("token") String token,
            @Query("userName") String userName,
            @Query("pageCount") int pageCount,
            @Query("pageSize") int pageSize);

    /**
     * 随机获取所有用户分享的图片
     * @param pageSize  单页容量
     * @return
     */
    @GET("user_handle_field/random_shared")
    Call<ResultVO<List<PictureShared>>> randomSharedPictures(
            @Query("pageSize") int pageSize);

    /**
     * 用户图片分享操作
     * @param token 用户 token
     * @param userId    用户 Id
     * @param userName  用户名
     * @param parts 待上传的图片文件内容
     * @return
     */
    @Multipart
    @FormUrlEncoded
    @POST("user_handle_field/share")
    Call<ResultVO> sharePictures(@Part("token") String token,
                                 @Part("userId") String userId,
                                 @Part("userName") String userName,
                                 @Part List<MultipartBody.Part> parts);

}
