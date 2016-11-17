package com.swuos.ALLFragment.library.library.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.swuos.ALLFragment.library.library.model.BookItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


/**
 * Created by youngkaaa on 2016/5/29.
 * Email:  645326280@qq.com
 */
public class Tools {

    //关闭软键盘
    public static void closeSoftKeyBoard(Activity activity) {
        View peekDecorView = activity.getWindow().peekDecorView();
        if (peekDecorView != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(peekDecorView.getWindowToken(), 0);
        }
    }

    public static String getDevice() {
        return Build.MODEL;
    }

    public static String getSystemProperty(String propName) {
        String line;
        String TAG = "MIUI";
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Log.e(TAG, "Unable to read sysprop " + propName, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e(TAG, "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }
    public static void log(String TAG, BookItem item) {
            Log.d(TAG, "\n");
            Log.d(TAG, "****************************************************");
            Log.d(TAG, "** item.getBookName()==>" + item.getBookName());
            Log.d(TAG, "** item.getBookIsbn()==>" + item.getBookIsbn());
            Log.d(TAG, "** item.getKind()==>" + item.getKind());
            Log.d(TAG, "** item.getTime()==>" + item.getTime());
            Log.d(TAG, "** item.getFine()==>" + item.getFine());
            Log.d(TAG, "***************************************************");
            Log.d(TAG, "\n");
    }

    public static void log(String TAG, List<BookItem> bookItems) {
        for (BookItem item : bookItems) {
            Log.d(TAG, "\n");
            Log.d(TAG, "****************************************************");
            Log.d(TAG, "** item.getBookName()==>" + item.getBookName());
            Log.d(TAG, "** item.getBookIsbn()==>" + item.getBookIsbn());
            Log.d(TAG, "** item.getKind()==>" + item.getKind());
            Log.d(TAG, "** item.getTime()==>" + item.getTime());
            Log.d(TAG, "** item.getFine()==>" + item.getFine());
            Log.d(TAG, "***************************************************");
            Log.d(TAG, "\n");
        }
    }
}
