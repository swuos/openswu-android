package com.gallops.mobile.jmvclibrary.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.gallops.mobile.jmvclibrary.models.StackModel;
import com.gallops.mobile.jmvclibrary.utils.ToastUtil;
import com.gallops.mobile.jmvclibrary.utils.injector.ModelInjector;
import com.gallops.mobile.jmvclibrary.utils.kt.LoggerKt;
import com.jianyuyouhun.inject.ViewInjector;

import java.util.List;

/**
 * activity基类
 * Created by wangyu on 2018/3/26.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected ProgressDialog mProgressDialog;
    private boolean mIsDestroy;
    private boolean mIsFinish;
    private long mLastClickTime;
    protected static final boolean IS_DEBUG_MODE = JApp.isDebug();
    private StackModel stackModel;
    protected String TAG = "";
    protected static final String KEY_RESULT_DATA = "data";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        TAG = this.getClass().getSimpleName();
        stackModel = JApp.getInstance().getModel(StackModel.class);
        stackModel.registerActivity(this);
        mIsDestroy = false;
        mIsFinish = false;
        int layoutResId = getLayoutResId();
        if (layoutResId != 0) {
            setContentView(layoutResId);
        } else {
            TextView textView = new TextView(this);
            textView.setText(getClass().getSimpleName());
            setContentView(textView);
        }
        ViewInjector.inject(this);
        ModelInjector.injectModel(this);
    }

    /**
     * 返回layoutId
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 弃用直接设置contentView，改为返回getLayoutResId的值实现
     * @param layoutResID
     */
    @Deprecated
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    /**
     * 弃用findViewById，改为ViewInjector注解绑定
     * @param id
     * @param <T>
     * @return
     */
    @Deprecated
    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }

    public void showToast(@StringRes int msgId) {
        ToastUtil.showToast(msgId);
    }

    public void showToast(String msg) {
        ToastUtil.showToast(msg);
    }

    public static int dipToPx(Context context, float dip) {
        return (int) (context.getResources().getDisplayMetrics().density * dip + 0.5f);
    }

    public final Context getContext() {
        return this;
    }

    @Override
    public void finish() {
        super.finish();
        mIsFinish = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stackModel.unregisterActivity(this);
        mIsDestroy = true;
    }

    public void showProgressDialog() {
        showProgressDialog("");
    }

    public void showProgressDialog(String message) {
        if (mIsDestroy) return;
        initProgressDialog();
        mProgressDialog.setMessage(message);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mIsDestroy) return;
        if (mProgressDialog == null) return;
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void initProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setCancelable(false);
        }
    }

    /**
     * 是否在500毫秒内快速点击
     *
     * @return true 快速点击， false 非快速点击
     */
    public synchronized boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (mLastClickTime > time) {
            mLastClickTime = time;
            return false;
        }
        if (time - mLastClickTime < 500) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }

    //判断程序是否在前台运行
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 隐藏键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = this.getCurrentFocus();
        if (currentFocus == null)
            return;
        IBinder windowToken = currentFocus.getWindowToken();
        imm.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 打开键盘
     *
     * @param view view
     */
    public void openSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    /**
     * 启动Activity
     *
     * @param cls 需要启动的界面
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    /**
     * 延时启动页面，默认关闭当前页
     *
     * @param cls   需启动页面
     * @param delay 延时
     */
    public void postStartActivity(Class<? extends Activity> cls, long delay) {
        postStartActivity(cls, delay, true);
    }

    /**
     * 延迟启动
     *
     * @param cls           需启动页面
     * @param delay         延时
     * @param finishCurrent 启动后是否关闭当前页
     */
    public void postStartActivity(Class<? extends Activity> cls, long delay, boolean finishCurrent) {
        getHandler().postDelayed(() -> {
            startActivity(cls);
            if (finishCurrent) {
                finish();
            }
        }, delay);
    }

    /**
     * 启动Activity
     *
     * @param cls         需要启动的页面
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<? extends Activity> cls, int requestCode) {
        startActivityForResult(new Intent(this, cls), requestCode);
    }

    /**
     * 获取BaseActivity上下文
     *
     * @return BaseActivity上下文
     */
    public BaseActivity getActivity() {
        return this;
    }

    /**
     * 启动设置界面
     */
    public void startSystemSettingActivity(int settingsRequestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, settingsRequestCode);
    }

    /**
     * 以类名为标签打印E级别日志
     *
     * @param msg 日志内容
     */
    public void logE(String msg) {
        LoggerKt.lgE(getClass().getSimpleName(), msg);
    }

    /**
     * 以类名为标签打印I级别日志
     *
     * @param msg 日志内容
     */
    public void logI(String msg) {
        LoggerKt.lgI(getClass().getSimpleName(), msg);
    }

    /**
     * 以类名为标签打印W级别日志
     *
     * @param msg 日志内容
     */
    public void logW(String msg) {
        LoggerKt.lgW(getClass().getSimpleName(), msg);
    }

    /**
     * 以类名为标签打印d级别日志
     *
     * @param msg 日志内容
     */
    public void logD(String msg) {
        LoggerKt.lgD(getClass().getSimpleName(), msg);
    }

    /**
     * 获取handler对象
     *
     * @return app的全局handler
     */
    protected Handler getHandler() {
        return JApp.getHandler();
    }
}
