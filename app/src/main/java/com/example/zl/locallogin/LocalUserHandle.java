package com.example.zl.locallogin;

import com.alibaba.fastjson.support.retrofit.Retrofit2ConverterFactory;
import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.bean.geek.GeekResult;
import com.example.zl.imageshowapplication.linkanalyzestrategy.retrofits.RetrofitFactory;
import com.example.zl.imageshowapplication.myinterface.RetrofitInfoService;
import com.example.zl.locallogin.bean.ISUser;
import com.example.zl.locallogin.bean.LocalError;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZhongLeiDev on 2019/4/2.
 * 用户数据方法类
 */
public class LocalUserHandle {

    private static ISUser currentUser;
    private static RetrofitInfoService service = RetrofitFactory.getBcyRetroSingleInstance();

    public static void setCurrentUser(ISUser user) {
        currentUser = user;
    }

    public static ISUser currentUser() {
        return currentUser;
    }

    /**
     * 注册
     * @param userName  用户名
     * @param passWord  密码
     * @param avatarPath    图片路径
     * @param mail  邮箱
     * @param callback  回调实例
     */
    public static void doRegister(String userName, String passWord, String avatarPath,
            String mail, final LocalCallback<ResultVO> callback) {
       if (!userName.isEmpty() && !passWord.isEmpty() && !avatarPath.isEmpty() && !mail.isEmpty()) {
           File file = new File(avatarPath);
           RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
           MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("avatar", file.getName(), imageBody);
           Call<ResultVO<ISUser>> call = service.register(userName, passWord,imageBodyPart, mail);
           call.enqueue(new Callback<ResultVO<ISUser>>() {
               @Override
               public void onResponse(Call<ResultVO<ISUser>> call, Response<ResultVO<ISUser>> response) {
                   ResultVO result = response.body();
                   if (result.getCode() == 0) {
                       callback.done(result, null);
                   } else {
                       callback.done(result, new LocalError(result.getCode(), result.getMsg()));
                   }
               }

               @Override
               public void onFailure(Call<ResultVO<ISUser>> call, Throwable t) {
                    callback.done(new ResultVO(), new LocalError(-2, "网络连接错误！"));
               }
           });
       } else {

       }
    }

}
