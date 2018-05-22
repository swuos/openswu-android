package com.swuos.mobile.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by youngkaaa on 2018/5/18
 */
public class DisplayUtils {
    private static Context context;
    private static float density;
    private static float scaledDensity;

    public static void init(Context c){
        context=c;
    }

    /**
     * 获取屏幕的宽度
     *
     * @return 屏幕的宽度
     */
    public static int getScreenWidth() {
        if (context == null) {
            return 0;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        density = dm.density;
        scaledDensity = dm.scaledDensity;
        return dm.widthPixels;
    }



    /**
     * 获取屏幕的高度
     *
     * @return 屏幕的高度
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        density = dm.density;
        scaledDensity = dm.scaledDensity;
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }

    /**
     * 将dip转换为px
     *
     * @param dip
     *
     * @return dip转换为px
     */
    public static float dip2px(float dip) {
        if (density <= 0) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            density = dm.density;
            scaledDensity = dm.scaledDensity;
        }
        return dip * density + 0.5f * (dip >= 0 ? 1 : -1);

    }

    /**
     * 将px转换为dip
     *
     * @param px
     *
     * @return px转换为dip
     */
    public static int px2dip(int px) {
        if (density <= 0) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            density = dm.density;
            scaledDensity = dm.scaledDensity;
        }
        return (int) (px / density + 0.5f * (px >= 0 ? 1 : -1));
    }

    /**
     * 显示输入法
     *
     * @param view 推荐将EditText传入
     */
    public static void showSoftInput(View view) {
        if (view instanceof EditText) {
            view.requestFocus();
            ((EditText) view).setCursorVisible(true);
        }

        try {
            InputMethodManager imm =
                    (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(view.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏输入法
     *
     * @param view 推荐将EditText传入
     */
    public static void hideSoftInput(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setCursorVisible(false);
        }
        try {
            InputMethodManager imm =
                    (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 延时显示输入法
     *
     * @param view    推荐将EditText传入
     * @param delayMs 延迟时间(毫秒)
     */
    public static void showSoftInputDelay(final View view, final long delayMs) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                showSoftInput(view);
            }
        }, delayMs);

    }



    /**
     * 延时隐藏输入法
     *
     * @param view    推荐将EditText传入
     * @param delayMs 延迟时间(毫秒)
     */
    public static void hideSoftInputDelay(final View view, final long delayMs) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideSoftInput(view);
            }
        }, delayMs);

    }
}
