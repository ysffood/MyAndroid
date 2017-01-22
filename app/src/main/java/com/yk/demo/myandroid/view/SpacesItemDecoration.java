package com.yk.demo.myandroid.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecycleView瀑布流间隔
 * @author yk
 * @version 
 * created at 2017/1/9 10:07
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpacesItemDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.right=space;
        outRect.bottom=space;
        if (!(parent.getChildAdapterPosition(view) < 4)){
            outRect.top=space;
        }
    }
}
