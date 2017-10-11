package com.riven.lee.rivennews.model.net.help;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.riven.lee.rivennews.app.APP;
import com.riven.lee.rivennews.model.net.interceptor.CacheInterceptor;
import com.riven.lee.rivennews.model.net.interceptor.LoggingInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author rivenlee
 * @date 2017/10/11 17:42
 */

public class RetrofitHelper {

    private final int cacheSize = 10 * 1024 * 1024;
    private final long connectTimeoutMills = 10 * 1000L;
    private final long readTimeoutMills = 10 * 1000L;
    private OkHttpClient client;
//    private static ZhiHuApi mZhiHuApi = null;
//    private static GankApi mGankApi = null;

    public RetrofitHelper(){
        init();
    }

    private void init() {
//        mZhiHuApi = getApiService(ZhiHuApi.HOST,ZhiHuApi.class);
//        mGankApi = getApiService(GankApi.Host,GankApi.class);

    }

    /**
     * 初始化OkHttp参数
     */
    private OkHttpClient getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (client == null) {
            synchronized (RetrofitHelper.class) {
                if (client == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(APP.getInstance()
                            .getCacheDir(), "HttpCache"), cacheSize);
                    client = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(new LoggingInterceptor())
                            .addNetworkInterceptor(new CacheInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(connectTimeoutMills, TimeUnit.SECONDS)
                            .writeTimeout(readTimeoutMills, TimeUnit.SECONDS)
                            .readTimeout(readTimeoutMills, TimeUnit.SECONDS)
                            .build();
                    return client;
                }
            }
        }
        return client;
    }


    private <T> T getApiService(String host, Class<T> clz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clz);
    }
}
