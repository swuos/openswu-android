package com.swuos.mobile.api

import com.gallops.mobile.jmvclibrary.http.RouteInterface

/**
 * 路由统一配置
 * Created by wangyu on 2018/5/11.
 */
enum class RouteEnum(private val r: String, private val l: Int) : RouteInterface {
    ROUTE_LOGIN("/ac/login", 100),
    ROUTE_REGISTER("/ac/register", 101),
    ROUTE_SEND_VERIFICATION_CODE("/ac/sendVerificationCode", 102),
    ROUTE_GET_SCHEDULE("/api/schedule/search", 103),
    ROUTE_BIND("/ac/bindSwuac", 104),
    ROUTE_GET_SCORE("/api/grade/search", 1030),
    ROUTE_GET_CALENDAR("/api/calendar/search", 1032),
    GET_AC_PROFILE("/ac/profile", 1031),
    GET_LIB_POPULAR("/mock/17/api/lib/popular", 1051),
    GET_LIB_BOOKSHELF("/mock/17/api/lib/bookshelf", 1052),
    GET_LIB_SEARCH("/mock/17/api/lib/search", 1053);
    override fun getRoute(): String = r
    override fun getLogId(): Int = l
}