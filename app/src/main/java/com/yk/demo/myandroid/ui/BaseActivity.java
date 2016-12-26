package com.yk.demo.myandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.yk.demo.lib.rxjava.core.BasePresenter;

import butterknife.ButterKnife;

/**
 * 初始化presenter Activity
 * @author yk
 * @version V1.0.0
 * created at 2016/11/25 17:48
 */
public abstract class BaseActivity<P extends BasePresenter> extends FragmentActivity {

    protected P mvpPresenter;
    protected FragmentTransaction ft;
    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
        getWindow().getDecorView().findViewById(android.R.id.content)
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                });
        setContentView(getLayoutId());
        // 初始化绑定控件,this对应每个页面的context
        ButterKnife.bind(this);
        intPresenter();
        initView(savedInstanceState);

        ft = getSupportFragmentManager().beginTransaction();
        fragmentManager = getSupportFragmentManager();
    }

    /** 初始化http请求presenter */
    protected abstract void intPresenter();

    /** 获取layout布局id */
    protected abstract int getLayoutId();

    /**
     * Initialize the view in the layout
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}
