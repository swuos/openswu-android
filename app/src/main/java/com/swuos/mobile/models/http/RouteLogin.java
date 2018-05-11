package com.swuos.mobile.models.http;

import com.gallops.mobile.jmvclibrary.http.RouteInterface;

public enum RouteLogin implements RouteInterface{
    ROUTE_LOGIN("/ac/login",100);
    private String route;
    private int logId;
     RouteLogin(String route, int logId) {
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
