package com.swuos.mobile.utils.kt.proxy;

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Lazy
 * Created by wangyu on 2017/11/1.
 */
// Like Kotlin's lazy delegate but the initializer gets the target and metadata passed to it
class LazyROP<T, V>(private val initializer: (T, KProperty<*>) -> V) : ReadOnlyProperty<T, V> {
    private object EMPTY
    private var value: Any? = EMPTY

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (value == EMPTY) {
            value = initializer(thisRef, property)
        }
        @Suppress("UNCHECKED_CAST")
        return value as V
    }
}
class LazyRWP<T, V>(private val initializer: (T, KProperty<*>) -> V) : ReadWriteProperty<T, V> {
    private object EMPTY
    private var value: Any? = EMPTY
    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (value == EMPTY) {
            value = initializer(thisRef, property)
        }
        @Suppress("UNCHECKED_CAST")
        return value as V
    }

    override fun setValue(thisRef: T, property: KProperty<*>, value: V) {
        this.value = value
    }
}
