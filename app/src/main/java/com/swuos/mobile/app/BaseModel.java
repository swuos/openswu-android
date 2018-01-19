package com.swuos.mobile.app;

import android.app.Application;

/**
 * 控制器基类
 * Created by wangyu on 2017/12/7.
 */

public abstract class BaseModel {

    protected String TAG;

    public void onModelCreate(Application application) {
        TAG = this.getClass().getSimpleName();
    }

    public void onAllModelCreate() {

    }

    protected  <M extends BaseModel> M getModel(Class<M> cls) {
        return App.getInstance().getModel(cls);
    }
}
