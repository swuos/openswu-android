package com.swuos.mobile.app;

import android.app.Application;
import android.content.Context;

/**
 * 控制器基类
 * Created by wangyu on 2017/12/7.
 */

public abstract class BaseModel {

    protected String TAG;

    protected Application application;

    public void onModelCreate(Application application) {
        TAG = this.getClass().getSimpleName();
        this.application = application;
    }

    public void onAllModelCreate() {

    }

    protected  <M extends BaseModel> M getModel(Class<M> cls) {
        return App.getInstance().getModel(cls);
    }

    protected Context getApplicationContext() {
        return application.getApplicationContext();
    }

    protected Context getBaseContext() {
        return application.getBaseContext();
    }
}
