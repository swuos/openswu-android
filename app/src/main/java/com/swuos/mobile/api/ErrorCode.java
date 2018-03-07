package com.swuos.mobile.api;

/**
 * 错误码定义
 * Created by wangyu on 2017/12/7.
 */

public interface ErrorCode {
    int RESULT_DATA_OK = 200;//请求成功
    int RESULT_NET_ERROR = -1;//网络错误
    int RESULT_JSON_PARSE_EXCEPTION = -2;//json解析错误
    int RESULT_IO_EXCEPTION = -3;//io异常
    int RESULT_BODY_EMPTY = -4;//请求body为空
    int RESULT_NO_DATA = 204;//无数据
    int RESULT_TOKEN_ERROR = 401;//token错误
    int RESULT_SERVER_ERROR = 500;//服务器错误
}
