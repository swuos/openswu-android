package com.gallops.mobile.jmvclibrary.http.annotation;

import com.gallops.mobile.jmvclibrary.http.creator.BodyCreateAction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入参注解
 * Created by wangyu on 2018/5/11.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BodyCreator {
    Class<? extends BodyCreateAction> value();
}
