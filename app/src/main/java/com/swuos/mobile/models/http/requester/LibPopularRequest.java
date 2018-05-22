package com.swuos.mobile.models.http.requester;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.BodyCreator;
import com.gallops.mobile.jmvclibrary.http.annotation.RequestMethod;
import com.gallops.mobile.jmvclibrary.http.creator.JsonBodyCreator;
import com.swuos.mobile.api.FreegattyHostRequester;
import com.swuos.mobile.api.Route;
import com.swuos.mobile.api.RouteEnum;
import com.swuos.mobile.entity.LibPopularItem;
import com.swuos.mobile.utils.L;
import com.swuos.mobile.utils.LibCacheManager;
import com.swuos.mobile.utils.LibParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by youngkaaa on 2018/5/18
 */
@Route(RouteEnum.GET_LIB_POPULAR)
@RequestMethod(HttpMethod.GET)
@BodyCreator(JsonBodyCreator.class)
public class LibPopularRequest extends FreegattyHostRequester<List<LibPopularItem>> {
    private Context context;
    // 分页页码
    private int page;


    public LibPopularRequest(Context context, @NonNull OnResultListener<List<LibPopularItem>> listener) {
        super(listener);
        this.context = context;
    }

    @Override
    public void onPutParams(@NonNull Map params) {
        params.put("page", page);
    }

    @Override
    protected List<LibPopularItem> onDumpData(@NonNull JSONObject jsonObject) throws JSONException {
        L.d("page->" + page);
        L.d("jsonObject.toString()->" + jsonObject.toString());
        // 只缓存第一页数据
        if (page == 0) {
            LibCacheManager.instance(context).cachePopular(jsonObject.toString());
        }
        page++;
        return LibParser.parserLibPopularList(jsonObject);
    }

    public int getPage() {
        return page;
    }

    // 重置分页页码
    public void reset() {
        page = 0;
    }
}
