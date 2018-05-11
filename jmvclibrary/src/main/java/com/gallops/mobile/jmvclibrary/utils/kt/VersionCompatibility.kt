package com.gallops.mobile.jmvclibrary.utils.kt

import android.os.Build

/**
 * 版本兼容执行环境
 * Created by wangyu on 2017/11/23.
 */

inline fun onTargetOrAbove(target: Int, up: () -> Unit, down: () -> Unit) {
    if (Build.VERSION.SDK_INT >= target) {
        up()
    } else {
        down()
    }
}

inline fun on14orAbove(up: () -> Unit, down: () -> Unit)
    = onTargetOrAbove(Build.VERSION_CODES.ICE_CREAM_SANDWICH, up, down)

inline fun on16orAbove(up: () -> Unit, down: () -> Unit)
    = onTargetOrAbove(Build.VERSION_CODES.JELLY_BEAN, up, down)

inline fun on17orAbove(up: () -> Unit, down: () -> Unit)
    = onTargetOrAbove(Build.VERSION_CODES.JELLY_BEAN_MR1, up, down)

inline fun on19orAbove(up: () -> Unit, down: () -> Unit)
    = onTargetOrAbove(Build.VERSION_CODES.KITKAT, up, down)

inline fun on21orAbove(up: () -> Unit, down: () -> Unit)
    = onTargetOrAbove(Build.VERSION_CODES.LOLLIPOP, up, down)

inline fun on23orAbove(up: () -> Unit, down: () -> Unit)
    = onTargetOrAbove(Build.VERSION_CODES.M, up, down)

inline fun on24orAbove(up: () -> Unit, down: () -> Unit)
    = onTargetOrAbove(Build.VERSION_CODES.N, up, down)