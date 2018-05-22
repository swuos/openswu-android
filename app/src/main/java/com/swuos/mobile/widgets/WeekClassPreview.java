package com.swuos.mobile.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class WeekClassPreview extends View {
    int width;
    int height;
    float dotRadius;
    int[][] sources = new int[4][5];
    int[] color = {0xffD3DFF9, 0xffacc5fb, 0xff7FABFB};

    public WeekClassPreview(Context context) {
        super(context);
        init();

    }

    public WeekClassPreview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public WeekClassPreview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public WeekClassPreview(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init() {
        paint = new Paint();
        for (int i = 0; i < sources.length; i++) {
            for (int j = 0; j < sources[i].length; j++) {
                sources[i][j] = 1;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        dotRadius = width / 5 / 2;
        height = (int) (4 * dotRadius * 2);

        setMeasuredDimension(width, height);
    }

    private Paint paint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < sources.length; i++) {
            for (int j = 0; j < sources[i].length; j++) {
                paint.setColor(color[sources[i][j]]);
                canvas.drawCircle(j * dotRadius * 2 + dotRadius, i * dotRadius * 2 + dotRadius, dotRadius - 5, paint);
            }
        }
    }

    public void setPreview(int[][] colors) {
        this.sources = colors;
        invalidate();
    }

}
