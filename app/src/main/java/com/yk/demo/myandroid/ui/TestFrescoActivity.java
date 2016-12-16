package com.yk.demo.myandroid.ui;

import android.net.Uri;
import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yk.demo.myandroid.R;

import butterknife.BindView;

public class TestFrescoActivity extends BaseActivity {

    @BindView(R.id.my_image_view)
    SimpleDraweeView my_image_view;

    @Override
    protected void intPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_fresco;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Uri uri = Uri.parse("http://192.168.191.1:8080/WebProjectLC/img/pay.jpg");
        my_image_view.setImageURI(uri);
    }
}
