package com.gallops.mobile.jmvclibrary.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.gallops.mobile.jmvclibrary.app.JApp;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具
 * Created by wangyu on 2017/3/17.
 */

public class CommonUtils {
    private final static String TAG = "CommonUtils";
    private static int m_screenDPI = -1;
    private static int m_simCardState = -1;
    private static String m_sDeviceVersion = "";
    private static String m_sIMEI = "";
    private static int m_iSdkVersion = 0;
    private static float m_iDeviceVersion = 0;
    private static String m_strPhoneStyle = "";// 手机型号
    private static DisplayMetrics m_displayMetrics;
    private static int m_screenWidth = -1;
    private static int m_screenHeight = -1;
    private static int m_statusBarHeight = -1;
    private static String m_phoneVersion;// 手机版本号

    /**
     * 获得进程名字
     */
    public static String getUIPName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    // 获取版本号
    public static String getStringDeviceVersion() {
        m_sDeviceVersion = android.os.Build.VERSION.RELEASE;

        return m_sDeviceVersion;
    }

    // 获得数字版本号
    public static float getIntDeviceVersion() {
        m_sDeviceVersion = android.os.Build.VERSION.RELEASE;
        if (m_sDeviceVersion != null && m_sDeviceVersion.length() >= 3) {
            String spiltString = m_sDeviceVersion.substring(0, 3);
            Pattern pattern = Pattern.compile("^\\d+([\\.]?\\d+)?$");
            Matcher matcher = pattern.matcher(spiltString);
            boolean result = matcher.matches();
            if (result == true) {
                m_iDeviceVersion = Float.valueOf(spiltString);
            } else {
                m_iDeviceVersion = 0;
            }
        }
        return m_iDeviceVersion;

    }

    public static String getPhoneVersion() {
        m_phoneVersion = android.os.Build.VERSION.CODENAME;
        return m_phoneVersion;
    }

    public static int getDeviceSdk() {
        m_iSdkVersion = android.os.Build.VERSION.SDK_INT;
        return m_iSdkVersion;
    }

    @SuppressLint("MissingPermission")
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) JApp.getInstance().getSystemService(Service.TELEPHONY_SERVICE);
        if (tm.getDeviceId() != null && !tm.getDeviceId().equals("")) {
            m_sIMEI = null == tm.getDeviceId() ? "" : tm.getDeviceId();
        }
        return m_sIMEI;

    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getPhoneStyle() {
        m_strPhoneStyle = android.os.Build.MODEL;
        return m_strPhoneStyle;
    }

    /**
     * 获取国际移动用户识别码
     *
     * @param context 上下文
     * @return 手机号码，取不到时返回空字符串
     */
    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imsi = tm.getSubscriberId();
        if (null == imsi) imsi = "";
        return imsi;
    }

    /**
     * 获取手机电话号码
     *
     * @param context 上下文
     * @return 手机号码，取不到时返回空字符串
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String phoneNumber = tm.getLine1Number();
        if (phoneNumber == null) phoneNumber = "";
        return phoneNumber;
    }

    /**
     * 获取手机ip地址
     *
     * @return ip地址，没有获取到时返回空字符串
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static int getScreenDPI() {
        if (m_screenDPI == -1) {
            DisplayMetrics metric = new DisplayMetrics();
            WindowManager wndMgr = (WindowManager) JApp.getInstance().getSystemService(Context.WINDOW_SERVICE);
            wndMgr.getDefaultDisplay().getMetrics(metric);
            m_screenDPI = metric.densityDpi;
        }
        return m_screenDPI;
    }

    public static int getSIMCardState(Context context) {
        if (m_simCardState == -1) {
            TelephonyManager l_TelephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            m_simCardState = l_TelephonyManager.getSimState();
        }
        return m_simCardState;
    }

    /**
     * 获取文字高度和行高
     *
     * @param fontSize
     * @return
     */
    public static int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * 将dip转换为px
     *
     * @param dip
     * @return
     */
    public static int dipToPx(Context context, float dip) {
        float s = context.getResources().getDisplayMetrics().density;
        return (int) (dip * s + 0.5f);
    }

    /**
     * 检查邮箱地址是否符合规范
     *
     * @param a_strEmailAddress
     * @return
     */
    public static boolean checkEmailAddressFormat(String a_strEmailAddress) {
        if (a_strEmailAddress == null || a_strEmailAddress.trim().equals("")) return false;

        return Pattern.matches("\\w(\\.?\\w)*\\@\\w+(\\.[\\w&&[\\D]]+)+", a_strEmailAddress);

    }

    /**
     * 检查密码是否符合规范（规范为密码长度是6-20位，而且只能是字母或者是数字的组合）
     */
    public static boolean checkPasswordFormat(String a_strPassword) {
        if (a_strPassword == null || a_strPassword.trim().equals("")) return false;

        if (a_strPassword.length() < 6 || a_strPassword.length() > 20) return false;

        return Pattern.matches("[\\da-zA-Z]+", a_strPassword);

    }

    public static int getScreenWidth() {
        if (m_screenWidth == -1) {
            initDisplayMetrics();
            m_screenWidth = m_displayMetrics.widthPixels;
        }
        return m_screenWidth;
    }

    public static int getScreenHeight() {
        if (m_screenHeight == -1) {
            initDisplayMetrics();
            m_screenHeight = m_displayMetrics.heightPixels;
        }
        return m_screenHeight;
    }

    public static int getStatusHeight() {
        if (m_statusBarHeight == -1) {
            //获取status_bar_height资源的ID
            int resourceId = JApp.getInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                m_statusBarHeight = JApp.getInstance().getResources().getDimensionPixelSize(resourceId);
            }
        }
        return m_statusBarHeight;
    }

    private static void initDisplayMetrics() {
        if (m_displayMetrics == null) {
            m_displayMetrics = new DisplayMetrics();
            WindowManager wndMgr = (WindowManager) JApp.getInstance().getSystemService(Context.WINDOW_SERVICE);
            wndMgr.getDefaultDisplay().getMetrics(m_displayMetrics);
        }
    }

    /**
     * 判断是否为纯数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        } else {
            try {
                Long.valueOf(str);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     * @return
     */
    public static final boolean hideSoftPad(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            return ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    /**
     * 判断应用是否处于后台
     *
     * @param context 上下文
     * @return 是否处于后台，true ：后台， false：前台
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
            }
        }
        return false;
    }

    /**
     * 是否是手机号
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 调到系统首页
     *
     * @param context
     */
    public static void startHome(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }


    /**
     * double转string
     *
     * @param digits       保留几位小数
     * @param sourceDouble 源数据
     * @return doubleString
     */
    public static String getDoubleWithDigit(int digits, double sourceDouble) {
        if (digits <= 0) {
            digits = 0;
        }
        return String.format(Locale.getDefault(), "%." + digits + "f", sourceDouble);
    }
}
