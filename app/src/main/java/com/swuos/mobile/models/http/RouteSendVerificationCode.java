package com.swuos.mobile.models.http;

import com.gallops.mobile.jmvclibrary.http.RouteInterface;

public enum RouteSendVerificationCode implements RouteInterface{
    ROUTE_SEND_VERIFICATION_CODE("/ac/sendVerificationCode",102);
    private String route;
    private int logId;
     RouteSendVerificationCode(String route, int logId) {
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
