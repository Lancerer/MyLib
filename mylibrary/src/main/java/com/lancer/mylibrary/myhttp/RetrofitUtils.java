package com.lancer.mylibrary.myhttp;

import java.util.concurrent.TimeUnit;

import com.lancer.serviceandreceiver.myhttp.Interceptor.LogInterceptor;
import com.lancer.serviceandreceiver.myhttp.Interceptor.SaveCookiesInterceptor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    private static final int DEFAULT_TIME_OUT_READ_SECOND = 60; // 默认读取超时时间
    private static final int DEFAULT_TIME_OUT_CONNECT_SECOND = 30; // 默认连接超时时间
    private static Retrofit retrofit;
    private volatile static RetrofitUtils INSTANCE;

    public static RetrofitUtils getInstance(String baseUrl) {
        if (INSTANCE == null) {
            synchronized (RetrofitUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitUtils(baseUrl, getOkHttpClient());
                }
            }
        }
        return INSTANCE;
    }

    public static RetrofitUtils getInstance(String baseUrl, OkHttpClient okHttpClient) {
        if (INSTANCE == null) {
            synchronized (RetrofitUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitUtils(baseUrl, okHttpClient);
                }
            }
        }
        return INSTANCE;
    }

    private RetrofitUtils(String baseUrl, OkHttpClient client) {
        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * 获取一个 OkHttpClient 对象
     *
     * @return OkHttpClient
     */
    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        //设置网络连接超时时间及数据读取超时时间
        okHttpBuilder.readTimeout(DEFAULT_TIME_OUT_READ_SECOND, TimeUnit.SECONDS);
        okHttpBuilder.connectTimeout(DEFAULT_TIME_OUT_CONNECT_SECOND, TimeUnit.SECONDS);
        // 设置失败重连
        okHttpBuilder.retryOnConnectionFailure(false);
        // 设置 Log
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // 设置 Log 级别
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.interceptors().add(logging);
        okHttpBuilder.addInterceptor(new LogInterceptor());

        // 设置头部信息
//            okHttpBuilder.interceptors().add(new CustomHeaderInterceptor());
        return okHttpBuilder.build();
    }

    /**
     * 获取一个 OkHttpClient 对象
     *
     * @param interceptor 添加拦截器
     * @return OkHttpClient
     */
    public static OkHttpClient getOkHttpClient(Interceptor interceptor) {
        return getOkHttpClient().newBuilder().addInterceptor(interceptor).build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
