package com.yk.demo.myandroid.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.anye.greendao.gen.UserDao;
import com.google.gson.Gson;
import com.yk.demo.myandroid.DefaultApplication;
import com.yk.demo.myandroid.R;
import com.yk.demo.myandroid.adapter.DbAdapter;
import com.yk.demo.myandroid.db.entity.User;
import com.yk.demo.myandroid.logger.Logger;
import com.yk.demo.myandroid.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TestDaoActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.bt_add)
    Button bt_add;
    @BindView(R.id.bt_delete)
    Button bt_delete;
    @BindView(R.id.bt_update)
    Button bt_update;
    @BindView(R.id.bt_select)
    Button bt_select;
    @BindView(R.id.lv_data)
    ListView lv_data;

    private UserDao mUserDao;
    private User mUser;
    private long times;

    private ArrayList<User> mdata = new ArrayList<>();
    private DbAdapter adpter;

    @Override
    protected void intPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_dao;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mUserDao = DefaultApplication.getInstances().getDaoSession().getUserDao();

        times = System.currentTimeMillis();
        bt_add.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_update.setOnClickListener(this);
        bt_select.setOnClickListener(this);

        adpter = new DbAdapter(this,mdata);
        lv_data.setAdapter(adpter);
    }

    private void add(){
        mUser = new User(null,times,"anye3");
//        mUserDao.insertOrReplace(mUser);//添加一个
        mUserDao.insertWithoutSettingPk(mUser);
    }

    private void delete(long id){
//        mUserDao.deleteByKey(id);
        mUserDao.deleteAll();
    }

    private void update(){
        mUser = new User((long)2,times,"anye0803");
        mUserDao.update(mUser);
    }

    private void select(){
//        List<User> users = mUserDao.loadAll();

        List<User> users =  mUserDao.queryBuilder().build().list();

        String userName = "";
        for (int i = 0; i < users.size(); i++) {
            userName += users.get(i).getName()+",";
        }
        mdata.clear();
        mdata.addAll(users);
        adpter.notifyDataSetChanged();

        Logger.i("users",users);
        Logger.json( new Gson().toJson(users));

    }

    @Override
    public void onClick(View view) {
        if (view == bt_add){
            add();
        }else if (view == bt_delete){
            delete((long)2);
        }else if (view == bt_update){
            update();
        }else if (view == bt_select){
            select();
        }
    }
}
