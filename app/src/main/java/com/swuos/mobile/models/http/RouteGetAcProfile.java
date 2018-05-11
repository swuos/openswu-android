package com.swuos.mobile.models.http;

import com.gallops.mobile.jmvclibrary.http.RouteInterface;

public enum RouteGetAcProfile implements RouteInterface{
    GET_AC_PROFILE("/ac/profile",1031);
    private String route;
    private int logId;
     RouteGetAcProfile(String route, int logId) {
        this.route=route;
        this.logId=logId;
    }

    @Override
    public String getRoute() {
        return route;
    }

    @Override
    public int getLogId() {
        return logId;
    }
}
