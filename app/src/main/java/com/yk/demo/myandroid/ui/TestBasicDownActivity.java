package com.yk.demo.myandroid.ui;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.yk.demo.myandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
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
public class TestBasicDownActivity extends Activity implements View.OnClickListener{

    @BindView(R.id.bt_down)
    Button bt_start;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.size)
    TextView tv_size;
    @BindView(R.id.percent)
    TextView percent;
    private Subscription subscription;
    private RxDownload rxdownload;
    final String defaultPath = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath(); // 默认下载到存储设备download文件夹

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_down_load);

        ButterKnife.bind(this);
        rxdownload = RxDownload.getInstance(); // 每次返回的是一个全新的对象.

        bt_start.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == bt_start){
            startDownLoad();
        }
    }

    /**
     * 基础下载
     */
    public void startDownLoad(){
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
                .compose(rxdownload.transform("http://192.168.191.1:8080/WebProjectLC/file/mobileqq_android.apk", "qq.apk", defaultPath))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DownloadStatus>() {
                    @Override
                    public void onCompleted() {
                        //下载完成
                        Log.d("onCompleted","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //下载出错
                        Log.d("onError",e.getMessage());
                    }

                    @Override
                    public void onNext(final DownloadStatus status) {
                        //下载状态
                        progressBar.setIndeterminate(status.isChunked);
                        progressBar.setMax((int) status.getTotalSize());
                        progressBar.setProgress((int) status.getDownloadSize());
                        percent.setText(status.getPercent());
                        tv_size.setText(status.getFormatStatusString());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.unSubscribe(subscription);
    }
}
