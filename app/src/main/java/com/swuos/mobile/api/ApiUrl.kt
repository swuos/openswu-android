package com.swuos.mobile.api

/**
 * 路由配置，链接+log
 * Created by wangyu on 2018/1/20.
 */
enum class ApiUrl(val url: String, val logId: Int) {
    TEST_URL("http://www.baidu.com", 100),
    LOGIN_URL("http://www.baidu.com", 101),
    LOGOUT_URL("www.baidu.com", 102)
}