package com.example.zl.imageshowapplication.linkanalyzestrategy.retrofits;

import com.example.zl.imageshowapplication.myinterface.RetrofitInfoService;
import com.example.zl.imageshowapplication.utils.okhttputil.OkHttpUtils;

import java.util.Collections;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZhongLeiDev on 2018/5/17.
 * Retrofit 相关方法的创建工厂
 */

public class RetrofitFactory {

    /** Geek 访问 URL */
    private static final String GEEK_REQUEST_URL = "https://gank.io/api/";
    /** Bcy 访问 URL*/
    private static final String BCY_REQUEST_URL = "http://112.74.42.204:8080/coser/";
    /** HuaBan 访问 URL*/
    private static final String HUABAN_REQUEST_URL = "http://api.huaban.com/";

    private static RetrofitInfoService infoService;
    /** Geek 使用的 Retrofit 实例*/
    private static Retrofit geekRetrofit;
    /** Bcy 使用的 Retrofit 实例*/
    private static Retrofit bcyRetrofit;
    /** HuaBan 使用的 Retrofit 实例*/
    private static Retrofit huabanRetrofit;

    /**
     * 获取 GeekRetrofitInfoService 单例
     * @return
     */
    public static RetrofitInfoService getGeekRetroSingleInstance() {
        if (geekRetrofit == null) {

            geekRetrofit = new Retrofit.Builder()

                    .client(OkHttpUtils.getOkHttpClient().getBuild())

                    .baseUrl(GEEK_REQUEST_URL)

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
        }
        infoService = geekRetrofit.create(RetrofitInfoService.class);
        return infoService;
    }

    /**
     * 获取 BcyRetrofitInfoService 单例
     * @return
     */
    public static RetrofitInfoService getBcyRetroSingleInstance() {
        if (bcyRetrofit == null) {
            bcyRetrofit = new Retrofit.Builder()

                    .client(new OkHttpClient())

                    .baseUrl(BCY_REQUEST_URL)

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
        }
        infoService = bcyRetrofit.create(RetrofitInfoService.class);
        return infoService;
    }

    /**
     * 获取 HuaBanRetrofitInfoService 单例
     * @return
     */
    public static RetrofitInfoService getHuaBanRetroSingleInstance() {
        if (huabanRetrofit == null) {
            huabanRetrofit = new Retrofit.Builder()

                    .client(new OkHttpClient())

                    .baseUrl(HUABAN_REQUEST_URL)

                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //添加对 RxJava 的支持

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
        }
        infoService = huabanRetrofit.create(RetrofitInfoService.class);
        return infoService;
    }

}
