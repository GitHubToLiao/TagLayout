package com.example.as.taglayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by as on 2017/6/25.
 */

public class TagLayout extends ViewGroup {
    //用来存放所有的子view
    private List<List<View>> mChildViews = new ArrayList<>();
    private TagAdapter mAdapter;

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //清除一次数据  原因是onMeasure可能走多次
        mChildViews.clear();
        //获取子View的个数
        int childCount = getChildCount();
        //每一行的高度  默认为他的paddingLeft  和 paddingRight
        int lineWidth = getPaddingLeft() + getPaddingRight();
        //获取总的宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //获取默认高度
        int height = getPaddingTop() + getPaddingBottom();
        //每一行最大高度
        int maxHeight = 0;
        //用来存放每一行的子View
        ArrayList<View> childViews = new ArrayList<>();
        mChildViews.add(childViews);
        for (int i = 0; i < childCount; i++) {
            //获取ViewGroup中的子View
            View childView = getChildAt(i);
            //如果子View不可见  就跳出本次循环
            if(childView.getVisibility() ==GONE){
                continue;
            }
            //对子孩子进行测量
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            //获取layoutParams  目的为得到Margin
            MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();

            //判断是否换行
            if ((lineWidth + childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin) > width) {
                //换行
                height += childView.getMeasuredHeight() + layoutParams.bottomMargin + layoutParams.topMargin;
                lineWidth = getPaddingLeft() + getPaddingRight();
                childViews = new ArrayList<>();
                mChildViews.add(childViews);
            } else {
                //不换行
                lineWidth += childView.getMeasuredWidth() + layoutParams.rightMargin + layoutParams.leftMargin;
                maxHeight = Math.max(childView.getMeasuredHeight() + layoutParams.bottomMargin + layoutParams.topMargin, maxHeight);

            }
            childViews.add(childView);

        }
        height += maxHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, top = getPaddingTop(), right, bottom;
        for (List<View> mChildView : mChildViews) {
            left = getPaddingLeft();
            for (View view : mChildView) {
                //如果子View不可见  就跳出本次循环
                if(view.getVisibility() ==GONE){
                    continue;
                }

                MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
                left += layoutParams.leftMargin;
                int childTop = top + layoutParams.bottomMargin;
                right = left + view.getMeasuredWidth();
                bottom = childTop + view.getMeasuredHeight();
                //计算出位置然后计算
                view.layout(left, childTop, right, bottom);
                left += view.getMeasuredWidth();
            }
            MarginLayoutParams layoutParams = (MarginLayoutParams) mChildView.get(0).getLayoutParams();
            top += mChildView.get(0).getMeasuredHeight() + layoutParams.bottomMargin + layoutParams.topMargin;
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    public void setAdapter(TagAdapter adapter) {
        if (adapter == null) {
            return;
        }

        removeAllViews();
        mAdapter = null;
        mAdapter = adapter;

        int childCount = mAdapter.getCount();
        for (int i = 0; i < childCount; i++) {
            View childView = mAdapter.getView(i, this, this);
            addView(childView);
        }

    }
}
