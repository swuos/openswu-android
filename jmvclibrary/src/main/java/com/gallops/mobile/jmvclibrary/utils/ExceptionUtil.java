package com.gallops.mobile.jmvclibrary.utils;

import com.gallops.mobile.jmvclibrary.app.JApp;

/**
 * 异常抛出
 * Created by wangyu on 2018/3/30.
 */

public class ExceptionUtil {

    public static void throwException(Exception exception) {
        if (JApp.isDebug()) {
            throw new RuntimeException(exception);
        }
    }
}
