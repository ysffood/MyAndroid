package com.yk.demo.lib.mapper;

import com.yk.demo.lib.client.RetrofitClent;
import com.yk.demo.lib.mapper.model.UserApi;

import retrofit2.Retrofit;

/**
 * Api配置加载分发
 * @author yk
 * @version V1.0.0
 * created at 2016/11/23 17:04
 */
public class ApiManager {

    private Retrofit retrofit;

    private ApiManager(){
        retrofit = RetrofitClent.getRetrofit();
    }

    static class ApiHolder {
        private static ApiManager apiManager = new ApiManager();
    }

    /**
     * 获取PhoneApi实例
     * @return ApiManager
     */
    public static ApiManager getInstance() {
        return ApiHolder.apiManager;
    }

    /**
     * 用户相关模块接口
     * @return UserApi
     */
    public UserApi getUserApi(){
        return retrofit.create(UserApi.class);
    }
}
