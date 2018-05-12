package com.gallops.mobile.jmvclibrary.app;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.gallops.mobile.jmvclibrary.BuildConfig;
import com.gallops.mobile.jmvclibrary.app.exception.ExceptionCaughtAdapter;
import com.gallops.mobile.jmvclibrary.models.HttpModel;
import com.gallops.mobile.jmvclibrary.models.SettingCacheModel;
import com.gallops.mobile.jmvclibrary.models.StackModel;
import com.gallops.mobile.jmvclibrary.utils.CommonUtils;
import com.gallops.mobile.jmvclibrary.utils.injector.ModelInjector;
import com.gallops.mobile.jmvclibrary.utils.kt.LoggerKt;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * app基类
 * Created by wangyu on 2018/3/26.
 */

public abstract class JApp extends Application {

    private static String TAG;

    private static String buglyId;

    private static JApp instance;

    private static boolean isDebug;

    private static Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 是否是在主线程中
     */
    private boolean mIsMainProcess = false;

    private HashMap<String, BaseModel> modelsMap = new HashMap<>();

    public static Handler getHandler() {
        return handler;
    }

    public static JApp getInstance() {
        return instance;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TAG = getClass().getSimpleName();
        if (instance != null) {
            instance = this;
            return;
        }
        instance = this;
        isDebug = setDebugMode();
        buglyId = setBuglyId();
        initExceptionCatcher();
        initDependencies();
        String pidName = CommonUtils.getUIPName(this);
        mIsMainProcess = pidName.equals(getPackageName());
        initApp();
    }

    protected boolean setDebugMode() {
        return BuildConfig.DEBUG;
    }

    /**
     * 设置bugly崩溃统计
     * @return
     */
    protected String setBuglyId() {
        return "";
    }

    /**
     * 第三方框架初始化
     */
    protected void initDependencies() {

    }

    private void initExceptionCatcher() {
        if (isDebug) {
            //debug版本   启用崩溃日志捕获
            Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
            ExceptionCaughtAdapter exceptionCaughtAdapter = new ExceptionCaughtAdapter(handler);
            Thread.setDefaultUncaughtExceptionHandler(exceptionCaughtAdapter);
        } else {
            try {
                // 正式版本  启用崩溃提交
                CrashReport.initCrashReport(this, buglyId, false);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private void initApp() {
        List<BaseModel> modelList = new ArrayList<>();
        initCommonModels(modelList);
        initModels(modelList);
        for (BaseModel model : modelList) {
            long time = System.currentTimeMillis();
            ModelInjector.injectModel(model);
            model.onModelCreate(this);
            Class<? extends BaseModel> baseModelClass = model.getClass();
            String name = baseModelClass.getName();
            modelsMap.put(name, model);
            // 打印初始化耗时
            long spendTime = System.currentTimeMillis() - time;
            LoggerKt.lgE(TAG, baseModelClass.getSimpleName() + "启动耗时(毫秒)：" + spendTime);
        }
        for (BaseModel model : modelList) {
            model.onAllModelCreate();
        }
    }

    private void initCommonModels(List<BaseModel> modelList) {
        HttpModel httpModel = new HttpModel();
        registerApi(httpModel);
        modelList.add(httpModel);             //http执行环境管理
        modelList.add(new StackModel());            //activity栈管理
        modelList.add(new SettingCacheModel());     //app设置缓存管理器，与用户相关的缓存建议另建立model
    }

    /**
     * 注册api
     * @param httpModel httpModel
     */
    protected abstract void registerApi(HttpModel httpModel);

    /**
     * 注册控制器
     * @param modelList
     */
    protected abstract void initModels(List<BaseModel> modelList);


    /**
     * app是否处在主进程
     */
    public boolean isMainProcess() {
        return mIsMainProcess;
    }

    /**
     * 获取后台常驻Model
     *
     * @param <Model> Model类
     * @return model
     */
    @SuppressWarnings("unchecked")
    public <Model extends BaseModel> Model getModel(Class<Model> model) {
        return getModel(model.getName());
    }

    @SuppressWarnings("unchecked")
    public <Model extends BaseModel> Model getModel(String modelName) {
        Model result = (Model) modelsMap.get(modelName);
        if (result == null) {
            throw new NullPointerException("无法获取到已注册的" + modelName + "，请确保目标Model为后台常驻Model类型");
        }
        return result;
    }
}
