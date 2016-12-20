//package com.yk.demo.myandroid.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.yk.demo.myandroid.R;
//import com.yk.demo.myandroid.db.entity.User;
//
//import java.util.ArrayList;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * greendao数据库测试数据展示
// * @author yk
// * @version V1.0.0
// * created at 2016/12/16 10:51
// */
//public class DbAdapter extends BaseAdapter {
//
//    private ArrayList<User> data;
//    private LayoutInflater inflater;
//
//    public DbAdapter(Context context, ArrayList<User> data){
//        this.data = data;
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public int getCount() {
//        if (data == null)
//            return 0;
//        return data.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return data.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHolder viewHolder = null;
//        if (view == null){
//            view = inflater.inflate(R.layout.item_db_data,viewGroup,false);
//            viewHolder = new ViewHolder(view);
//            view.setTag(viewHolder);
//        }else{
//            viewHolder = (ViewHolder) view.getTag();
//        }
//        User user = data.get(i);
//        viewHolder.tv_id.setText(String.valueOf(user.getId()));
//        viewHolder.tv_name.setText(user.getName());
//        viewHolder.tv_time.setText(String.valueOf(user.getTime()));
//        viewHolder.tv_sex.setText("");
//        return view;
//    }
//
//    class ViewHolder{
//        @BindView(R.id.tv_id)
//        TextView tv_id;
//        @BindView(R.id.tv_name)
//        TextView tv_name;
//        @BindView(R.id.tv_time)
//        TextView tv_time;
//        @BindView(R.id.tv_sex)
//        TextView tv_sex;
//
//        public ViewHolder(View view){
//            ButterKnife.bind(this,view);
//        }
//    }
//}
