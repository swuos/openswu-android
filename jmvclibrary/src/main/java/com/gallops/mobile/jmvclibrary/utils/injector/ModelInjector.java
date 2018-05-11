package com.gallops.mobile.jmvclibrary.utils.injector;

import com.gallops.mobile.jmvclibrary.app.BaseModel;
import com.gallops.mobile.jmvclibrary.app.JApp;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 注解model
 * Created by wangyu on 2017/6/30.
 */

public class ModelInjector {

    public static void injectModel(Object object) {
        Class<?> aClass = object.getClass();

        while (aClass != BaseModel.class && aClass != Object.class) {
            Field[] declaredFields = aClass.getDeclaredFields();
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    int modifiers = field.getModifiers();
                    if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
                        // 忽略掉static 和 final 修饰的变量
                        continue;
                    }

                    if (!field.isAnnotationPresent(Model.class)) {
                        continue;
                    }

                    Class<?> type = field.getType();
                    if (!BaseModel.class.isAssignableFrom(type)) {
                        throw new RuntimeException("@Model 只能在BaseModel子类中使用");
                    }

                    @SuppressWarnings("unchecked")
                    BaseModel baseModel = JApp.getInstance().getModel((Class<? extends BaseModel>) type);

                    if (baseModel == null) {
                        throw new RuntimeException(type.getSimpleName() + " Model还未初始化！");
                    }

                    if (!field.isAccessible())
                        field.setAccessible(true);

                    try {
                        field.set(object, baseModel);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            aClass = aClass.getSuperclass();
        }
    }
}
