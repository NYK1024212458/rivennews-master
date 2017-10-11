package com.riven.lee.rivennews.model.net.interceptor;

import android.util.Log;

import com.riven.lee.rivennews.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author rivenlee
 * @date 2017/10/11 18:54
 */

public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        int maxAge = 60 * 60;
        int maxStale = 60 * 60 * 24;
        Request request = chain.request();//求链中获得请求
        //有网络时从网络中获取
        if(NetWorkUtil.isNetworkConnected()){
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
            Log.d("TAG","request ->");
        }else{
            //无网络时从缓存中获取
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);//执行链中请求获得响应结果
        Log.d("TAG","chain.proceed");
        if(NetWorkUtil.isNetworkConnected()){
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .addHeader("Cache-Control","public, max-age=" + maxAge)
                    .build();
        }else{
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .addHeader("Cache-Control","public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }
}
