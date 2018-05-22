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
import com.swuos.mobile.entity.LibBookshelfItem;
import com.swuos.mobile.entity.LibPopularItem;
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
@Route(RouteEnum.GET_LIB_BOOKSHELF)
@RequestMethod(HttpMethod.POST)
@BodyCreator(JsonBodyCreator.class)
public class LibBookshelfRequest extends FreegattyHostRequester<List<LibBookshelfItem>> {
    private Context context;
    // 学号
    private String swuId;

    public LibBookshelfRequest(Context context, String swuId, @NonNull OnResultListener<List<LibBookshelfItem>> listener) {
        super(listener);
        this.swuId = swuId;
        this.context = context;
    }

    @Override
    protected void onPutParams(@NonNull Map<String, Object> params) {
        params.put("swuId", swuId);
    }

    @Override
    protected List<LibBookshelfItem> onDumpData(@NonNull JSONObject jsonObject) throws JSONException {
        // 缓存数据
        LibCacheManager.instance(context).cacheBookshelf(jsonObject.toString());
        return LibParser.parserLibBookshelfList(jsonObject);
    }
}
