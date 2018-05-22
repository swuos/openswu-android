package com.swuos.mobile.utils;

import android.util.Log;

import com.gallops.mobile.jmvclibrary.app.JApp;

/**
 * Created by youngkaaa on 2018/5/19
 */
public class L {
    private static String sClassName = null;
    private static String sMethodName = null;

    private static void getTrace() {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        String className = caller.getClassName();
        sClassName = className.substring(className.lastIndexOf(".") + 1);
        sMethodName = caller.getMethodName() + "->" + caller.getLineNumber() + ": ";
    }

    public static void v(String text) {
        if (!JApp.isDebug()) {
            return;
        }
        getTrace();
        String message = sClassName + "->" + sMethodName + text;
        Log.i(sClassName, message);
    }



    public static void v(String tag, String text) {
        if (!JApp.isDebug()) {
            return;
        }

        getTrace();
        String message = sClassName + "->" + sMethodName + text;
        Log.v(tag, message);
    }

    /**
     * 调用系统的Log, 将调用该方法的类名作为tag, 并且自动在log信息中添加方法信息和行信息.
     *
     * @param text 需要打印的Log信息
     */
    public static void d(String text) {
        if (!JApp.isDebug()) {
            return;
        }
        getTrace();
        String message = sClassName + "->" + sMethodName + text;
        Log.i(sClassName, message);
    }

    /**
     * 使用自定义Tag打印Log, 并且但是会自动在输出日志前添加方法信息和行信息.
     *
     * @param tag  自定义的Tag
     * @param text 要打印的内容
     */
    public static void d(String tag, String text) {
        if (!JApp.isDebug()) {
            return;
        }
        getTrace();
        String message = sClassName + "->" + sMethodName + text;
        Log.d(tag, message);

    }

    public static void i(String text) {
        if (!JApp.isDebug()) {
            return;
        }
        getTrace();
        String message = sClassName + "->" + sMethodName + text;
        Log.i(sClassName, message);
    }


    public static void w(String text) {
        if (!JApp.isDebug()) {
            return;
        }
        getTrace();
        String message = sClassName + "->" + sMethodName + text;
        Log.w(sClassName, message);

    }


    public static void e(String text) {
        if (!JApp.isDebug()) {
            return;
        }
        getTrace();
        String message = sClassName + "->" + sMethodName + text;
        Log.e(sClassName, message);
    }
}