package com.swuos.mobile.models.http;

import com.gallops.mobile.jmvclibrary.http.RouteInterface;

public enum RouteGetSchedule implements RouteInterface{
    ROUTE_GET_SCHEDULE("/api/schedule/search",103);
    private String route;
    private int logId;
     RouteGetSchedule(String route, int logId) {
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
