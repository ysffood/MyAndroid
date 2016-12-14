package com.yk.demo.lib.rxjava.presenter.imp;

import com.yk.demo.lib.bean.HttpResponse;
import com.yk.demo.lib.bean.response.UploadFile;
import com.yk.demo.lib.exception.AppException;
import com.yk.demo.lib.rxjava.core.BasePresenter;
import com.yk.demo.lib.rxjava.core.MvpView;
import com.yk.demo.lib.rxjava.core.SubscriberCallBack;
import com.yk.demo.lib.rxjava.presenter.IUpFileRequest;
import com.yk.demo.lib.rxjava.presenter.IUpMultiFileRequest;
import com.yk.demo.lib.rxjava.presenter.IUpSuffixFileRequest;
import com.yk.demo.lib.rxjava.viewcallback.IFileUpView;
import com.yk.demo.lib.sevice.imp.FileUpModelImp;

import java.io.File;
import java.util.Map;

/**
 * 文件上传请求Presenter
 *  一个页面的所有http请求对应一个presenter,对应一个页面返回(IHomeUserView)接口
 * @author yk
 * @version V1.0.0
 * created at 2016/12/12 17:23
 */

public class FileUpPresenter extends BasePresenter<IFileUpView> implements IUpFileRequest, IUpMultiFileRequest, IUpSuffixFileRequest {

    public FileUpPresenter(IFileUpView iFileUpView){
        this.attachView(iFileUpView);
    }

    @Override
    public void sendUpFileRequest(File file) {
        SubscriberCallBack<HttpResponse<UploadFile>> subscriber = new SubscriberCallBack<>(this.mCompositeSubscription,
                new MvpView() {
                    @Override
                    public void onSuccess(Object data) {
                        mvpView.getFileUpSuccessful(data);
                    }

                    @Override
                    public void onFailure(AppException e) {
                        mvpView.getFileUpFailure(e);
                    }

                    @Override
                    public void onHttpError(AppException e) {
                        mvpView.getFileUpFailure(e);
                    }
                });
        // 加入RxJava异步处理set集合,等待请求后接收回调 <UploadFile>
        addSubscription(FileUpModelImp.getFileUpModelImp().uploadFile(file),subscriber);
    }

    @Override
    public void sendUpMultiFileRequest(Map<String, File> files) {
        SubscriberCallBack<HttpResponse<UploadFile>> subscriber = new SubscriberCallBack<>(this.mCompositeSubscription,
                new MvpView() {
                    @Override
                    public void onSuccess(Object data) {
                        mvpView.getMultiFileUpSuccessful(data);
                    }

                    @Override
                    public void onFailure(AppException e) {
                        mvpView.getMultiFileUpFailure(e);
                    }

                    @Override
                    public void onHttpError(AppException e) {
                        mvpView.getMultiFileUpFailure(e);
                    }
                });
        // 加入RxJava异步处理set集合,等待请求后接收回调 <UploadFile>
        addSubscription(FileUpModelImp.getFileUpModelImp().uploadMultiFile(files),subscriber);
    }

    @Override
    public void sendUpSuffixFileRequest(Map<String, File> files, String test1, String test2) {
        SubscriberCallBack<HttpResponse<UploadFile>> subscriber = new SubscriberCallBack<>(this.mCompositeSubscription,
                new MvpView() {
                    @Override
                    public void onSuccess(Object data) {
                        mvpView.getSuffixFileUpSuccessful(data);
                    }

                    @Override
                    public void onFailure(AppException e) {
                        mvpView.getSuffixFileUpFailure(e);
                    }

                    @Override
                    public void onHttpError(AppException e) {
                        mvpView.getSuffixFileUpFailure(e);
                    }
                });
        // 加入RxJava异步处理set集合,等待请求后接收回调 <UploadFile>
        addSubscription(FileUpModelImp.getFileUpModelImp().uploadSuffixFile(files,test1,test2),subscriber);
    }
}
