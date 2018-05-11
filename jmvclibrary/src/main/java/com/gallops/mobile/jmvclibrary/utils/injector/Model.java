package com.gallops.mobile.jmvclibrary.utils.injector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * model注解
 * Created by wangyu on 2017/12/7.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Model {
}
