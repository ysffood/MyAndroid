package com.yk.demo.myandroid.ui;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.yk.demo.myandroid.R;

import butterknife.BindView;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import zlc.season.rxdownload.RxDownload;
import zlc.season.rxdownload.entity.DownloadEvent;
import zlc.season.rxdownload.entity.DownloadStatus;
import zlc.season.rxdownload.function.Utils;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class TestBackgroundDownActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.bt_down2)
    Button button;
    @BindView(R.id.progress2)
    ProgressBar progressBar;
    @BindView(R.id.size2)
    TextView tv_size;
    @BindView(R.id.percent2)
    TextView percent;

    private RxDownload rxdownload;

    final String defaultPath = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath();
    final String url = "http://192.168.191.1:8080/WebProjectLC/file/mobileqq_android.apk";
    /** 用户监听下载进度状态 */
    private Subscription subscription;

    @Override
    protected void intPresenter() {
        //// TODO: 2016/12/8 初始化http请求presenter
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_service_down;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        rxdownload = RxDownload.getInstance().context(this); // 每次返回的是一个全新的对象.

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == button){
            startDownLoad();
        }
    }

    /** 获取下载状态 */
    @Override
    protected void onResume() {
        super.onResume();
        subscription = rxdownload.receiveDownloadStatus(url)
                .subscribe(new Subscriber<DownloadEvent>() {
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
                    public void onNext(final DownloadEvent event) {
                        //下载状态
                        updateProgress(event);
                    }
                });
    }

    /** 启动后台下载 */
    private void startDownLoad(){
        rxdownload.autoInstall(true)                //下载完成自动安装
                .maxRetryCount(10)                //设置下载失败重试次数
                .maxDownloadNumber(3);            //设置同时最大下载数量
        RxPermissions.getInstance(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (!granted) {
                            throw new RuntimeException("no permission");
                        }
                    }
                })
                .compose(rxdownload.transformService(url, "QQ_service.apk", defaultPath))
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Toast.makeText(TestBackgroundDownActivity.this, "下载开始", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateProgress(DownloadEvent event) {
        DownloadStatus status = event.getDownloadStatus();
        progressBar.setIndeterminate(status.isChunked);
        progressBar.setMax((int) status.getTotalSize());
        progressBar.setProgress((int) status.getDownloadSize());
        percent.setText(status.getPercent());
        tv_size.setText(status.getFormatStatusString());
    }

    /** 取消订阅后不再监听下载进度，下载不中断 */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.unSubscribe(subscription);
    }
}
