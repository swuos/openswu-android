package com.swuos.mobile.models.http;

import com.gallops.mobile.jmvclibrary.http.RouteInterface;

public enum RouteRegister implements RouteInterface{
    ROUTE_REGISTER("/ac/register",101);
    private String route;
    private int logId;
     RouteRegister(String route, int logId) {
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
