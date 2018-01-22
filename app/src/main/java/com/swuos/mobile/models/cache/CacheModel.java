package com.swuos.mobile.models.cache;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.swuos.mobile.app.App;
import com.swuos.mobile.app.BaseModel;
import com.swuos.mobile.entity.UserInfo;
import com.swuos.mobile.entity.UserWifiInfo;
import com.swuos.mobile.models.user.OnUserStateChangeListener;
import com.swuos.mobile.models.user.SimpleUserStateChangeListener;
import com.swuos.mobile.models.user.UserModel;
import com.swuos.mobile.utils.injector.Model;
import com.swuos.mobile.utils.json.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 缓存管理器，区分用户账户
 * Created by wangyu on 2017/12/7.
 */

public class CacheModel extends BaseModel {

    private static final String CACHE_NAME = "app_cache";
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    @Model
    private UserModel userModel;

    private static final String GUEST_ID = "-1";//未登录时使用访客缓存，主要避免空指针问题。业务逻辑上并没有访客

    @Override
    public void onModelCreate(Application application) {
        super.onModelCreate(application);
        userModel.addOnUserStateChangeListener(new SimpleUserStateChangeListener() {
            @Override
            public void onLogin(UserInfo userInfo) {
                initCache();
            }

            @Override
            public void onLogout(UserInfo userInfo) {
                initCache();
            }
        });
    }

    private void initCache() {
        String userId;
        if (userModel.isNeedLogin()) {
            userId = GUEST_ID;
        } else {
            UserInfo userInfo = userModel.getUserInfo();
            if (userInfo == null) {
                userId = GUEST_ID;
            } else {
                userId = userInfo.getStudentId();
            }
        }
        String cacheKey = CACHE_NAME + userId;
        sp = App.getInstance().getSharedPreferences(cacheKey, Context.MODE_PRIVATE);
        spEditor = sp.edit();
        spEditor.apply();
    }

    /**
     * 将lis的数据放入缓存中
     *
     * @param key  缓存的KEY
     * @param list 需要缓存的数据
     */
    public <V> void putList(String key, List<V> list) {
        if (list == null) {
            return;
        }
        JSONArray jsonArray = new JSONArray();
        for (Object object : list) {
            JSONObject jsonObject = JsonUtil.toJSONObject(object);
            if (jsonObject != null) {
                jsonArray.put(jsonObject);
            }
        }
        putString(key, jsonArray.toString());
    }

    /**
     * 取出缓存列表
     * @param key
     * @param cls
     * @param <T>
     * @return
     */
    public <T> List<T> getList(String key, Class<T> cls) {
        List<T> list = new ArrayList<>();
        String jsonStr = getString(key, "");
        if ("".equals(jsonStr)) {
            return list;
        }
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            list = JsonUtil.toList(jsonArray, cls);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 缓存实体
     * @param key       key
     * @param object    object
     */
    public void putObject(String key, Object object) {
        JSONObject jsonObject = JsonUtil.toJSONObject(object);
        putString(key, jsonObject.toString());
    }

    /**
     * 取出缓存实体
     * @param key       key
     * @param cls       cls类型
     * @param <T>       泛型
     * @return  返回实体
     */
    public <T> T getObject(String key, Class<T> cls) {
        String jsonStr = getString(key, "");
        if ("".equals(jsonStr)) {
            return null;
        }
        T t;
        try {
            t = JsonUtil.toObject(new JSONObject(jsonStr), cls);
        } catch (JSONException e) {
            e.printStackTrace();
            t = null;
        }
        return t;
    }

    /**
     * SP中写入String类型value
     *
     * @param key   键
     * @param value 值
     */
    public void putString(String key, String value) {
        spEditor.putString(key, value).apply();
    }

    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    /**
     * SP中写入int类型value
     *
     * @param key   键
     * @param value 值
     */
    public void putInt(String key, int value) {
        spEditor.putInt(key, value).apply();
    }

    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /**
     * SP中写入long类型value
     *
     * @param key   键
     * @param value 值
     */
    public void putLong(String key, long value) {
        spEditor.putLong(key, value).apply();
    }

    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public long getLong(String key) {
        return getLong(key, -1L);
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /**
     * SP中写入float类型value
     *
     * @param key   键
     * @param value 值
     */
    public void putFloat(String key, float value) {
        spEditor.putFloat(key, value).apply();
    }

    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public float getFloat(String key) {
        return getFloat(key, -1f);
    }

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    /**
     * SP中写入boolean类型value
     *
     * @param key   键
     * @param value 值
     */
    public void putBoolean(String key, boolean value) {
        spEditor.putBoolean(key, value).apply();
    }

    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    public void remove(String key) {
        spEditor.remove(key).apply();
    }

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     * SP中清除所有数据
     */
    public void clear() {
        spEditor.clear().apply();
    }
}
