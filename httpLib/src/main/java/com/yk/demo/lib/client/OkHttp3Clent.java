package com.yk.demo.lib.client;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * okhttp3客户端初始化配置
 *
 * @author yk
 * @version V1.0.0
 * created at 2016/11/23 17:21
 */
public class OkHttp3Clent {

    public static OkHttpClient getHttpClient(){
        // Log信息拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        if (BuildConfig.DEBUG) {
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
//        }

        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request newRequest = original.newBuilder()
                        .addHeader("User-Agent", "Your-App-Name")
//                        .header("Accept", "application/vnd.yourapi.v1.full+json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(newRequest);
            }
        };
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(logging) //设置 Debug Log 模式
                .addInterceptor(headerInterceptor) //设置 OkHttp 请求拦截器
//                .cache(new Cache(new File(context.getExternalCacheDir(), "http_cache"), 1024 * 1024 * 100))
                .connectTimeout(10, TimeUnit.SECONDS) // 设置请求连接的超时时间
                .writeTimeout(30, TimeUnit.SECONDS) // 设置请求写的超时时间
                .readTimeout(30, TimeUnit.SECONDS) // 设置请求读的超时时间
                .build();
        return httpClient;
    }
}
