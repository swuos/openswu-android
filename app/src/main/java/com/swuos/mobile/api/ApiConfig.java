package com.swuos.mobile.api;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;

/**
 * api配置
 * Created by wangyu on 2018/5/11.
 */

public class ApiConfig {
    public static String AC_HOST = "http://ac.xenoeye.org";
    // TODO: 2018/5/19 端口10080修改为3000
    public static String FREEGATTY_HOST = "http://118.25.5.239:3000";
    public static String VERIFICATION_HOST = "http://119.27.174.160";

    public static class AcHostApi extends ApiConfig implements ApiInterface {

        @Override
        public String getApiUrl() {
            return AC_HOST;
        }
    }

    public static class FreegattyHostApi extends ApiConfig implements ApiInterface {

        @Override
        public String getApiUrl() {
            return FREEGATTY_HOST;
        }
    }

    public static class VerificationHostApi extends ApiConfig implements ApiInterface {
        @Override
        public String getApiUrl() {
            return VERIFICATION_HOST;
        }
    }
}
