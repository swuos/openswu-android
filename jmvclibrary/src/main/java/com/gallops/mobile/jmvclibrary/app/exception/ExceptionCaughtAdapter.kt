package com.gallops.mobile.jmvclibrary.app.exception

/**
 * 异常捕获适配器
 * Created by wangyu on 2017/7/25.
 */
class ExceptionCaughtAdapter(private val handler: Thread.UncaughtExceptionHandler) : Thread.UncaughtExceptionHandler{

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        ExceptionActivity.showException(e!!)
        handler.uncaughtException(t, e)
    }
}