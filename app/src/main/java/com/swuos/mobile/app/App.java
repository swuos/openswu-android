package com.swuos.mobile.app;

import com.gallops.mobile.jmvclibrary.app.BaseModel;
import com.gallops.mobile.jmvclibrary.app.JApp;
import com.gallops.mobile.jmvclibrary.models.HttpModel;
import com.jianyuyouhun.permission.library.EZPermission;
import com.swuos.mobile.BuildConfig;
import com.swuos.mobile.api.ApiConfig;
import com.swuos.mobile.models.cache.CacheModel;
import com.swuos.mobile.models.network.NetworkModel;
import com.swuos.mobile.models.user.UserModel;

import java.util.List;

public class App extends JApp {

    public static App getApp() {
        return (App) getInstance();
    }

    @Override
    protected void initDependencies() {
        EZPermission.Companion.init(this);
    }

    @Override
    protected boolean setDebugMode() {
        return BuildConfig.DEBUG;
    }

    @Override
    protected void registerApi(HttpModel httpModel) {
        httpModel.registerApi(new ApiConfig.AcHostApi());
        httpModel.registerApi(new ApiConfig.FreegattyHostApi());
        httpModel.registerApi(new ApiConfig.VerificationHostApi());
    }

    @Override
    protected void initModels(List<BaseModel> modelList) {
        modelList.add(new UserModel());
        modelList.add(new CacheModel());
        modelList.add(new NetworkModel());
    }
}
