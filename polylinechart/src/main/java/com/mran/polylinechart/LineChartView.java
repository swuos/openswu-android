package com.mran.polylinechart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/9/16.
 */


public class LineChartView extends View {

    int offsetx;
    private List<ChargeBean> data = new ArrayList<>();
    private int xLineColor;
    private int xLineHeight;
    private int xTextSize;
    private int xTextColor;
    private int xTextPaddingtoXline;
    private int xPointColor;
    private int xPointRadius;
    private int polylineWidth;
    private int polylineColor;
    private int polylinePointColor;
    private int polylinePointRadius;
    private int offtset;
    private Rect mBound = new Rect();
    private Paint mpaint = new Paint();
    private int XPOINTSIZE = 30;

    private int[] xPointSet = new int[XPOINTSIZE];
    private int avl;
    private int width;
    private int height;
    private int lastx;
    private boolean first = true;
    private int DEFULTPADDINGBOTTOM = 20;
    private int DEFULTPADDINGRIGHT = 20;
    private int DEFULTRATE=80;
    private int hightRate=DEFULTRATE;
    private int paddingbottom = DEFULTPADDINGBOTTOM;
    private int paddingRight = DEFULTPADDINGRIGHT;

    public LineChartView(Context context) {
        super(context);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {


        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineChartView, 0, 0);
        int n = array.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.LineChartView_xLineColor) {
                xLineColor = array.getColor(attr, ContextCompat.getColor(context, R.color.colorPrimary));

            } else if (attr == R.styleable.LineChartView_xLineHeight) {
                xLineHeight = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.LineChartView_xTextSize) {
                xTextSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.LineChartView_xTextColor) {
                xTextColor = array.getColor(attr, ContextCompat.getColor(context, R.color.colorPrimary));

            } else if (attr == R.styleable.LineChartView_xTextPaddingtoXline) {
                xTextPaddingtoXline = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.LineChartView_xPointColor) {
                xPointColor = array.getColor(attr, ContextCompat.getColor(context, R.color.colorAccent));

            } else if (attr == R.styleable.LineChartView_xPointRadius) {
                xPointRadius = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.LineChartView_polylineColor) {
                polylineColor = array.getColor(attr, ContextCompat.getColor(context, R.color.colorAccent));

            } else if (attr == R.styleable.LineChartView_polylineWidth) {
                polylineWidth = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.LineChartView_polylinePointColor) {
                polylinePointColor = array.getColor(attr, ContextCompat.getColor(context, R.color.colorAccent));

            } else if (attr == R.styleable.LineChartView_polylinePointRadius) {
                polylinePointRadius = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));


            } else {
            }
        }
        array.recycle();

    }

    public void addData(List<ChargeBean> data) {
        this.data.clear();
        this.data.addAll(data);
        first = true;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        width = widthSize;
        height = heightSize;
        paddingbottom = getPaddingBottom();
        paddingRight = getPaddingRight();
        avl = (getMeasuredWidth() - paddingRight) / (6 - 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data.size() != 0) {
            mpaint.setAntiAlias(true);
            mpaint.setDither(true);
            mBound = new Rect();

            if (first) {
                xPointSet[0] = width - paddingRight;
                for (int i = 1; i < XPOINTSIZE; i++) {
                    xPointSet[i] = width - paddingRight - avl * i;
                }
                first = false;

            }
            //画x轴
            mpaint.setColor(xLineColor);
            mpaint.setStrokeWidth(xLineHeight);
            canvas.drawLine(xPointSet[0], height  - paddingbottom, xPointSet[29], height - paddingbottom, mpaint);
            for (int i = 0; i < XPOINTSIZE; i++) {

                if (i != 0) {

                    //画折线
                    mpaint.setStrokeWidth(polylineWidth);
                    mpaint.setColor(polylineColor);
                    canvas.drawLine(xPointSet[i] + polylinePointRadius, height  - paddingbottom - data.get(i).getCoast() * hightRate, xPointSet[i - 1] - polylinePointRadius, height  - paddingbottom - data.get(i - 1).getCoast() * hightRate, mpaint);

                }
                //画x轴节点
                mpaint.setColor(xPointColor);
                canvas.drawCircle(xPointSet[i], height  - paddingbottom, xPointRadius, mpaint);
                //画x轴文字
                mpaint.setTextSize(xTextSize);
                mpaint.getTextBounds(data.get(i).getDate(), 0, data.get(i).getDate().length(), mBound);
                canvas.drawText(data.get(i).getDate(), xPointSet[i] - mBound.width() / 2, height - paddingbottom + xTextPaddingtoXline, mpaint);
                //画日消费节点
                mpaint.setColor(polylinePointColor);
                canvas.drawCircle(xPointSet[i], height - paddingbottom - data.get(i).getCoast() * hightRate, polylinePointRadius, mpaint);
                //画折线文字
                mpaint.setTextSize(xTextSize);
                mpaint.getTextBounds(String.valueOf(data.get(i).getCoast()), 0, String.valueOf(data.get(i).getCoast()).length(), mBound);
                canvas.drawText(String.valueOf(data.get(i).getCoast()), xPointSet[i] - mBound.width() / 2, height - paddingbottom - data.get(i).getCoast() * hightRate - 30, mpaint);
            }
            mpaint.reset();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                offsetx = x - lastx;
                //                for (int i = 0; i < XPOINTSIZE; i++) {
                //                    xPointSet[i] += offsetx;
                //                }
                //                if (xPointSet[0] > avl) {
                //                    int tmp = xPointSet[0];
                //                    for (int i = 1; i < 6; i++) {
                //                        int k = xPointSet[i];
                //                        xPointSet[i] = tmp;
                //                        tmp = k;
                //                    }
                //                    xPointSet[0] = 0;
                //
                //                }
                //                if (width - xPointSet[5] > avl) {
                //                    int tmp = xPointSet[5];
                //                    for (int i = 4; i >= 0; i--) {
                //                        int k = xPointSet[i];
                //                        xPointSet[i] = tmp;
                //                        tmp = k;
                //                    }
                //                    xPointSet[5] = width;
                //                }
                //                Log.d("LineChartView", "offsetx:" + offsetx);
                Log.d("LineChartView", "getScrollX():" + getScrollX());
                //                Log.d("LineChartView", "getLeft():" + getLeft());
                //                Log.d("LineChartView", "getRight():" + getRight());
                //                Log.d("LineChartView", "getX():" + getX());
                if ((getScrollX() >= paddingRight && offsetx <= 0) || (getScrollX() <= -(XPOINTSIZE * avl - width - 100) && offsetx >= 0)) {
                    break;
                }
                scrollBy(-offsetx, 0);
                lastx = x;
                //                Log.d("LinechartView", String.format("offsetx %d %d %d %d %d %d %d", offsetx, xPointSet[0], xPointSet[1], xPointSet[2], xPointSet[3], xPointSet[4], xPointSet[5]));
                break;
            case MotionEvent.ACTION_DOWN:
                //                Log.d("LineChartView", "down");
                lastx = x;
                break;
            default:
                break;
        }
        return true;

    }

    private void drawPolylinePoint(Canvas canvas, float cx, float cy, float radius) {
        canvas.drawCircle(cx, cy, radius, mpaint);
    }

    public int getPolylinePointRadius() {
        return polylinePointRadius;
    }

    public void setPolylinePointRadius(int polylinePointRadius) {
        this.polylinePointRadius = polylinePointRadius;
    }

    public int getPolylinePointColor() {
        return polylinePointColor;
    }

    public void setPolylinePointColor(int polylinePointColor) {
        this.polylinePointColor = polylinePointColor;
    }

    public int getPolylineColor() {
        return polylineColor;
    }

    public void setPolylineColor(int polylineColor) {
        this.polylineColor = polylineColor;
    }

    public int getPolylineWidth() {
        return polylineWidth;
    }

    public void setPolylineWidth(int polylineWidth) {
        this.polylineWidth = polylineWidth;
    }

    public int getxPointRadius() {
        return xPointRadius;
    }

    public void setxPointRadius(int xPointRadius) {
        this.xPointRadius = xPointRadius;
    }

    public int getxPointColor() {
        return xPointColor;
    }

    public void setxPointColor(int xPointColor) {
        this.xPointColor = xPointColor;
    }

    public int getxTextColor() {
        return xTextColor;
    }

    public void setxTextColor(int xTextColor) {
        this.xTextColor = xTextColor;
    }

    public int getxTextSize() {
        return xTextSize;
    }

    public void setxTextSize(int xTextSize) {
        this.xTextSize = xTextSize;
    }

    public int getxLineHeight() {
        return xLineHeight;
    }

    public void setxLineHeight(int xLineHeight) {
        this.xLineHeight = xLineHeight;
    }

    public int getxLineColor() {
        return xLineColor;
    }

    public void setxLineColor(int xLineColor) {
        this.xLineColor = xLineColor;
    }


}
