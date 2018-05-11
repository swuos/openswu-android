package com.gallops.mobile.jmvclibrary.utils.kt

/**
 * 字符串处理扩展
 * Created by wangyu on 2018/4/10.
 */

//kt列表拼接成字符串
inline fun <T> appendList(list: List<T>, split: String, field: (it: T) -> String): String {
    val builder = StringBuilder()
    list.forEach {
        builder.append(field(it)).append(split)
    }
    var result = builder.toString()
    if (result.endsWith(split)) result = result.substring(0, result.length - 1)
    return result
}

//java列表拼接成字符串
fun <T> appendListJava(list: List<T>, split: String, field: FieldAction<T>): String {
    val builder = StringBuilder()
    list.forEach {
        builder.append(field.getField(it)).append(split)
    }
    var result = builder.toString()
    if (result.endsWith(split)) result = result.substring(0, result.length - 1)
    return result
}

interface FieldAction<in T> {
    fun getField(it: T): String?
}