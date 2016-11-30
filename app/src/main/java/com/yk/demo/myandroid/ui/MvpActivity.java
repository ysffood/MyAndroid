package com.yk.demo.myandroid.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yk.demo.lib.rxjava.core.BasePresenter;

/**
 * 初始化presenter Activity
 * @author yk
 * @version V1.0.0
 * created at 2016/11/25 17:48
 */
public class MvpActivity<P extends BasePresenter> extends AppCompatActivity {

    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}
