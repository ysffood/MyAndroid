package com.yk.demo.myandroid.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.yk.demo.myandroid.R;
import com.yk.demo.myandroid.adapter.RecycleTestAdapter;
import com.yk.demo.myandroid.bean.News;
import com.yk.demo.myandroid.logger.Logger;
import com.yk.demo.myandroid.view.CustomRecycleDivider;

import java.util.ArrayList;

public class RecycleActivity extends AppCompatActivity implements RecycleTestAdapter.OnItemClickListener, RecycleTestAdapter.OnItemLongClickListener{

    private RecyclerView recycle_view;
    private ArrayList<News> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        recycle_view = (RecyclerView)findViewById(R.id.rc_view);

        initData();
        // 网格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,OrientationHelper.HORIZONTAL,false);
        // 瀑布流布局
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        // 线性布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,OrientationHelper.VERTICAL,false);

        recycle_view.setLayoutManager(gridLayoutManager);
        recycle_view.setItemAnimator(new DefaultItemAnimator());

        // 添加自定义分割线
        CustomRecycleDivider recycleDivider = new CustomRecycleDivider(this,OrientationHelper.HORIZONTAL, CustomRecycleDivider.LayoutType.GIRDEVIEW);
        recycleDivider.setVerticalDrawable(getDrawable(R.drawable.shape_divider_bg_1));
        recycleDivider.setHorizontalDivider(getDrawable(R.drawable.shape_divider_bg_2));
        recycle_view.addItemDecoration(recycleDivider);
//        recycle_view.addItemDecoration(new DividerItemDecoration(this,OrientationHelper.HORIZONTAL));
        // 瀑布流分割间隔
//        recycle_view.addItemDecoration(new SpacesItemDecoration(16));

        RecycleTestAdapter adapter = new RecycleTestAdapter(this,mData);
        recycle_view.setAdapter(adapter);
        adapter.setOnItemClickLister(this);
        adapter.setOnLongItemClickListener(this);

    }

    private void initData(){
        mData.clear();
        for (int i = 0; i <11 ;i++){
            News news = new News();
            news.setId("11");
            news.setTitle("标题"+i+1);
            news.setContent("内容");
            news.setTime("时间");
            news.setAuthor("作者");
            mData.add(news);
        }
        mData.get(0).setPicUrl("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=17435921,1676944870&fm=116&gp=0.jpg");
        mData.get(1).setPicUrl("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3144465310,4114570573&fm=116&gp=0.jpg");
        mData.get(2).setPicUrl("https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=4da4ff1895ef76c6cfd2fc2bad16fdf6/f9dcd100baa1cd11daf25f19bc12c8fcc3ce2d46.jpg");
        mData.get(3).setPicUrl("https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=5f6ab3f5d00735fa8ef049b9ae500f9f/29381f30e924b8995d7368d66a061d950b7bf695.jpg");
        mData.get(4).setPicUrl("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2069176211,1713824082&fm=11&gp=0.jpg");
        mData.get(5).setPicUrl("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=827020255,4071626112&fm=21&gp=0.jpg");
        for (int i = 6 ; i< mData.size();i++){
            mData.get(i).setPicUrl("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2480561195,968299301&fm=21&gp=0.jpg");
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Logger.d("RecycleView------onItemClick",position+"");
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Logger.d("RecycleView------onItemLongClick",position+"");
    }
}
