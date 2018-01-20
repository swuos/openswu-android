package com.swuos.mobile.api;

import android.support.annotation.NonNull;

import com.swuos.mobile.app.App;
import com.swuos.mobile.models.http.HttpModel;
import com.swuos.mobile.utils.LoggerKt;
import com.swuos.mobile.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求入口，主要封装日志打印，方便查看
 * Created by wangyu on 2018/1/20.
 */

public class HttpRequester {
    private ApiUrl apiUrl;
    private RequestBody requestBody;
    private HttpMethod method;
    private static final String TAG = "WEB";
    private HttpModel httpModel = App.getInstance().getModel(HttpModel.class);

    public ApiUrl getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(ApiUrl apiUrl) {
        this.apiUrl = apiUrl;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public <Data> void execute(@NonNull final OnHttpResultListener<Data> listener) {
        httpModel.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                @SuppressWarnings("unchecked")
                Class<Data> dataCls = (Class<Data>) ((ParameterizedType) listener.getClass()
                        .getGenericSuperclass()).getActualTypeArguments()[0];
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(apiUrl.getUrl())
                        .method(method.getMethod(), requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (response == null) {
                        LoggerKt.lgD(TAG, String.format(Locale.getDefault(), "code: %d, result: %s", apiUrl.getLogId(), "RESULT_BODY_EMPTY"));
                        postResult(listener, ErrorCode.RESULT_BODY_EMPTY, null);
                    } else {
                        if (response.isSuccessful()) {
                            String result = response.body().string();
                            LoggerKt.lgD(TAG, String.format(Locale.getDefault(), "code: %d, result: %s", apiUrl.getLogId(), result));
                            if (dataCls == String.class) {
                                //noinspection unchecked
                                postResult(listener, ErrorCode.RESULT_DATA_OK, (Data) result);
                            } else {
                                JSONObject jsonObject = new JSONObject(result);
                                Data data = JsonUtil.toObject(jsonObject, dataCls);
                                postResult(listener, ErrorCode.RESULT_DATA_OK, data);
                            }
                        } else {
                            LoggerKt.lgD(TAG, String.format(Locale.getDefault(), "code: %d, result: %s", apiUrl.getLogId(), "RESULT_NET_ERROR"));
                            postResult(listener, ErrorCode.RESULT_NET_ERROR, null);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    LoggerKt.lgD(TAG, String.format(Locale.getDefault(), "code: %d, result: %s", apiUrl.getLogId(), "RESULT_IO_EXCEPTION"));
                    postResult(listener, ErrorCode.RESULT_IO_EXCEPTION, null);
                } catch (JSONException e) {
                    e.printStackTrace();
                    LoggerKt.lgD(TAG, String.format(Locale.getDefault(), "code: %d, result: %s", apiUrl.getLogId(), "RESULT_JSON_PARSE_EXCEPTION"));
                    postResult(listener, ErrorCode.RESULT_JSON_PARSE_EXCEPTION, null);
                }
            }
        });
    }

    private <Data> void postResult(@NonNull final OnHttpResultListener<Data> listener, final int code, final Data data) {
        httpModel.getHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.onResult(code, data);
            }
        });
    }

    public static class Builder {
        private ApiUrl apiUrl;
        private RequestBody requestBody;
        private HttpMethod method = HttpMethod.GET;

        public Builder(ApiUrl apiUrl) {
            this.apiUrl = apiUrl;
        }

        public Builder body(RequestBody requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public Builder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public HttpRequester build() {
            HttpRequester requester = new HttpRequester();
            requester.setApiUrl(apiUrl);
            requester.setRequestBody(requestBody);
            requester.setMethod(method);
            return requester;
        }
    }
}
