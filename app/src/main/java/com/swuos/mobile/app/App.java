package com.swuos.mobile.app;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.jianyuyouhun.permission.library.EZPermission;
import com.swuos.mobile.BuildConfig;
import com.swuos.mobile.app.exception.ExceptionCaughtAdapter;
import com.swuos.mobile.models.StackModel;
import com.swuos.mobile.models.cache.CacheModel;
import com.swuos.mobile.models.http.HttpModel;
import com.swuos.mobile.models.network.NetworkModel;
import com.swuos.mobile.models.user.UserModel;
import com.swuos.mobile.utils.CommonUtils;
import com.swuos.mobile.utils.LoggerKt;
import com.swuos.mobile.utils.injector.ModelInjector;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * app
 * Created by wangyu on 2018/1/19.
 */

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    private static App instance;

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

    public static App getInstance() {
        return instance;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance != null) {
            instance = this;
            return;
        }
        instance = this;
        isDebug = setDebugMode();
        initExceptionCatcher();
        initDependencies();
        String pidName = CommonUtils.getUIPName(this);
        mIsMainProcess = pidName.equals(getPackageName());
        initApp();
    }

    /**
     * 第三方框架初始化
     */
    protected void initDependencies() {
        EZPermission.Companion.init(this);
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
                // TODO: 2018/2/26 appId未配置
                CrashReport.initCrashReport(this, "", false);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private boolean setDebugMode() {
        return BuildConfig.DEBUG;
    }

    private void initApp() {
        List<BaseModel> modelList = new ArrayList<>();
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

    private void initModels(List<BaseModel> modelList) {
        modelList.add(new HttpModel());             //http执行环境
        modelList.add(new StackModel());            //activity栈管理
        modelList.add(new NetworkModel());          //网络状态变化管理
        modelList.add(new UserModel());             //用户管理器
        modelList.add(new CacheModel());            //缓存管理器
    }

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
