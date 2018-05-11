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
    GET_AC_PROFILE("/ac/profile", 1031);
    override fun getRoute(): String = r
    override fun getLogId(): Int = l
}