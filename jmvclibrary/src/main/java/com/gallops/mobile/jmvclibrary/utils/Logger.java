package com.gallops.mobile.jmvclibrary.utils;

import com.gallops.mobile.jmvclibrary.utils.kt.LoggerKt;

/**
 * 日志打印
 * Created by wangyu on 2018/3/27.
 */

public class Logger {
    public static void i(String tag, String msg) {
        LoggerKt.lgI(tag, msg);
    }
    public static void e(String tag, String msg) {
        LoggerKt.lgE(tag, msg);
    }
    public static void d(String tag, String msg) {
        LoggerKt.lgD(tag, msg);
    }
    public static void w(String tag, String msg) {
        LoggerKt.lgW(tag, msg);
    }
}
