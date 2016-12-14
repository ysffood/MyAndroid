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
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.yk.demo.myandroid.R;
import com.yk.demo.myandroid.download.DownloadController;

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
    @BindView(R.id.tv_status)
    TextView tv_status;

    private RxDownload rxdownload;

    final String defaultPath = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath();
    final String url = "http://192.168.191.1:8080/WebProjectLC/file/mobileqq_android.apk";
    final String saveName = "qq.apk";
    /** 用户监听下载进度状态 */
    private Subscription subscription;

    private DownloadController downloadController;

    /** 用于接受下载状态更改后事件执行的回调 */
    private DownloadController.Callback callback = new DownloadController.Callback() {
        @Override
        public void startDownload() {
            startDownLoad();
        }

        @Override
        public void pauseDownload() {
            pause();
        }

        @Override
        public void cancelDownload() {
            cancel();
        }

        @Override
        public void install() {
            if (rxdownload.getRealFiles(saveName, defaultPath)[0].exists()){
                installApk();
            }else{
                rxdownload.deleteServiceDownload(url).subscribe();
                startDownLoad();
            }
        }
    };

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
        downloadController = new DownloadController(tv_status,button);
    }

    @Override
    public void onClick(View view) {
        if (view == button){
            downloadController.startHandleClick(callback);
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
                        downloadController.setState(new DownloadController.Completed());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //下载出错
                        Log.d("onError",e.getMessage());
                        downloadController.setState(new DownloadController.Failed());
                    }

                    @Override
                    public void onNext(final DownloadEvent event) {
                        //下载状态
                        updateProgress(event);
                        downloadController.setEvent(event);
                    }
                });
    }

    /** 启动后台下载 */
    private void startDownLoad(){
        rxdownload.autoInstall(true)              //下载完成自动安装
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
                .compose(rxdownload.transformService(url, saveName, defaultPath))
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Toast.makeText(TestBackgroundDownActivity.this, "下载开始", Toast.LENGTH_SHORT).show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(TestBackgroundDownActivity.this, "下载任务已存在", Toast.LENGTH_SHORT).show();
                    }
                });
        downloadController.setState(new DownloadController.Started());
    }

    /** 暂停下载 */
    private void pause() {
        rxdownload.pauseServiceDownload(url).subscribe();
    }

    /** 取消下载 */
    private void cancel() {
        rxdownload.cancelServiceDownload(url).subscribe();
    }

    private void installApk() {
        Uri uri = Uri.fromFile(rxdownload.getRealFiles(saveName, defaultPath)[0]);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
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
