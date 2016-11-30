package com.yk.demo.lib.rxjava.subject.presenter;

import com.yk.demo.lib.bean.HttpResponse;
import com.yk.demo.lib.bean.response.UserBean;
import com.yk.demo.lib.exception.AppException;
import com.yk.demo.lib.rxjava.core.BasePresenter;
import com.yk.demo.lib.rxjava.core.MvpView;
import com.yk.demo.lib.rxjava.core.SubscriberCallBack;
import com.yk.demo.lib.rxjava.observer.IHomeUserView;
import com.yk.demo.lib.rxjava.subject.IUserGetList;
import com.yk.demo.lib.rxjava.subject.IUserInfoUpdate;
import com.yk.demo.lib.rxjava.subject.IUserInfoUpdatePost;
import com.yk.demo.lib.sevice.imp.UserModelImp;

import java.util.ArrayList;

/**
 * 获取用户列表请求presenter
 * 一个页面的所有http请求对应一个presenter,对应一个页面返回(IHomeUserView)接口
 * @author yk
 * @version V1.0.0
 * created at 2016/11/25 11:21
 */
public class HomeViewPresenter extends BasePresenter<IHomeUserView> implements IUserGetList,IUserInfoUpdate,IUserInfoUpdatePost {

    public HomeViewPresenter(IHomeUserView mvpView){
        this.attachView(mvpView);
    }

    @Override
    public void getUserListRequest() {
        SubscriberCallBack<HttpResponse<ArrayList<UserBean>>> subscriber = new SubscriberCallBack<>(this.mCompositeSubscription,
                new MvpView() {
                    @Override
                    public void onSuccess(Object data) {
                        mvpView.getUserListSuccessfull(data);
                    }

                    @Override
                    public void onFailure(AppException e) {
                        mvpView.getUserListFailure(e);
                    }

                    @Override
                    public void onHttpError(AppException e) {
                        mvpView.getUserListFailure(e);
                    }
                });
        // UserModelImp.getUserModelImp().getUserList()
        // 加入RxJava异步处理set集合,等待请求后接收回调 <ArrayList<UserBean>>
        addSubscription(UserModelImp.getUserModelImp().getUserList(),subscriber);
    }

    @Override
    public void sendUserInfoUpdate(UserBean userBean) {
        SubscriberCallBack<HttpResponse<String>> subscriber = new SubscriberCallBack<>(this.mCompositeSubscription,
                new MvpView() {
                    @Override
                    public void onSuccess(Object data) {
                        mvpView.updateUserInfoSuccessfull(data);
                    }

                    @Override
                    public void onFailure(AppException e) {
                        mvpView.updateUserInfFailure(e);
                    }

                    @Override
                    public void onHttpError(AppException e) {
                        mvpView.updateUserInfFailure(e);
                    }
                });
        addSubscription(UserModelImp.getUserModelImp().UpdateUserInfo(userBean.getId(),
                userBean.getNo(),userBean.getName(),userBean.getSex(),userBean.getDepartment()),subscriber);
    }

    @Override
    public void sendUserInfoUpdateByPost(UserBean userBean) {
        SubscriberCallBack<HttpResponse<String>> subscriber = new SubscriberCallBack<>(this.mCompositeSubscription,
                new MvpView() {
                    @Override
                    public void onSuccess(Object data) {
                        mvpView.updateUserInfoPostSuccessfull(data);
                    }

                    @Override
                    public void onFailure(AppException e) {
                        mvpView.updateUserInfoPostFailure(e);
                    }

                    @Override
                    public void onHttpError(AppException e) {
                        mvpView.updateUserInfoPostFailure(e);
                    }
                });
        addSubscription(UserModelImp.getUserModelImp().updateUsersForPost(userBean.getId(),
                userBean.getNo(),userBean.getName(),userBean.getSex(),userBean.getDepartment()),subscriber);
    }
}
