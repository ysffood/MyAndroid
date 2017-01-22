package com.yk.demo.myandroid.test;

import android.os.Bundle;
import android.widget.ListView;

import com.yk.demo.myandroid.R;
import com.yk.demo.myandroid.adapter.CombinedAdapter;
import com.yk.demo.myandroid.bean.News;
import com.yk.demo.myandroid.bean.News2;
import com.yk.demo.myandroid.ui.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class CombinedListviewActivity extends BaseActivity {

    @BindView(R.id.lv_data)
    ListView lv_data;
    private ArrayList<Object> mData = new ArrayList<>();
    private CombinedAdapter combinedAdapter;

    @Override
    protected void intPresenter() {
        
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_combined_listview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initData();
        combinedAdapter = new CombinedAdapter(this,mData);
        lv_data.setAdapter(combinedAdapter);

    }

    private void initData(){
        for (int i = 0; i <10 ;i++){
            News news = new News();
            news.setId("11");
            news.setTitle("标题"+i+1);
            news.setContent("内容");
            news.setTime("时间");
            news.setAuthor("作者");
            mData.add(news);
        }

        for (int i = 0; i <5 ;i++){
            News2 news = new News2();
            news.setSex("男");
            news.setDistance("1250km");
            mData.add(news);
        }

        for (int i = 0; i <10 ;i++){
            News news = new News();
            news.setId("11");
            news.setTitle("标题"+i+1);
            news.setContent("内容");
            news.setTime("时间");
            news.setAuthor("作者");
            mData.add(news);
        }

        for (int i = 0; i <5 ;i++){
            News2 news = new News2();
            news.setSex("男");
            news.setDistance("1250km");
            mData.add(news);
        }
    }
}
