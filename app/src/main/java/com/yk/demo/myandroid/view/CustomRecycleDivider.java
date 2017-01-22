package com.yk.demo.myandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 自定义RecycleView分割线
 * /**
 * DividerItemDecoration is a {@link RecyclerView.ItemDecoration} that can be used as a divider
 * between items of a {@link LinearLayoutManager}. It supports both {@link #HORIZONTAL} and
 * {@link #VERTICAL} orientations.
 *
 * <pre>
 *     mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
 *             mLayoutManager.getOrientation());
 *     recyclerView.addItemDecoration(mDividerItemDecoration);
 * </pre>
 * @author yk
 * @version V1.0.0
 * created at 2017/1/4 10:12
 */
public class CustomRecycleDivider extends RecyclerView.ItemDecoration{

    private static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    private static final int VERTICAL = LinearLayout.VERTICAL;
    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int mOrientation;
    /** 是否是网格分割线 */
//    private boolean isGridDivide = false;

//    private static final int[] ATTRS = new int[]{R.styleable.custom_style_attrs};
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider,android.R.attr.listDivider};
    /** 横分割线 */
    private Drawable mVerticalDivider;
    /** 竖分割线线 */
    private Drawable mHorizontalDivider;

    private final Rect mBounds = new Rect();

    /** 当前布局类型 */
    private LayoutType currentType;

    public enum LayoutType{
        GIRDEVIEW,
        LINNERVIEW
    }

    /**
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
     * {@link LinearLayoutManager}.
     *
     * @param context Current context, it will be used to access resources.
     * @param orientation Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    public CustomRecycleDivider(Context context, int orientation,LayoutType type){
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
//        TypedArray a = context.obtainStyledAttributes(R.styleable.custom_style);
//        String text = a.getString(R.styleable.custom_style_attrs);
//        Logger.d("TAG",text);
        mVerticalDivider = a.getDrawable(0);
        mHorizontalDivider = a.getDrawable(1);
        a.recycle();
        setOrientation(orientation);
        currentType = type;
    }


    public void setGridDivide(boolean type){
//        this.isGridDivide = type;
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
     *
     * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    /**
     * Sets the {@link Drawable} for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    public void setVerticalDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mVerticalDivider = drawable;
    }

    /**
     * Sets the {@link Drawable} for this second divider.
     *
     * @param drawable drawable Drawable that should be used as a divider.
     */
    public void setHorizontalDivider(@NonNull Drawable drawable){
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mHorizontalDivider = drawable;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        if (currentType == LayoutType.GIRDEVIEW){
            drawHorizontalMutil(c, parent);
            drawVerticalMutil(c, parent);
        }else {
            if (mOrientation == VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }
    }

    /** 单垂直分割线 */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
            final int top = bottom - mVerticalDivider.getIntrinsicHeight();
            mVerticalDivider.setBounds(left, top, right, bottom);
            mVerticalDivider.draw(canvas);
        }
        canvas.restore();
    }

    /** 单水平分割线 */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(ViewCompat.getTranslationX(child));
            final int left = right - mHorizontalDivider.getIntrinsicWidth();
            mHorizontalDivider.setBounds(left, top, right, bottom);
            mHorizontalDivider.draw(canvas);
        }
        canvas.restore();
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    /** 网格水平分割线 */
    private void drawVerticalMutil(Canvas canvas, RecyclerView parent) {
        canvas.save();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + mVerticalDivider.getIntrinsicWidth();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mVerticalDivider.getIntrinsicHeight();
            mVerticalDivider.setBounds(left, top, right, bottom);
            mVerticalDivider.draw(canvas);
        }
        canvas.restore();
    }

    /** 网格垂直分割线 */
    private void drawHorizontalMutil(Canvas canvas, RecyclerView parent) {
        canvas.save();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mHorizontalDivider.getIntrinsicWidth();

            mHorizontalDivider.setBounds(left, top, right, bottom);
            mHorizontalDivider.draw(canvas);
        }
        canvas.restore();
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) { // 如果是最后一列，则不需要绘制右边
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0) { // 如果是最后一列，则不需要绘制右边
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else { // StaggeredGridLayoutManager 且横向滚动
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (currentType == LayoutType.GIRDEVIEW){
            super.getItemOffsets(outRect, view, parent, state);
        }else {
            if ( mOrientation == VERTICAL) {
                outRect.set(0, 0, 0, mVerticalDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mHorizontalDivider.getIntrinsicWidth(), 0);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        super.getItemOffsets(outRect, itemPosition, parent);
        if (currentType == LayoutType.GIRDEVIEW){
            int spanCount = getSpanCount(parent);
            int childCount = parent.getAdapter().getItemCount();

            if (isLastRaw(parent, itemPosition, spanCount, childCount)) { // 如果是最后一行，则不需要绘制底部
                outRect.set(0, 0, mHorizontalDivider.getIntrinsicWidth(), 0);
            } else if (isLastColum(parent, itemPosition, spanCount, childCount)) { // 如果是最后一列，则不需要绘制右边
                outRect.set(0, 0, 0, mVerticalDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mHorizontalDivider.getIntrinsicWidth(),
                        mVerticalDivider.getIntrinsicHeight());
            }
        }
    }
}
