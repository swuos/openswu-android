package com.gallops.mobile.jmvclibrary.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.gallops.mobile.jmvclibrary.app.JApp;

/**
 * toast工具类
 * Created by wangyu on 2018/4/4.
 */

public class ToastUtil {

    private static Toast mLastToast;
    public static void showToast(@StringRes int msgId) {
        showToast(JApp.getInstance().getString(msgId));
    }

    public static void showToast(String msg) {
        cancelToast();
        mLastToast = Toast.makeText(JApp.getInstance(), msg, Toast.LENGTH_SHORT);
        mLastToast.show();
    }

    private static void cancelToast() {
        if (mLastToast != null) {
            mLastToast.cancel();
        }
    }
}
