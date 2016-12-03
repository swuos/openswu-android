package com.swuos.ALLFragment.swujw.grade.adapter;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.swuos.swuassistant.R;


public class RecycleviewItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private double padleftRate;

    public RecycleviewItemDecoration(Context context, double padleftRate) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.decoration);
        this.padleftRate = padleftRate;
    }


    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = (int) (parent.getPaddingLeft() + parent.getWidth() * padleftRate);
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}