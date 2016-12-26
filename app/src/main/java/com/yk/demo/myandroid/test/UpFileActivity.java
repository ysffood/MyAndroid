package com.yk.demo.myandroid.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yk.demo.lib.rxjava.presenter.imp.FileUpPresenter;
import com.yk.demo.lib.rxjava.viewcallback.IFileUpView;
import com.yk.demo.myandroid.R;
import com.yk.demo.myandroid.ui.BaseActivity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class UpFileActivity extends BaseActivity implements View.OnClickListener, IFileUpView{

    @BindView(R.id.bt_up)
    Button bt_up;
    @BindView(R.id.bt_multi_up)
    Button bt_multi_up;
    @BindView(R.id.bt_suffix_up)
    Button bt_suffix_up;

    final String defaultPath = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath();
    final String saveName = "qq.apk";

    @Override
    protected void intPresenter() {
        mvpPresenter = new FileUpPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_up_file;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bt_up.setOnClickListener(this);
        bt_multi_up.setOnClickListener(this);
        bt_suffix_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == bt_up){
            // 单文件上传
            File file = new File(defaultPath+"/"+saveName);
            ((FileUpPresenter)mvpPresenter).sendUpFileRequest(file);
        }else if (view == bt_multi_up){
            // 多文件上传
            File file1 = new File(defaultPath+"/"+saveName);
            File file2 = new File(defaultPath+"/"+"cloud.apk");
            File file3 = new File(defaultPath+"/"+"news.apk");
            Map<String, File> files = new HashMap<>();
            files.put(file1.getName(),file1);
            files.put(file2.getName(),file2);
            files.put(file3.getName(),file3);
            ((FileUpPresenter)mvpPresenter).sendUpMultiFileRequest(files);
        }else if (view == bt_suffix_up){
            // 混合上传
            File file1 = new File(defaultPath+"/"+saveName);
            File file2 = new File(defaultPath+"/"+"cloud.apk");
            File file3 = new File(defaultPath+"/"+"news.apk");
            Map<String, File> files = new HashMap<>();
            files.put(file1.getName(),file1);
            files.put(file2.getName(),file2);
            files.put(file3.getName(),file3);
            ((FileUpPresenter)mvpPresenter).sendUpSuffixFileRequest(files,"我是测试字段1","我是测试字段2");
        }
    }

    @Override
    public void getFileUpSuccessful(Object data) {
        Toast.makeText(this,"单文件上传成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getFileUpFailure(Exception e) {
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getMultiFileUpSuccessful(Object data) {
        Toast.makeText(this,"多文件上传成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getMultiFileUpFailure(Exception e) {
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getSuffixFileUpSuccessful(Object data) {
        Toast.makeText(this,"复合上传成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getSuffixFileUpFailure(Exception e) {
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
    }

}
