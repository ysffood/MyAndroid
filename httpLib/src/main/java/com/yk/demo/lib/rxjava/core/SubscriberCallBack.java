package com.yk.demo.lib.rxjava.core;

import com.yk.demo.lib.bean.HttpResponse;
import com.yk.demo.lib.config.HttpCodeHelper;
import com.yk.demo.lib.exception.AppException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * 具体观察者角色类,接受RxJva的回调manager类
 * @author yk
 * @version V1.0.0
 * created at 2016/11/25 14:05
 */
public class SubscriberCallBack<T extends HttpResponse> extends Subscriber<T>{

    /** 服务器请求成功 */
    private static final int CODE = 200;

    private CompositeSubscription mCompositeSubscription;
    private MvpView callBack;

    public SubscriberCallBack(CompositeSubscription mCompositeSubscription, MvpView callBack){
        this.mCompositeSubscription = mCompositeSubscription;
        this.callBack = callBack;
    }

    @Override
    public void onCompleted() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.remove(this);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        AppException appException = null;
        if (e instanceof HttpException){
            HttpException httpException = (HttpException)e;
            int code = httpException.code();
            String msg = httpException.getMessage();
            switch (code){
                case 404:
                    msg = "服务器异常";
                    break;
                case 504:
                    msg = "服务器异常";
                    break;
                default:
                    // TODO: 2016/11/25 统一网络异常处理
                    msg = "未匹配服务器异常码";
                    break;
            }
            appException = new AppException(code,msg);
        }else if (e instanceof SocketTimeoutException){
            appException = new AppException(0,"服务器响应超时");
        }else if (e instanceof ConnectException){
            appException = new AppException(0,"服务器连接超时");
        }else{
            appException = new AppException(0,"未匹配异常");
        }
        callBack.onHttpError(appException);
    }

    @Override
    public void onNext(T t) {
        if (t.getCode() == CODE){
            callBack.onSuccess(t);
        }else {
            //// TODO: 2016/11/25 统一业务错误码处理
            AppException exception = new AppException(t.getCode(), HttpCodeHelper.getErrorMessage(t.getCode()));
            callBack.onFailure(exception);
        }
    }
}
