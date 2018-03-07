package com.swuos.mobile.models;

import android.app.Application;

import com.swuos.mobile.ui.tab.MainActivity;
import com.swuos.mobile.app.BaseActivity;
import com.swuos.mobile.app.BaseModel;
import com.swuos.mobile.utils.LoggerKt;

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

    /**
     * 判断app还有没有activity活跃
     *
     * @return
     */
    public AppState getAppState() {
        return activityList.size() != 0 ? AppState.RUNNING : AppState.DIED;
    }

    /**
     * 首页是否存活
     *
     * @return true/false
     */
    public boolean isMainActivityOn() {
        for (BaseActivity baseActivity : activityList) {
            if (baseActivity instanceof MainActivity) {
                return true;
            }
        }
        return false;
    }

    public enum AppState {
        RUNNING, DIED;
    }
}
