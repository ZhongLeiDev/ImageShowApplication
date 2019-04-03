package com.example.zl.locallogin;

import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.linkanalyzestrategy.retrofits.RetrofitFactory;
import com.example.zl.imageshowapplication.myinterface.RetrofitInfoService;
import com.example.zl.locallogin.bean.ISUser;
import com.example.zl.locallogin.bean.LocalError;
import com.example.zl.locallogin.localenum.LocalEnum;

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

    /**
     * 私有化构造函数，避免实例化
     */
    private LocalUserHandle() { }

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
                   ResultVO<ISUser> result = response.body();
                   if (result.getCode() == 0) {
                       callback.done(result, null);
                   } else {
                       callback.done(result, new LocalError(result.getCode(), result.getMsg()));
                   }
               }

               @Override
               public void onFailure(Call<ResultVO<ISUser>> call, Throwable t) {
                    callback.done(new ResultVO(), new LocalError(LocalEnum.NET_ERROR));
               }
           });
       } else {
            callback.done(new ResultVO(), new LocalError(LocalEnum.REGISTER_INFO_MISSING));
       }
    }

    /**
     * 登录
     * @param userName  用户名
     * @param passWord  密码
     * @param token token
     * @param callback  回调实例
     */
    public static void doLogin(String userName, String passWord, String token,
                               final LocalCallback<ResultVO> callback) {
        if (!userName.isEmpty() && !passWord.isEmpty()) {
            Call<ResultVO<ISUser>> call = service.login(userName, passWord, token);
            call.enqueue(new Callback<ResultVO<ISUser>>() {
                @Override
                public void onResponse(Call<ResultVO<ISUser>> call, Response<ResultVO<ISUser>> response) {
                    ResultVO<ISUser> result = response.body();
                    if (result.getCode() == 0) {
                        setCurrentUser(result.getData());
                        callback.done(result, null);
                    } else {
                        callback.done(result, new LocalError(result.getCode(), result.getMsg()));
                    }
                }

                @Override
                public void onFailure(Call<ResultVO<ISUser>> call, Throwable t) {
                    callback.done(new ResultVO(), new LocalError(LocalEnum.NET_ERROR));
                }
            });
        } else {
            callback.done(new ResultVO(), new LocalError(LocalEnum.LOGIN_INFO_MISSING));
        }
    }

    /**
     * 注销
     * @param token token
     * @param callback  回调实例
     */
    public static void doLogout(String token, final LocalCallback<ResultVO> callback) {
        Call<ResultVO<ISUser>> call = service.logout(token);
        call.enqueue(new Callback<ResultVO<ISUser>>() {
            @Override
            public void onResponse(Call<ResultVO<ISUser>> call, Response<ResultVO<ISUser>> response) {
                ResultVO<ISUser> result = response.body();
                if (result.getCode() == 0) {
                    setCurrentUser(null);
                    callback.done(result, null);
                } else {
                    callback.done(result, new LocalError(result.getCode(), result.getMsg()));
                }
            }

            @Override
            public void onFailure(Call<ResultVO<ISUser>> call, Throwable t) {
                callback.done(new ResultVO(), new LocalError(LocalEnum.NET_ERROR));
            }
        });
    }

    /**
     * 更改头像
     * @param avatarPath  新选择的头像路径
     * @param callback  回调实例
     */
    public static void doAvatarChange(String avatarPath, final LocalCallback<ResultVO> callback) {
        if (currentUser() != null && !avatarPath.isEmpty()) {
            File file = new File(avatarPath);
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("avatar", file.getName(), imageBody);
            Call<ResultVO<String>> call = service.changeAvatar(currentUser.getUserName(),imageBodyPart);
            call.enqueue(new Callback<ResultVO<String>>() {
                @Override
                public void onResponse(Call<ResultVO<String>> call, Response<ResultVO<String>> response) {
                    ResultVO<String> result = response.body();
                    if (result.getCode() == 0) {
                        callback.done(result, null);
                    } else {
                        callback.done(new ResultVO(), new LocalError(result.getCode(), result.getMsg()));
                    }
                }

                @Override
                public void onFailure(Call<ResultVO<String>> call, Throwable t) {
                    callback.done(new ResultVO(), new LocalError(LocalEnum.NET_ERROR));
                }
            });
        } else {
            callback.done(new ResultVO(), new LocalError(LocalEnum.AVATAR_INFO_MISSING));
        }
    }

}
