package com.yk.demo.lib.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yk.demo.lib.config.ServerConstant;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit2.1初始化配置
 *
 * @author yk
 * @version V1.0.0
 * created at 2016/11/23 17:27
 */
public class RetrofitClent {

    // @formatter:off
    private final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .serializeNulls()
            .create();
    // @formatter:on

    public static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerConstant.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttp3Clent.getHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }
}
