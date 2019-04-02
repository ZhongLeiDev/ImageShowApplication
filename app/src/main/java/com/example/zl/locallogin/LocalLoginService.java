package com.example.zl.locallogin;

import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.locallogin.bean.ISUser;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by ZhongLeiDev on 2019/4/2.
 * Retrofit 注册登录接口
 */
public interface LocalLoginService {

    @Multipart
    @POST("user_login_field/register")
    Call<ResultVO<ISUser>> register(@Part("userName") String userName,
                                    @Part("passWord") String passWord,
                                    @Part MultipartBody.Part file,
                                    @Part("mail") String mail);

    @POST("user_login_field/login")
    Call<ResultVO<ISUser>> login(@Part("userName") String userName,
                                    @Part("passWord") String passWord,
                                    @Part("token") String token);

    @POST("user_login_field/logout")
    Call<ResultVO<ISUser>> logout(@Part("token") String token);

    @Multipart
    @POST("user_login_field/change_avatar")
    Call<String> uploadImageWithInfo(@Part("userName") String username,
                                     @Part MultipartBody.Part file);

}
