package com.yk.demo.lib.rxjava.core;

import com.yk.demo.lib.exception.MvpViewNotAttachedException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Description：BasePresenter
 * <p>
 * Base abstract class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 * <p>
 * 具体主题抽象类
 * @author yk
 * @version V1.0.0
 * created at 2016/11/25 10:51
 */
public abstract class BasePresenter<T> implements Presenter<T> {

    protected T mvpView;
    protected CompositeSubscription mCompositeSubscription;

    @Override
    public void attachView(T mvpView) {
        this.mvpView = mvpView;
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        this.mvpView = null;
        onUnsubscribe();
    }

    private void checkViewAttached() {
        if (mvpView == null)
            throw new MvpViewNotAttachedException();
    }

    /**
     * 关联网络返回结果与subscriber
     * @param observable 请求返回
     * @param subscriber 请求返回rxJava统一回调对象
     */
    protected void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        checkViewAttached();
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    /** RXjava取消注册，以避免内存泄露 */
    private void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription = null;
        }
    }
}
