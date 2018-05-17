package com.example.zl.imageshowapplication.linkanalyzestrategy.retrofits;

import com.example.zl.imageshowapplication.myinterface.RetrofitInfoService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZhongLeiDev on 2018/5/17.
 * Retrofit 相关方法的创建工厂
 */

public class RetrofitFactory {

    /** Geek 访问 URL */
    private static final String GEEK_REQUEST_URL = "http://gank.io/api/";

    private static RetrofitInfoService infoService;
    /** Geek 使用的 Retrofit 实例*/
    private static Retrofit geekRetrofit;

    /**
     * 获取 GeekRetrofitInfoService 单例
     * @return
     */
    public static RetrofitInfoService getGeekRetroSingleInstance() {
        if (geekRetrofit == null) {
            geekRetrofit = new Retrofit.Builder()

                    .client(new OkHttpClient())

                    .baseUrl(GEEK_REQUEST_URL)

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
        }
        infoService = geekRetrofit.create(RetrofitInfoService.class);
        return infoService;
    }

}
