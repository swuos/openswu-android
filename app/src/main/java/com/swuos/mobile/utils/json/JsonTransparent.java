package com.swuos.mobile.utils.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 结构中不需要JSON序列化的字段
 * Created by wangyu on 2017/4/25.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface JsonTransparent {

}
