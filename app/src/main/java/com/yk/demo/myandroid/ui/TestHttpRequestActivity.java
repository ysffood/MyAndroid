package com.yk.demo.myandroid.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yk.demo.lib.bean.HttpResponse;
import com.yk.demo.lib.bean.response.UserBean;
import com.yk.demo.lib.rxjava.presenter.imp.HomeViewPresenter;
import com.yk.demo.lib.rxjava.viewcallback.IHomeUserView;
import com.yk.demo.myandroid.R;

public class TestHttpRequestActivity extends BaseActivity implements IHomeUserView {

    @Override
    protected void intPresenter() {
        mvpPresenter = new HomeViewPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Log.d("系统当前时间", System.currentTimeMillis()+"");

        // get 方式获取用户列表
        ((HomeViewPresenter)mvpPresenter).getUserListRequest();

        // get 方式发送更新用户请求
        UserBean userBean = new UserBean();
        userBean.setId(3);
        userBean.setNo(305);
        userBean.setName("张三丰");
        userBean.setSex("女");
        userBean.setDepartment("机动部队");
        ((HomeViewPresenter)mvpPresenter).sendUserInfoUpdate(userBean);

        // post 方式发送更新用户请求
//        UserBean userBean = new UserBean();
        userBean.setId(3);
        userBean.setNo(305);
        userBean.setName("张三丰");
        userBean.setSex("女");
        userBean.setDepartment("机动部队");
        ((HomeViewPresenter)mvpPresenter).sendUserInfoUpdateByPost(userBean);
    }

    @Override
    public void getUserListSuccessfull(Object data) {
        Toast.makeText(this,"获取成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getUserListFailure(Exception e) {
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void updateUserInfoSuccessfull(Object data) {
        Toast.makeText(this,"获取成功"+((HttpResponse<String>)data).getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUserInfFailure(Exception e) {
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUserInfoPostSuccessfull(Object data) {
        Toast.makeText(this,"获取成功"+((HttpResponse<String>)data).getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUserInfoPostFailure(Exception e) {
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
    }
}
