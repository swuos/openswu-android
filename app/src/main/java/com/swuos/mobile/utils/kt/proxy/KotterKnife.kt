package com.swuos.mobile.utils.kt.proxy

import android.app.Activity
import android.app.Dialog
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * kotterKnife源码搬运
 * Created by wangyu on 2018/3/14.
 */
fun <V : View> View.bindView(id: Int)
        : ReadOnlyProperty<View, V> = required(id, viewFinder)
fun <V : View> Activity.bindView(id: Int)
        : ReadOnlyProperty<Activity, V> = required(id, viewFinder)
fun <V : View> Dialog.bindView(id: Int)
        : ReadOnlyProperty<Dialog, V> = required(id, viewFinder)
fun <V : View> DialogFragment.bindView(id: Int)
        : ReadOnlyProperty<DialogFragment, V> = required(id, viewFinder)
fun <V : View> Fragment.bindView(id: Int)
        : ReadOnlyProperty<Fragment, V> = required(id, viewFinder)
fun <V : View> RecyclerView.ViewHolder.bindView(id: Int)
        : ReadOnlyProperty<RecyclerView.ViewHolder, V> = required(id, viewFinder)

fun <V : View> View.bindOptionalView(id: Int)
        : ReadOnlyProperty<View, V?> = optional(id, viewFinder)
fun <V : View> Activity.bindOptionalView(id: Int)
        : ReadOnlyProperty<Activity, V?> = optional(id, viewFinder)
fun <V : View> Dialog.bindOptionalView(id: Int)
        : ReadOnlyProperty<Dialog, V?> = optional(id, viewFinder)
fun <V : View> DialogFragment.bindOptionalView(id: Int)
        : ReadOnlyProperty<DialogFragment, V?> = optional(id, viewFinder)
fun <V : View> Fragment.bindOptionalView(id: Int)
        : ReadOnlyProperty<Fragment, V?> = optional(id, viewFinder)
fun <V : View> RecyclerView.ViewHolder.bindOptionalView(id: Int)
        : ReadOnlyProperty<RecyclerView.ViewHolder, V?> = optional(id, viewFinder)

fun <V : View> View.bindViews(vararg ids: Int)
        : ReadOnlyProperty<View, List<V>> = required(ids, viewFinder)
fun <V : View> Activity.bindViews(vararg ids: Int)
        : ReadOnlyProperty<Activity, List<V>> = required(ids, viewFinder)
fun <V : View> Dialog.bindViews(vararg ids: Int)
        : ReadOnlyProperty<Dialog, List<V>> = required(ids, viewFinder)
fun <V : View> DialogFragment.bindViews(vararg ids: Int)
        : ReadOnlyProperty<DialogFragment, List<V>> = required(ids, viewFinder)
fun <V : View> Fragment.bindViews(vararg ids: Int)
        : ReadOnlyProperty<Fragment, List<V>> = required(ids, viewFinder)
fun <V : View> RecyclerView.ViewHolder.bindViews(vararg ids: Int)
        : ReadOnlyProperty<RecyclerView.ViewHolder, List<V>> = required(ids, viewFinder)
fun <V : View> View.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<View, List<V>> = optional(ids, viewFinder)
fun <V : View> Activity.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<Activity, List<V>> = optional(ids, viewFinder)
fun <V : View> Dialog.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<Dialog, List<V>> = optional(ids, viewFinder)
fun <V : View> DialogFragment.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<DialogFragment, List<V>> = optional(ids, viewFinder)
fun <V : View> Fragment.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<Fragment, List<V>> = optional(ids, viewFinder)
fun <V : View> RecyclerView.ViewHolder.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<RecyclerView.ViewHolder, List<V>> = optional(ids, viewFinder)

private val View.viewFinder: View.(Int) -> View?
    get() = { findViewById(it) }
private val Activity.viewFinder: Activity.(Int) -> View?
    get() = { findViewById(it) }
private val Dialog.viewFinder: Dialog.(Int) -> View?
    get() = { findViewById(it) }
private val DialogFragment.viewFinder: DialogFragment.(Int) -> View?
    get() = { dialog?.findViewById(it) ?: view?.findViewById(it) }
private val Fragment.viewFinder: Fragment.(Int) -> View?
    get() = { view?.findViewById(it) }
private val RecyclerView.ViewHolder.viewFinder: RecyclerView.ViewHolder.(Int) -> View?
    get() = { itemView.findViewById(it) }

private fun viewNotFound(id: Int, desc: KProperty<*>): Nothing =
        throw IllegalStateException("View ID $id for '${desc.name}' not found.")

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(id: Int, finder: T.(Int) -> View?)
        = LazyROP { t: T, desc -> t.finder(id) as V? ?: viewNotFound(id, desc) }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(id: Int, finder: T.(Int) -> View?)
        = LazyROP { t: T, desc -> t.finder(id) as V? }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(ids: IntArray, finder: T.(Int) -> View?)
        = LazyROP { t: T, desc -> ids.map { t.finder(it) as V? ?: viewNotFound(it, desc) } }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(ids: IntArray, finder: T.(Int) -> View?)
        = LazyROP { t: T, desc -> ids.map { t.finder(it) as V? }.filterNotNull() }

