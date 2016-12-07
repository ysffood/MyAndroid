package com.yk.demo.lib.rxjava.presenter;

import com.yk.demo.lib.bean.response.UserBean;

/**
 * 发送更新用户信息请求
 * 对应主页面
 * @author yk
 * @version V1.0.0
 * created at 2016/11/28 15:40
 */
public interface IUserInfoUpdate {
    /**
     * 更新用户信息请求
     * @param userBean 用户实体
     */
    void sendUserInfoUpdate(UserBean userBean);
}
