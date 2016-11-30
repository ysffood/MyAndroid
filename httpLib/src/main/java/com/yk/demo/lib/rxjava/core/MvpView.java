package com.yk.demo.lib.rxjava.core;

import com.yk.demo.lib.exception.AppException;

/**
 * 请求接口回调
 * RxJava接受回调后由SubscriberCallBack统一分发给对应的Presenter具体类
 * Description：MvpView
 * <p>
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 * <p>
 * @author yk
 * @version V1.0.0
 * created at 2016/11/25 14:25
 */
public interface MvpView {

    /**
     * 获取业务数据成功
     * @param data 数据
     */
    void onSuccess(Object data);

    /**
     * 获取业务数据失败
     * @param e 异常
     */
    void onFailure(AppException e);

    /**
     * 网络异常
     * @param e 异常
     */
    void onHttpError(AppException e);
}
