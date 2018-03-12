package com.swuos.mobile.utils.kt

import android.view.View
import android.view.ViewGroup

/**
 * kotlin扩展类
 * Created by wangyu on 2018/1/22.
 */
//获取childView列表
fun ViewGroup.getChildViews(): List<View> = (0 until childCount).map { getChildAt(it) }

//获取第一个view，可为空
fun ViewGroup.getFirstView(): View? {
    if (childCount == 0) return null
    return getChildAt(0)
}
//获取最后一个view，可为空
fun ViewGroup.getLastView(): View? {
    if (childCount == 0) return null
    return getChildAt(childCount - 1)
}

