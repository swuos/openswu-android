package com.swuos.mobile.utils.kt.proxy;

import com.swuos.mobile.app.App
import com.swuos.mobile.app.BaseModel
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * model延时加载策略，使用方式
 * {
 *      private val userModel by bindModel(UserModel::class.java)
 * }
 * Created by wangyu on 2017/11/1.
 */

fun <Model : BaseModel> Any.bindModel(cls: Class<Model>)
        : ReadOnlyProperty<Any, Model> = require(cls.name, modelFinder)

private val Any.modelFinder: Any.(String) -> BaseModel?
    get() = { App.getInstance().getModel(it) }

private fun modelNotFound(name: String, desc: KProperty<*>): Nothing =
        throw IllegalStateException("Model Name $name for '${desc.name}' not found.")

@Suppress("UNCHECKED_CAST")
private fun <T, Model : BaseModel> require(name: String, finder: T.(String) -> BaseModel?)
        = LazyROP { t: T, desc -> t.finder(name) as Model? ?: modelNotFound(name, desc) }