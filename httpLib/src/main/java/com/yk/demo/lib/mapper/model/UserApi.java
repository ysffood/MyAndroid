package com.yk.demo.lib.mapper.model;

import com.yk.demo.lib.bean.HttpResponse;
import com.yk.demo.lib.bean.response.UserBean;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 用户API相关接口
 *
 * @author yk
 * @version V1.0.0
 * created at 2016/11/23 16:26
 */
public interface UserApi {

    /**
     * 获取用户信息列表
     * @return 用户列表
     */
    @GET("WebProjectLC/lc/userInfo/getUserList")
    Observable<HttpResponse<ArrayList<UserBean>>> getUsers();

    /**
     * 更改用户信息get请求方式
     * @param id 用户id
     * @param no 用户编号
     * @param name 用户姓名
     * @param sex 用户性别
     * @param department 用户部门
     * @return 服务器返回对象
     */
    @GET("WebProjectLC/lc/userInfo/updateUserInfo")
    Observable<HttpResponse<String>> updateUsers(@Query("id") int id, @Query("no") int no
            , @Query("name") String name, @Query("sex") String sex, @Query("department") String department);

    /**
     * 更改用户信息post请求方式
     * @param id 用户id
     * @param no 用户编号
     * @param name 用户姓名
     * @param sex 用户性别
     * @param department 用户部门
     * @return 服务器返回对象
     */
    @FormUrlEncoded
    @POST("WebProjectLC/lc/userInfo/updateUserInfo")
    Observable<HttpResponse<String>> updateUsersForPost(@Field("id") int id, @Field("no") int no
            , @Field("name") String name, @Field("sex") String sex, @Field("department") String department);
}
