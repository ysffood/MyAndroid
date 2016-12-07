package com.yk.demo.lib.rxjava.viewcallback;

/**
 * 观察者角色抽象类
 * 用户Presenter与页面之间数据回调交互
 * @author yk
 * @version V1.0.0
 * created at 2016/11/25 15:22
 */
public interface IHomeUserView {

    /**
     * 获取用户列表请求成功
     * @param data 返回数据
     */
    void getUserListSuccessfull(Object data);

    /**
     * 获取用户列表请求异常
     * @param e 异常
     */
    void getUserListFailure(Exception e);

    /**
     * 更新用户信息请求成功
     * @param data 返回数据
     */
    void updateUserInfoSuccessfull(Object data);

    /**
     * 更新用户请求异常
     * @param e 异常
     */
    void updateUserInfFailure(Exception e);

    /**
     * Post更新用户信息请求成功
     * @param data 返回数据
     */
    void updateUserInfoPostSuccessfull(Object data);

    /**
     * Post更新用户请求异常
     * @param e 异常
     */
    void updateUserInfoPostFailure(Exception e);
}
