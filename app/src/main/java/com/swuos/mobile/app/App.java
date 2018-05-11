package com.swuos.mobile.app;

import com.gallops.mobile.jmvclibrary.app.BaseModel;
import com.gallops.mobile.jmvclibrary.app.JApp;
import com.gallops.mobile.jmvclibrary.models.HttpModel;
import com.swuos.mobile.models.cache.CacheModel;
import com.swuos.mobile.models.user.UserModel;

import java.util.List;

public class App extends JApp {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void registerApi(HttpModel httpModel) {

    }

    @Override
    protected void initModels(List<BaseModel> modelList) {
        modelList.add(new UserModel());
        modelList.add(new CacheModel());
    }
}
