package com.example.zl.imageshowapplication.utils.okhttputil;

import okhttp3.OkHttpClient;

/**
 * Created by ZhongLeiDev on 2018/9/11.
 */

public class OkHttpUtils {
    private OkHttpClient build;

    public OkHttpClient getBuild() {
        return build;
    }

    private OkHttpUtils() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        build = builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), new SSLTrustAllManager()).build();
    }

    private static class SingletonHolder {
        private static OkHttpUtils instance = new OkHttpUtils();
    }

    public static OkHttpUtils getOkHttpClient() {
        return SingletonHolder.instance;
    }
}
