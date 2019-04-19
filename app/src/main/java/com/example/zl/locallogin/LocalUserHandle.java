package com.example.zl.locallogin;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.linkanalyzestrategy.retrofits.RetrofitFactory;
import com.example.zl.imageshowapplication.myinterface.RetrofitInfoService;
import com.example.zl.locallogin.bean.ISUser;
import com.example.zl.locallogin.bean.LocalError;
import com.example.zl.locallogin.bean.PictureCollect;
import com.example.zl.locallogin.bean.PictureRandom;
import com.example.zl.locallogin.bean.PictureShared;
import com.example.zl.locallogin.localenum.LocalEnum;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    /**单页容量*/
    private static final int PAGE_SIZE = 30;

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
            String mail, final LocalCallback<ResultVO<ISUser>> callback) {
       if (!userName.isEmpty() && !passWord.isEmpty() && !avatarPath.isEmpty() && !mail.isEmpty()) {
           File file = new File(avatarPath);
           RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
           MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData(
                   "avatar", file.getName(), imageBody);
           Call<ResultVO<ISUser>> call = service.register(userName, passWord,imageBodyPart, mail);
           call.enqueue(new Callback<ResultVO<ISUser>>() {
               @Override
               public void onResponse(@NonNull Call<ResultVO<ISUser>> call,
                                      @NonNull Response<ResultVO<ISUser>> response) {
                   ResultVO<ISUser> result = response.body();
                   if (result != null) {
                       if (result.getCode() == 0) {
                           callback.done(result, null);
                       } else {
                           callback.done(null, new LocalError(result.getCode(), result.getMsg()));
                       }
                   } else {
                       callback.done(null, new LocalError(LocalEnum.UNCATCHED_ERROR));
                   }
               }

               @Override
               public void onFailure(@NonNull Call<ResultVO<ISUser>> call, @NonNull Throwable t) {
                    callback.done(null, new LocalError(LocalEnum.NET_ERROR));
               }
           });
       } else {
            callback.done(null, new LocalError(LocalEnum.REGISTER_INFO_MISSING));
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
                               final LocalCallback<ResultVO<ISUser>> callback) {
        if (!userName.isEmpty() && !passWord.isEmpty()) {
            Call<ResultVO<ISUser>> call = service.login(userName, passWord, token);
            call.enqueue(new Callback<ResultVO<ISUser>>() {
                @Override
                public void onResponse(@NonNull Call<ResultVO<ISUser>> call,
                                       @NonNull Response<ResultVO<ISUser>> response) {
                    ResultVO<ISUser> result = response.body();
                    if (result != null) {
                        if (result.getCode() == 0) {
                            String token = "token_" + result.getData().getToken().replaceAll("token_", "");    //重构token
                            result.getData().setToken(token);
                            Log.i("LOGIN", "Result->" + result);
                            callback.done(result, null);
                        } else {
                            callback.done(null, new LocalError(result.getCode(), result.getMsg()));
                        }
                    } else {
                        callback.done(null, new LocalError(LocalEnum.UNCATCHED_ERROR));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResultVO<ISUser>> call, @NonNull Throwable t) {
                    callback.done(null, new LocalError(LocalEnum.NET_ERROR));
                }
            });
        } else {
            callback.done(null, new LocalError(LocalEnum.LOGIN_INFO_MISSING));
        }
    }

    /**
     * 注销
     * @param token token
     * @param callback  回调实例
     */
    public static void doLogout(String token, final LocalCallback<ResultVO<ISUser>> callback) {
        Call<ResultVO<ISUser>> call = service.logout(token);
        call.enqueue(new Callback<ResultVO<ISUser>>() {
            @Override
            public void onResponse(@NonNull Call<ResultVO<ISUser>> call,
                                   @NonNull Response<ResultVO<ISUser>> response) {
                ResultVO<ISUser> result = response.body();
                if (result != null) {
                    if (result.getCode() == 0) {
                        callback.done(result, null);
                    } else {
                        callback.done(null, new LocalError(result.getCode(), result.getMsg()));
                    }
                } else {
                    callback.done(null, new LocalError(LocalEnum.UNCATCHED_ERROR));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultVO<ISUser>> call, @NonNull Throwable t) {
                callback.done(null, new LocalError(LocalEnum.NET_ERROR));
            }
        });
    }

    /**
     * 更改头像
     * @param avatarPath  新选择的头像路径
     * @param callback  回调实例
     */
    public static void doAvatarChange(String avatarPath, final LocalCallback<ResultVO<String>> callback) {
        if (currentUser() != null && !avatarPath.isEmpty()) {
            File file = new File(avatarPath);
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData(
                    "avatar", file.getName(), imageBody);
            Call<ResultVO<String>> call = service.changeAvatar(currentUser.getUserName(),imageBodyPart);
            call.enqueue(new Callback<ResultVO<String>>() {
                @Override
                public void onResponse(@NonNull Call<ResultVO<String>> call,
                                       @NonNull Response<ResultVO<String>> response) {
                    ResultVO<String> result = response.body();
                    if (result != null) {
                        if (result.getCode() == 0) {
                            callback.done(result, null);
                        } else {
                            callback.done(null, new LocalError(result.getCode(), result.getMsg()));
                        }
                    } else {
                        callback.done(null, new LocalError(LocalEnum.UNCATCHED_ERROR));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResultVO<String>> call, @NonNull Throwable t) {
                    callback.done(null, new LocalError(LocalEnum.NET_ERROR));
                }
            });
        } else {
            callback.done(null, new LocalError(LocalEnum.AVATAR_INFO_MISSING));
        }
    }

    /**
     * 获取随机多张图片(默认30张)
     * @param callback  回调实例
     */
    public static void getRandomPictures(final LocalCallback<ResultVO<List<PictureRandom>>> callback) {
        Call<ResultVO<List<PictureRandom>>> call = service.randomPictures(PAGE_SIZE);
        call.enqueue(new Callback<ResultVO<List<PictureRandom>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultVO<List<PictureRandom>>> call,
                                   @NonNull Response<ResultVO<List<PictureRandom>>> response) {
                ResultVO<List<PictureRandom>> result = response.body();
                if (result != null) {
                    if (result.getCode() == 0) {
                        callback.done(result, null);
                    } else {
                        callback.done(null, new LocalError(result.getCode(), result.getMsg()));
                    }
                } else {
                    callback.done(null, new LocalError(LocalEnum.UNCATCHED_ERROR));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultVO<List<PictureRandom>>> call, @NonNull Throwable t) {
                callback.done(null, new LocalError(LocalEnum.NET_ERROR));
            }
        });
    }

    /**
     * 获取随机单张图片
     * @param callback  回调实例
     */
    public static void getRandomSinglePicture(final LocalCallback<ResultVO<List<PictureRandom>>> callback) {
        Call<ResultVO<List<PictureRandom>>> call = service.randomSinglePicture();
        call.enqueue(new Callback<ResultVO<List<PictureRandom>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultVO<List<PictureRandom>>> call,
                                   @NonNull Response<ResultVO<List<PictureRandom>>> response) {
                ResultVO<List<PictureRandom>> result = response.body();
                if (result != null) {
                    if (result.getCode() == 0) {
                        callback.done(result, null);
                    } else {
                        callback.done(null, new LocalError(result.getCode(), result.getMsg()));
                    }
                } else {
                    callback.done(null, new LocalError(LocalEnum.UNCATCHED_ERROR));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultVO<List<PictureRandom>>> call, @NonNull Throwable t) {
                callback.done(null, new LocalError(LocalEnum.NET_ERROR));
            }
        });
    }

    /**
     * 收藏 PIN 的方法
     * @param user  用户实例
     * @param pinUrl    PIN 链接
     * @param cover   PIN 封面
     * @param callback  回调实例
     */
    public static void collectPin(ISUser user, String pinUrl, String cover,
                                  final LocalCallback<ResultVO> callback) {
        if (user != null) {
            Call<ResultVO> call = service.collectPin(user.getToken(),user.getUserId(),
                    user.getUserName(),pinUrl,cover);
            call.enqueue(new Callback<ResultVO>() {
                @Override
                public void onResponse(@NonNull Call<ResultVO> call,
                                       @NonNull Response<ResultVO> response) {
                    ResultVO result = response.body();
                    if (result != null) {
                        if (result.getCode() == 0) {
                            callback.done(result, null);
                        } else {
                            callback.done(null, new LocalError(result.getCode(), result.getMsg()));
                        }
                    } else {
                        callback.done(null, new LocalError(LocalEnum.UNCATCHED_ERROR));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResultVO> call, @NonNull Throwable t) {
                    callback.done(null, new LocalError(LocalEnum.NET_ERROR));
                }
            });
        } else {
            callback.done(new ResultVO(), new LocalError(LocalEnum.CURRENTUSER_NULL));
        }
    }

    /**
     * 收藏 BOARD 的方法
     * @param user  用户实例
     * @param boardUrl    BOARD 链接
     * @param cover   BOARD 封面
     * @param callback  回调实例
     */
    public static void collectBoard(ISUser user, String boardUrl, String cover,
                                  final LocalCallback<ResultVO> callback) {
        if (user != null) {
            Call<ResultVO> call = service.collectBoard(user.getToken(),user.getUserId(),
                    user.getUserName(),boardUrl,cover);
            call.enqueue(new Callback<ResultVO>() {
                @Override
                public void onResponse(@NonNull Call<ResultVO> call,
                                       @NonNull Response<ResultVO> response) {
                    ResultVO result = response.body();
                    if (result != null) {
                        if (result.getCode() == 0) {
                            callback.done(result, null);
                        } else {
                            callback.done(null, new LocalError(result.getCode(), result.getMsg()));
                        }
                    } else {
                        callback.done(null, new LocalError(LocalEnum.UNCATCHED_ERROR));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResultVO> call, @NonNull Throwable t) {
                    callback.done(null, new LocalError(LocalEnum.NET_ERROR));
                }
            });
        } else {
            callback.done(null, new LocalError(LocalEnum.CURRENTUSER_NULL));
        }
    }

    /**
     * 获取用户收藏的 PIN
     * @param user  用户实例
     * @param pageCount 当前页码
     * @param callback  回调实例
     */
    public static void getCollectedPins(ISUser user, int pageCount,
                                      final LocalCallback<ResultVO<List<PictureCollect>>> callback) {
        if (user != null) {
            Call<ResultVO<List<PictureCollect>>> call = service.getCollectPins(user.getToken(),
                    user.getUserName(),pageCount,PAGE_SIZE);
            call.enqueue(new Callback<ResultVO<List<PictureCollect>>>() {
                @Override
                public void onResponse(@NonNull Call<ResultVO<List<PictureCollect>>> call,
                                       @NonNull Response<ResultVO<List<PictureCollect>>> response) {
                    ResultVO<List<PictureCollect>> result = response.body();
                    if (result != null) {
                        if (result.getCode() == 0) {
                            callback.done(result, null);
                        } else {
                            callback.done(null, new LocalError(result.getCode(), result.getMsg()));
                        }
                    } else {
                        callback.done(null, new LocalError(LocalEnum.UNCATCHED_ERROR));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResultVO<List<PictureCollect>>> call,
                                      @NonNull Throwable t) {
                    callback.done(null, new LocalError(LocalEnum.NET_ERROR));
                }
            });
        } else {
            callback.done(null, new LocalError(LocalEnum.CURRENTUSER_NULL));
        }
    }

    /**
     * 获取用户收藏的 BOARD
     * @param user  用户实例
     * @param pageCount 当前页码
     * @param callback  回调实例
     */
    public static void getCollectedBoards(ISUser user, int pageCount,
                                          final LocalCallback<ResultVO<List<PictureCollect>>> callback) {
        if (user != null) {
            Call<ResultVO<List<PictureCollect>>> call = service.getCollectBoards(user.getToken(),
                    user.getUserName(),pageCount,PAGE_SIZE);
            call.enqueue(new Callback<ResultVO<List<PictureCollect>>>() {
                @Override
                public void onResponse(@NonNull Call<ResultVO<List<PictureCollect>>> call,
                                       @NonNull Response<ResultVO<List<PictureCollect>>> response) {
                    ResultVO<List<PictureCollect>> result = response.body();
                    if (result != null) {
                        if (result.getCode() == 0) {
                            callback.done(result, null);
                        } else {
                            callback.done(null, new LocalError(result.getCode(), result.getMsg()));
                        }
                    } else {
                        callback.done(null, new LocalError(LocalEnum.UNCATCHED_ERROR));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResultVO<List<PictureCollect>>> call,
                                      @NonNull Throwable t) {
                    callback.done(null, new LocalError(LocalEnum.NET_ERROR));
                }
            });
        } else {
            callback.done(null, new LocalError(LocalEnum.CURRENTUSER_NULL));
        }
    }

    /**
     * 获取用户分享的图片资源
     * @param user  用户实例
     * @param pageCount 当前页码
     * @param callback  回调实例
     */
    public static void getSharedPictures(ISUser user, int pageCount,
                                         final LocalCallback<ResultVO<List<PictureShared>>> callback) {
        if (user != null) {
            Call<ResultVO<List<PictureShared>>> call = service.getSharedPictures(user.getToken(),
                    user.getUserName(),pageCount,PAGE_SIZE);
            call.enqueue(new Callback<ResultVO<List<PictureShared>>>() {
                @Override
                public void onResponse(@NonNull Call<ResultVO<List<PictureShared>>> call,
                                       @NonNull Response<ResultVO<List<PictureShared>>> response) {
                    ResultVO<List<PictureShared>> result = response.body();
                    if (result != null) {
                        if (result.getCode() == 0) {
                            callback.done(result, null);
                        } else {
                            callback.done(null, new LocalError(result.getCode(), result.getMsg()));
                        }
                    } else {
                        callback.done(null, new LocalError(LocalEnum.UNCATCHED_ERROR));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResultVO<List<PictureShared>>> call,
                                      @NonNull Throwable t) {
                    callback.done(null, new LocalError(LocalEnum.NET_ERROR));
                }
            });
        } else {
            callback.done(null, new LocalError(LocalEnum.CURRENTUSER_NULL));
        }
    }

    /**
     * 随机获取所有用户分享的图片资源
     * @param callback  回调实例
     */
    public static void getRandomSharedPictures(final LocalCallback<ResultVO<List<PictureShared>>> callback) {

        Call<ResultVO<List<PictureShared>>> call = service.randomSharedPictures(PAGE_SIZE);
        call.enqueue(new Callback<ResultVO<List<PictureShared>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultVO<List<PictureShared>>> call,
                                   @NonNull Response<ResultVO<List<PictureShared>>> response) {
                ResultVO<List<PictureShared>> result = response.body();
                if (result != null) {
                    if (result.getCode() == 0) {
                        callback.done(result, null);
                    } else {
                        callback.done(null, new LocalError(result.getCode(), result.getMsg()));
                    }
                } else {
                    callback.done(null, new LocalError(LocalEnum.UNCATCHED_ERROR));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultVO<List<PictureShared>>> call,
                                  @NonNull Throwable t) {
                callback.done(null, new LocalError(LocalEnum.NET_ERROR));
            }
        });

    }

    /**
     * 用户分享图片操作
     * @param user  用户实例
     * @param filePaths 待分享的图片列表
     * @param callback  回调实例
     */
    public static void doSharePictures(ISUser user, List<String> filePaths,
                                final LocalCallback<ResultVO> callback) {
        if (user != null) {
            if (filePaths.size() > 0) {
                List<MultipartBody.Part> multipartlist = new ArrayList<>();
                for (String path : filePaths) {
                    File file = new File(path);
                    RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("fileName", file.getName(), imageBody);
                    multipartlist.add(imageBodyPart);
                }
                Call<ResultVO> call = service.sharePictures(user.getToken(),user.getUserId(),
                        user.getUserName(),multipartlist);
                call.enqueue(new Callback<ResultVO>() {
                    @Override
                    public void onResponse(@NonNull Call<ResultVO> call, @NonNull Response<ResultVO> response) {
                        ResultVO result = response.body();
                        if (result != null) {
                            if (result.getCode() == 0) {
                                callback.done(result, null);
                            } else {
                                callback.done(null, new LocalError(result.getCode(), result.getMsg()));
                            }
                        } else {
                            callback.done(null, new LocalError(LocalEnum.UNCATCHED_ERROR));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResultVO> call, @NonNull Throwable t) {
                        callback.done(null, new LocalError(LocalEnum.NET_ERROR));
                    }
                });
            } else {
                callback.done(null, new LocalError(LocalEnum.SHARED_LIST_NULL));
            }
        } else {
            callback.done(null, new LocalError(LocalEnum.CURRENTUSER_NULL));
        }

    }

}
