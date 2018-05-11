package com.swuos.mobile.models.http;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;

public class VerificationApiHostUrl implements ApiInterface {

    @Override
    public String getApiUrl() {
        return ApiUrl.VERIFICATION_HOST.getUrl();
    }
}
