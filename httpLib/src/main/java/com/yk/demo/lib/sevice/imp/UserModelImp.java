package com.yk.demo.lib.sevice.imp;

import com.yk.demo.lib.mapper.ApiManager;
import com.yk.demo.lib.bean.HttpResponse;
import com.yk.demo.lib.bean.response.UserBean;
import com.yk.demo.lib.mapper.model.UserApi;
import com.yk.demo.lib.sevice.IuserModel;

import java.util.ArrayList;

import rx.Observable;

/**
 * 用户相关接口API具体服务类
 * @author yk
 * @version V1.0.0
 * created at 2016/11/25 10:10
 */
public class UserModelImp implements IuserModel {

    private UserModelImp(){}

    private static class UserModelImpHolder{
        private static UserModelImp userModelImp = new UserModelImp();
    }

    public static UserModelImp getUserModelImp(){
        return UserModelImpHolder.userModelImp;
    }

    /**
     * 获取用户列表
     * @return 服务器返回对象
     */
    @Override
    public Observable<HttpResponse<ArrayList<UserBean>>> getUserList() {
        UserApi userApi = ApiManager.getInstance().getUserApi();
        return userApi.getUsers();
    }

    /**
     * 更改用户信息
     * @param id 用户id
     * @param no 用户编号
     * @param name 用户姓名
     * @param sex 用户性别
     * @param department 用户部门
     * @return 服务器返回对象
     */
    @Override
    public Observable<HttpResponse<String>> UpdateUserInfo(int id, int no, String name, String sex, String department) {
        UserApi userApi = ApiManager.getInstance().getUserApi();
        return userApi.updateUsers(id,no,name,sex,department);
    }

    /**
     * 更改用户信息
     * @param id 用户id
     * @param no 用户编号
     * @param name 用户姓名
     * @param sex 用户性别
     * @param department 用户部门
     * @return 服务器返回对象
     */
    @Override
    public Observable<HttpResponse<String>> updateUsersForPost(int id, int no, String name, String sex, String department) {
        UserApi userApi = ApiManager.getInstance().getUserApi();
        return userApi.updateUsersForPost(id,no,name,sex,department);
    }
}
