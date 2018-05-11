package com.gallops.mobile.jmvclibrary.http.annotation;

import com.gallops.mobile.jmvclibrary.http.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求方式注解
 * Created by wangyu on 2018/5/11.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequestMethod {
    HttpMethod value();
}
