package com.yk.demo.myandroid.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yk.demo.myandroid.R;
import com.yk.demo.myandroid.bean.News;
import com.yk.demo.myandroid.bean.News2;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 测试复合listview
 * @author yk
 * @version V1.0.0
 * created at 2016/12/27 9:39
 */
public class CombinedAdapter extends BaseAdapter{

    private ArrayList<Object> mData;
    private LayoutInflater inflater;
    private Context context;

    /** 不同数据类型数量 */
    private final static int TYPE_COUNT = 2;
    /** 类型1的type */
    private final static int FLAG_NEWA = 0;
    /** 类型2的type */
    private final static int FLAG_NEWB = 1;

    public CombinedAdapter(Context context, ArrayList<Object> mData){
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (mData != null)
            return mData.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof News){
            return FLAG_NEWA;
        }else if (mData.get(position) instanceof News2){
            return FLAG_NEWB;
        }else{
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderA holderA = null;
        ViewHolderB holderB = null;
        int type = getItemViewType(i);
        if (view == null){
            switch (type){
                case FLAG_NEWA:
                    view = inflater.inflate(R.layout.item_clist_head,viewGroup,false);
                    holderA = new ViewHolderA(view);
                    view.setTag(holderA);
                    break;
                case FLAG_NEWB:
                    view = inflater.inflate(R.layout.itme_clist_bottom,viewGroup,false);
                    holderB = new ViewHolderB(view);
                    view.setTag(holderB);
                    break;
            }

        }else{
            switch (type){
                case FLAG_NEWA:
                    holderA = (ViewHolderA) view.getTag();
                    break;
                case FLAG_NEWB:
                    holderB = (ViewHolderB) view.getTag();
                    break;
            }
        }
        switch (type){
            case FLAG_NEWA:
                News news = (News) mData.get(i);
                holderA.id.setText(news.getId());
                holderA.title.setText(news.getTitle());
                holderA.content.setText(news.getContent());
                holderA.date.setText(news.getTime());
                Uri uri = Uri.parse("res://"+context.getPackageName()+"/"+ R.mipmap.ic_launcher);
                holderA.icon.setImageURI(uri);
                break;
            case FLAG_NEWB:
                News2 news2 = (News2) mData.get(i);
                holderB.sex.setText(news2.getSex());
                holderB.distance.setText(news2.getDistance());
                break;
        }
        return view;
    }

    static class ViewHolderA{
        @BindView(R.id.tv_id)
        TextView id;
        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_content)
        TextView content;
        @BindView(R.id.tv_date)
        TextView date;
        @BindView(R.id.img_icon)
        SimpleDraweeView icon;

        private ViewHolderA(View view){
            ButterKnife.bind(this,view);
        }
    }

    static class ViewHolderB{
        @BindView(R.id.tv_sex)
        TextView sex;
        @BindView(R.id.tv_distance)
        TextView distance;

        private ViewHolderB(View view){
            ButterKnife.bind(this,view);
        }
    }
}
