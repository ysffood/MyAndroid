package com.yk.demo.myandroid.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.yk.demo.myandroid.R;
import com.yk.demo.myandroid.download.DownloadController;

import java.io.File;

import butterknife.BindView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import zlc.season.rxdownload.RxDownload;
import zlc.season.rxdownload.entity.DownloadStatus;
import zlc.season.rxdownload.function.Utils;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * 基础的文件下载调用，关闭页面后停止下载（前台下载）
 * 支持断点下载
 * 多线程，默认10个线程同时下载
 * 失败默认再请求连接10次
 * 下载完成后自动安装
 * 适配android6.0
 * @author yk
 * @version V1.0.0
 * created at 2016/12/7 11:40
 */
public class TestBasicDownActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.bt_down)
    Button bt_start; // 开始下载按钮
    @BindView(R.id.progress)
    ProgressBar progressBar; // 下载进度条
    @BindView(R.id.size)
    TextView tv_size; // 总大小
    @BindView(R.id.percent)
    TextView percent; // 下载进度
    @BindView(R.id.tv_status)
    TextView tv_status; // 下载状态
    private Subscription subscription;
    private RxDownload rxdownload;
    final String defaultPath = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath(); // 默认下载到存储设备download文件夹
    final String url = "http://192.168.191.1:8080/WebProjectLC/file/mobileqq_android.apk";
    final String saveName = "qq.apk";
    private DownloadController downloadController; // 下载状态管理器 开始 暂停 取消 安装

    /** 用于接受下载状态更改后事件执行的回调 */
    private DownloadController.Callback statusCall = new DownloadController.Callback() {
        @Override
        public void startDownload() {
            startDownLoad();
        }

        @Override
        public void pauseDownload() {
            pauseDown();
        }

        @Override
        public void cancelDownload() {

        }

        @Override
        public void install() {
            installApk();
        }
    };

    @Override
    protected void intPresenter() {
        //// TODO: 2016/12/8 初始化http请求presenter
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_down_load;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        rxdownload = RxDownload.getInstance(); // 每次返回的是一个全新的对象.

        bt_start.setOnClickListener(this);

        downloadController = new DownloadController(tv_status,bt_start);
    }

    @Override
    public void onClick(View view) {
        if (view == bt_start){
            // 执行DownloadController中当前state状态对应的事件
            downloadController.startHandleClick(statusCall);
        }
    }

    /**
     * 开始基础下载
     */
    private void startDownLoad(){
        rxdownload.context(this)                  //自动安装需要Context
                .maxThread(10)                    //设置最大线程
                .maxRetryCount(10)                //设置下载失败重试次数
//                .retrofit(myRetrofit)             //若需要自己的retrofit客户端,可在这里指定
                .autoInstall(true);               //下载完成自动安装
        // download task对应一个下载任务
        subscription = RxPermissions.getInstance(this)    // RxPermission是为Android 6.0解决运行时权限的一个库
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE) //申请存储卡权限
                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (!granted) {           //权限被拒绝
                            throw new RuntimeException("no permission");
                        }
                    }
                })
                .compose(rxdownload.transform(url, saveName, defaultPath))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DownloadStatus>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        downloadController.setState(new DownloadController.Started());
                    }
                    @Override
                    public void onCompleted() {
                        //下载完成
                        Log.d("onCompleted","onCompleted");
                        downloadController.setState(new DownloadController.Completed());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //下载出错
                        Log.d("onError",e.getMessage());
                        downloadController.setState(new DownloadController.Failed());
                    }

                    @Override
                    public void onNext(final DownloadStatus status) {
                        //下载状态
                        updateProgress(status);
                    }
                });
    }

    /** 暂停下载 */
    private void pauseDown(){
        // 暂停Service中下载地址为url的下载任务.
        rxdownload.pauseServiceDownload(url);
        // 切换当前下载状态
        downloadController.setState(new DownloadController.Paused());
        // 停止下载
        Utils.unSubscribe(subscription);
    }

    /**
     * 动态更新下载进度
     * @param status 下载状态信息
     */
    private void updateProgress(DownloadStatus status) {
        progressBar.setIndeterminate(status.isChunked);
        progressBar.setMax((int) status.getTotalSize());
        progressBar.setProgress((int) status.getDownloadSize());
        percent.setText(status.getPercent());
        tv_size.setText(status.getFormatStatusString());
    }

    /** 安装 */
    private void installApk() {
        Uri uri = Uri.fromFile(new File(defaultPath + File.separator + saveName));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /** 取消订阅,停止下载 */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.unSubscribe(subscription);
    }
}
