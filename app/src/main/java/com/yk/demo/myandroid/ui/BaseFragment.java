package com.yk.demo.myandroid.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yk.demo.lib.rxjava.core.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseFragment
 * @author yk
 * @version V1.0.0
 * created at 2017/1/10 9:13
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment{

    private View view;
    protected P mvpPresenter;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (null != view) {
            //因为共用一个Fragment视图，所以当前这个视图已被加载到Activity中，必须先清除后再加入Activity
            if (view.getParent() != null && view instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) view.getParent();
                parent.removeView(view);
            }
            return view;
        }

        view = inflater.inflate(getLayoutId(),container,false);
        unbinder = ButterKnife.bind(this,view);

        initView(inflater,container,savedInstanceState);
        intPresenter();
        return view;
    }

    /**
     ** 获取layout布局id
     * @return LayoutId
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     * @param inflater 类加载器
     * @param container container
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /** 初始化http请求presenter */
    protected abstract void intPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
        if (unbinder != null){
            unbinder.unbind();
        }

    }
}
