package com.app.davidwang.flowlayout.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by DavidWang on 2017/8/3.
 */

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int measureWidth = 0;
        int measureHeight = 0;
        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int childCount = getChildCount();

        if (MeasureSpec.AT_MOST == heightSpecMode || MeasureSpec.AT_MOST == widthSpecMode) {
            int currentWidth = 0;
            int maxChildHigh = 0;

            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                FlowLayout.LayoutParams lp = (LayoutParams) childView.getLayoutParams();
                int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

                if (widthSpaceSize >= currentWidth + childWidth) {
                    currentWidth += childWidth;

                    if (maxChildHigh < childHeight) {
                        measureHeight -= maxChildHigh;
                        maxChildHigh = childHeight;
                        measureHeight += maxChildHigh;
                    }
                } else if (widthSpaceSize < childWidth) {
                    maxChildHigh = childHeight;
                    currentWidth = childWidth;
                    measureHeight += maxChildHigh;
                } else {
                    currentWidth = childWidth;
                    maxChildHigh = childHeight;
                    measureHeight += maxChildHigh;
                }

                if (currentWidth > measureWidth) {
                    measureWidth = currentWidth;
                }
            }

            setMeasuredDimension(MeasureSpec.AT_MOST == widthSpecMode ? measureWidth : widthSpaceSize
                    , MeasureSpec.AT_MOST == heightSpecMode ? measureHeight : heightSpaceSize);
        } else {
            setMeasuredDimension(widthSpaceSize, heightSpaceSize);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        if (0 < childCount) {
            int currentWidth = 0;
            int currentHigh = 0;
            int maxChildHigh = 0;

            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                FlowLayout.LayoutParams lp = (LayoutParams) childView.getLayoutParams();
                int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

                if (getWidth() >= currentWidth + childWidth) {
                    childView.layout(currentWidth + lp.leftMargin, currentHigh + lp.topMargin
                            , currentWidth + childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin
                            , currentHigh + childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);

                    currentWidth += childWidth;

                    if (maxChildHigh < childHeight) {
                        maxChildHigh = childHeight;
                    }
                } else if (getWidth() < childWidth) {
                    currentWidth = 0;
                    currentHigh += maxChildHigh;

                    childView.layout(currentWidth + lp.leftMargin, currentHigh + lp.topMargin
                            , currentWidth + childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin
                            , currentHigh + childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);

                    currentWidth += childWidth;
                    maxChildHigh = childHeight;
                } else {
                    currentWidth = 0;
                    currentHigh += maxChildHigh;

                    childView.layout(currentWidth + lp.leftMargin, currentHigh + lp.topMargin
                            , currentWidth + childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin
                            , currentHigh + childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);

                    currentWidth += childWidth;
                    maxChildHigh = childHeight;
                }
            }
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new FlowLayout.LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
    }
}
