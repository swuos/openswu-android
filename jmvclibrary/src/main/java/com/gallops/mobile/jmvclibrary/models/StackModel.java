package com.gallops.mobile.jmvclibrary.models;

import android.app.Application;

import com.gallops.mobile.jmvclibrary.app.BaseActivity;
import com.gallops.mobile.jmvclibrary.app.BaseModel;
import com.gallops.mobile.jmvclibrary.utils.kt.LoggerKt;

import java.util.ArrayList;
import java.util.List;

/**
 * activity栈管理器
 * Created by wangyu on 2017/12/8.
 */

public class StackModel extends BaseModel {

    private List<BaseActivity> activityList = new ArrayList<>();

    @Override
    public void onModelCreate(Application application) {
        super.onModelCreate(application);
    }

    public void registerActivity(BaseActivity activity) {
        LoggerKt.lgD(TAG, "register " + activity.getClass().getSimpleName() + activity.toString());
        activityList.add(activity);
    }

    public void unregisterActivity(BaseActivity activity) {
        LoggerKt.lgD(TAG, "unregister " + activity.getClass().getSimpleName() + activity.toString());
        activityList.remove(activity);
    }

    public List<BaseActivity> getActivityList() {
        return activityList;
    }

    /**
     * 判断app还有没有activity活跃
     *
     * @return
     */
    public AppState getAppState() {
        return activityList.size() != 0 ? AppState.RUNNING : AppState.DIED;
    }

    public enum AppState {
        RUNNING, DIED;
    }
}
