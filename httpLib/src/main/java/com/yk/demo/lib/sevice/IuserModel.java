package com.yk.demo.lib.sevice;

import com.yk.demo.lib.bean.HttpResponse;
import com.yk.demo.lib.bean.response.UserBean;

import java.util.ArrayList;

import rx.Observable;

/**
 * 用户相关接口api抽象服务类
 *
 * @author yk
 * @version V1.0.0
 * created at 2016/11/25 9:58
 */
public interface IuserModel {
    /**
     * 获取用户列表
     * @return 服务器返回对象
     */
    public Observable<HttpResponse<ArrayList<UserBean>>> getUserList();

    /**
     * 更改用户信息get请求方式
     * @param id 用户id
     * @param no 用户编号
     * @param name 用户姓名
     * @param sex 用户性别
     * @param department 用户部门
     * @return 服务器返回对象
     */
    public Observable<HttpResponse<String>> UpdateUserInfo(int id, int no, String name, String sex, String department);

    /**
     * 更改用户信息post请求方式
     * @param id 用户id
     * @param no 用户编号
     * @param name 用户姓名
     * @param sex 用户性别
     * @param department 用户部门
     * @return 服务器返回对象
     */
    public Observable<HttpResponse<String>> updateUsersForPost(int id, int no, String name, String sex, String department);
}
