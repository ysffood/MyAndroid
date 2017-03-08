package com.yk.demo.myandroid.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yk.demo.myandroid.R;

import java.util.HashMap;

public class TestFirstFragment extends Fragment {

    private View view;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_test_first, container, false);
        textView = (TextView)view.findViewById(R.id.tv_fragment_1);

        SparseArray<String> array = new SparseArray<>();
        array.put(1,"hello");
        array.put(1,"world");

        HashMap<Integer,String> map = new HashMap<Integer,String>();
        map.put(1,"hello");
        map.put(1,"world");
        map.put(1,"test");

        for (int i = 0;i<array.size();i++){
            textView.setText("我是第一个fragment,SparseArray内容是:"+array.get(1)+"HashMap的内容是:"+map.get(1));
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("点击了第一个fragment");
            }
        });
        return view;
    }

    public String getTestText(){
        return textView.getText().toString();
    }
}
