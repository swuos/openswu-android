package com.swuos.mobile.models.http.requester;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.BodyCreator;
import com.gallops.mobile.jmvclibrary.http.annotation.RequestMethod;
import com.gallops.mobile.jmvclibrary.http.creator.JsonBodyCreator;
import com.swuos.mobile.api.FreegattyHostRequester;
import com.swuos.mobile.api.Route;
import com.swuos.mobile.api.RouteEnum;
import com.swuos.mobile.entity.LibPopularItem;
import com.swuos.mobile.entity.LibSearchItem;
import com.swuos.mobile.utils.L;
import com.swuos.mobile.utils.LibCacheManager;
import com.swuos.mobile.utils.LibParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.PortUnreachableException;
import java.util.List;
import java.util.Map;

/**
 * Created by youngkaaa on 2018/5/18
 */
@Route(RouteEnum.GET_LIB_SEARCH)
@RequestMethod(HttpMethod.POST)
@BodyCreator(JsonBodyCreator.class)
public class LibSearchRequest extends FreegattyHostRequester<List<LibSearchItem>> {
    private Context context;
    private String bookName;

    public LibSearchRequest(Context context, @NonNull OnResultListener<List<LibSearchItem>> listener) {
        super(listener);
        this.context = context;
    }

    @Override
    public void onPutParams(@NonNull Map params) {
        params.put("bookName", bookName);
    }

    @Override
    protected List<LibSearchItem> onDumpData(@NonNull JSONObject jsonObject){
        L.d("jsonObject.toString()->" + jsonObject.toString());
        return LibParser.parserLibSearchList(jsonObject);
    }

    /**
     * 搜索指定图书
     * @param name 如果书名为空 不做任何操作
     */
    public void search(String name) {
        if(TextUtils.isEmpty(name)){
            return;
        }
        bookName = name;
        execute();
    }
}
