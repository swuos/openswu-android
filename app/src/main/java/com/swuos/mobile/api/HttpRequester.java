package com.swuos.mobile.api;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.BaseRequester;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.RouteInterface;
import com.swuos.mobile.app.App;
import com.swuos.mobile.models.user.UserModel;

/**
 * 请求基类，封装了route注解
 * Created by wangyu on 2018/5/11.
 */

public abstract class HttpRequester<T> extends BaseRequester<T> {

    public HttpRequester(@NonNull OnResultListener<T> listener) {
        super(listener);
    }

    @NonNull
    @Override
    protected RouteInterface setRoute() {
        RouteInterface routeInterface = null;
        Class<?> cls = getClass();
        while (cls != null && cls != Object.class) {
            Route route = cls.getAnnotation(Route.class);
            if (route == null) {
                cls = cls.getSuperclass();
            } else {
                routeInterface = route.value();
                break;
            }
        }
        if (routeInterface == null) throw new RuntimeException(this.getClass().getSimpleName() + "缺少路由配置，请确保使用了@Route注解或者重写了setRoute方法");
        return routeInterface;
    }

    protected UserModel getUserModel() {
        return App.getApp().getModel(UserModel.class);
    }
}
