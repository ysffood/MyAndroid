package com.yk.demo.lib.rxjava.core;

/**
 * 抽象主题角色接口
 * Description：Presenter
 * <p>
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 * <p>
 * @author yk
 * @version V1.0.0
 * created at 2016/11/25 10:45
 */
public interface Presenter<T> {

    /**
     * 添加绑定页面对象引用
     * @param mvpView 页面对象
     */
    void attachView(T mvpView);

    /**
     * 解绑页面对象引用
     */
    void detachView();
}
