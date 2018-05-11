package com.swuos.mobile.models.http;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;

public class ApiHostUrl implements ApiInterface {

    @Override
    public String getApiUrl() {
        return ApiUrl.AC_HOST.getUrl();
    }
}
