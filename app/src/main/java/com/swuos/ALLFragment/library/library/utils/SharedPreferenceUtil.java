package com.swuos.ALLFragment.library.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swuos.ALLFragment.library.library.model.BookItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by : youngkaaa on 2016/10/29.
 * Contact me : 645326280@qq.com
 */

public class SharedPreferenceUtil {
    private static final String TAG = "SharedPreferenceUtil";
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    public static final String SP_USER_NAME = "SP_USER_NAME";
    public static final String SP_USER_PASSWORD = "SP_USER_PASSWORD";
    public static final String SP_STRING_DEFAULT = "SP_STRING_DEFAULT";
    public static final String SP_FILE_NAME = "Swulib";

    public static final String SP_LIST_BOOKITEM = "SP_LIST_BOOKITEM";


    public SharedPreferenceUtil(Context context) {
        mPreferences = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public void putUserName(String name) {
        mEditor.putString(SP_USER_NAME, name);
        mEditor.commit();
    }

    public String getUserName() {
        return mPreferences.getString(SP_USER_NAME, SP_STRING_DEFAULT);
    }

    public void putPassword(String password) {
        mEditor.putString(SP_USER_PASSWORD, password);
        mEditor.commit();
    }

    public String getPassword() {
        return mPreferences.getString(SP_USER_PASSWORD, SP_STRING_DEFAULT);
    }

    public boolean isLogin() {
        if (!getUserName().equals(SP_STRING_DEFAULT) && !getPassword().equals(SP_STRING_DEFAULT)) {
            return true;
        }
        return false;
    }

    public void clearNameAndPassword() {
        mEditor.putString(SP_USER_NAME, SP_STRING_DEFAULT);
        mEditor.putString(SP_USER_PASSWORD, SP_STRING_DEFAULT);
        mEditor.commit();
    }

    public void putBookList(List<BookItem> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        Log.d(TAG, json);
        mEditor.putString(SP_LIST_BOOKITEM, json);
        mEditor.commit();
    }

    public List<BookItem> getBookList() {
        String json = mPreferences.getString(SP_LIST_BOOKITEM, null);
        List<BookItem> bookItems = null;
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<BookItem>>() {}.getType();
            bookItems = new ArrayList<>();
            bookItems=gson.fromJson(json, type);
            for(BookItem item:bookItems){
                Log.d(TAG,item.toString());
            }
        }else{
            Log.d(TAG,"json=null");
        }
        return bookItems;
    }

}
