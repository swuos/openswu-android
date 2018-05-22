package com.swuos.mobile.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.swuos.mobile.adapter.lib.LibBookshelfAdapter;
import com.swuos.mobile.entity.LibBookshelfItem;
import com.swuos.mobile.entity.LibPopularItem;

import org.json.JSONObject;

import java.util.List;

/**
 * 图书馆模块 缓存类
 * Created by youngkaaa on 2018/5/21
 */
public class LibCacheManager {
    private static final String FILE_NAME = "lib_cache";
    private static final String KEY_POPULAR = "key_popular";
    private static final String KEY_BOOKSHELF = "key_bookshelf";


    private static LibCacheManager INSTANCE;
    private Context context;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    // private
    private LibCacheManager(Context context) {
        if (context == null) {
            return;
        }
        this.context = context;
        mPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static LibCacheManager instance(Context context) {
        if (INSTANCE == null) {
            synchronized (LibCacheManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LibCacheManager(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 缓存了热门书籍数据
     * @param cache
     */
    public void cachePopular(String cache) {
        cache(KEY_POPULAR, cache);
    }

    /**
     * 缓存 我的书架 数据
     * @param cache
     */
    public void cacheBookshelf(String cache) {
        cache(KEY_BOOKSHELF, cache);
    }

    /**
     * 缓存数据
     * @param key
     * @param value
     */
    private void cache(String key, String value) {
        if (mEditor == null || mPreferences == null || TextUtils.isEmpty(value) || TextUtils.isEmpty(key)) {
            return;
        }
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public List<LibPopularItem> getCachePopularList() {
        if (mEditor == null || mPreferences == null) {
            return null;
        }
        try {
            String popularStr = mPreferences.getString(KEY_POPULAR, "");
            if (TextUtils.isEmpty(popularStr)) {
                return null;
            }
            return LibParser.parserLibPopularList(new JSONObject(popularStr));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<LibBookshelfItem> getCacheBookshelfList() {
        if (mEditor == null || mPreferences == null) {
            return null;
        }
        try {
            String popularStr = mPreferences.getString(KEY_BOOKSHELF, "");
            if (TextUtils.isEmpty(popularStr)) {
                return null;
            }
            return LibParser.parserLibBookshelfList(new JSONObject(popularStr));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
