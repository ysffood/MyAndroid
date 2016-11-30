package com.yk.demo.lib.rxjava.subject;

import com.yk.demo.lib.bean.response.UserBean;

/**
 * 发送更新用户信息请求
 * 对应主页面
 * @author yk
 * @version V1.0.0
 * created at 2016/11/28 15:40
 */
public interface IUserInfoUpdatePost {
    /**
     * 更新用户信息请求Post方式
     * @param userBean 用户实体
     */
    void sendUserInfoUpdateByPost(UserBean userBean);
}
