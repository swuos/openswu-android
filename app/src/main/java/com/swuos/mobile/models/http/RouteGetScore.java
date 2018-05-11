package com.swuos.mobile.models.http;

import com.gallops.mobile.jmvclibrary.http.RouteInterface;

public enum RouteGetScore implements RouteInterface{
    ROUTE_GET_SCORE("/api/grade/search",1030);
    private String route;
    private int logId;
     RouteGetScore(String route, int logId) {
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
