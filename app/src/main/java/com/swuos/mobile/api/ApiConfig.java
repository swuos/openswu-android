package com.swuos.mobile.api;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;

/**
 * api配置
 * Created by wangyu on 2018/5/11.
 */

public class ApiConfig {

    /** api服务切换 当前用于切换域名和ip访问 */
    private static ServerConfig server = ServerConfig.TEST;

    private static String AC_HOST;
    private static String FREEGATTY_HOST;
    private static String VERIFICATION_HOST;

    static {
        switch (server) {
            case OFFICIAL:
                AC_HOST = "http://ac.xenoeye.org";
                FREEGATTY_HOST = "http://118.25.5.239:3000";
                VERIFICATION_HOST = "http://119.27.174.160";
                break;
            case TEST:
                AC_HOST = "http://119.27.174.160";
                FREEGATTY_HOST = "http://118.25.5.239:10080";
                VERIFICATION_HOST = "http://119.27.174.160";
                break;
        }
    }

    public enum ServerConfig {
        OFFICIAL("正式服"),//正式服
        TEST("测试服");//本机
        public String value;

        ServerConfig(String value) {
            this.value = value;
        }

        public static ServerConfig build(String value) {
            switch (value) {
                case "正式服":
                    return OFFICIAL;
                case "测试服":
                    return TEST;
                default:
                    return TEST;
            }
        }

    }

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
