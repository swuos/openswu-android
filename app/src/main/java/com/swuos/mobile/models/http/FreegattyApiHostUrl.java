package com.swuos.mobile.models.http;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;

public class FreegattyApiHostUrl implements ApiInterface {

    @Override
    public String getApiUrl() {
        return ApiUrl.FREEGATTY_HOST.getUrl();
    }
}
