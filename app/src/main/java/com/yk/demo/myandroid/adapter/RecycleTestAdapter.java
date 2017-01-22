package com.yk.demo.myandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yk.demo.myandroid.R;
import com.yk.demo.myandroid.bean.News;
import com.yk.demo.myandroid.utils.DeviceUtil;

import java.util.ArrayList;

/**
 * 测试RecycleView
 * @author yk
 * @version V1.0.0
 * created at 2016/12/30 14:27
 */
public class RecycleTestAdapter extends RecyclerView.Adapter<RecycleTestAdapter.RecycleViewHolder> {

    private ArrayList<News> mData;
    private LayoutInflater inflater;
    private Context context;

    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public RecycleTestAdapter(Context context, ArrayList<News> news){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = news;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_clist_head,parent,false);
        RecycleViewHolder viewHolder = new RecycleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecycleViewHolder holder, int position) {
        News news = mData.get(position);
//        holder.tv_id.setText(news.getId());
//        holder.tv_content.setText(news.getContent());
//        holder.tv_date.setText(news.getTime());
        Uri uri = Uri.parse("res://"+context.getPackageName()+"/"+ R.mipmap.ic_launcher);
        String url = news.getPicUrl();
        holder.img_icon.setImageURI(url);

        if (itemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }
            });
        }
        if (itemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemLongClickListener.onItemLongClick(holder.itemView,holder.getLayoutPosition());
                    return true;
                }
            });
        }

        // 计算设置不同大小的图片
        int width = 0;
        if (context instanceof Activity){
            width = DeviceUtil.getScreenWidth((Activity) context) / 4;
        }
        switch (position % 4){
            case 0:
                holder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(width,200));
                holder.img_icon.setLayoutParams(new LinearLayout.LayoutParams(width,200));
                break;
            case 1:
                holder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(width,120));
                holder.img_icon.setLayoutParams(new LinearLayout.LayoutParams(width,120));
                break;
            case 2:
                holder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(width,330));
                holder.img_icon.setLayoutParams(new LinearLayout.LayoutParams(width,330));
                break;
            case 3:
                holder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(width,230));
                holder.img_icon.setLayoutParams(new LinearLayout.LayoutParams(width,230));
                break;
            default:
                holder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(width,300));
                holder.img_icon.setLayoutParams(new LinearLayout.LayoutParams(width,300));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class RecycleViewHolder extends RecyclerView.ViewHolder{

        TextView tv_id;
        SimpleDraweeView img_icon;
        TextView tv_content;
        TextView tv_date;

        private RecycleViewHolder(View view){
            super(view);
            tv_id = (TextView)view.findViewById(R.id.tv_id);
            img_icon = (SimpleDraweeView)view.findViewById(R.id.img_icon);
            tv_content = (TextView)view.findViewById(R.id.tv_content);
            tv_date = (TextView)view.findViewById(R.id.tv_date);
        }
    }

    /** 添加Item点击事件 */
    public void setOnItemClickLister(OnItemClickListener lister){
        this.itemClickListener = lister;
    }

    /** 添加Item长按事件 */
    public void setOnLongItemClickListener(OnItemLongClickListener listener){
        this.itemLongClickListener = listener;
    }

    /** item点击事件 */
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    /** item长按事件 */
    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }
}
