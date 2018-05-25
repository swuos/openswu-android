package com.gallops.mobile.jmvclibrary.http;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.app.JApp;
import com.gallops.mobile.jmvclibrary.http.annotation.BodyCreator;
import com.gallops.mobile.jmvclibrary.http.annotation.RequestMethod;
import com.gallops.mobile.jmvclibrary.http.creator.FormBodyCreator;
import com.gallops.mobile.jmvclibrary.http.creator.BodyCreateAction;
import com.gallops.mobile.jmvclibrary.models.HttpModel;
import com.gallops.mobile.jmvclibrary.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * 网络请求基类
 * Created by wangyu on 2018/3/6.
 */

@BodyCreator(FormBodyCreator.class)
public abstract class BaseRequester<T> {

    private static final String TAG = "WEB";

    protected OnResultListener<T> listener;

    public BaseRequester(@NonNull OnResultListener<T> listener) {
        this.listener = listener;
    }

    private HttpResultParser httpResultParser = new HttpResultParser() {
        @Override
        public void onResult(int code, String content, String msg) {
            if (JApp.isDebug()) {
                String serverContent = content == null ? "null" : content.replace("%", "%%");
                String string = String.format(Locale.getDefault(), "response, logCode = %d, url = %s, code = %d, content = %s", setRoute().getLogId(), setReqUrl(), code, serverContent);
                Logger.i(TAG, string);
            }
            try {
                if (code == HTTP_OK) {
                    JSONObject jsonObject = new JSONObject(content);
                    BaseRequester.this.onResult(parseCode(jsonObject), parseResult(jsonObject), parseMessage(jsonObject));
                } else {
                    BaseRequester.this.onResult(code, null, "");
                }
            } catch (JSONException exception) {
                onError(exception);
            }
        }

        @Override
        public void onError(Exception e) {
            if (JApp.isDebug()) {//调试模式下打印web请求日志
                String string = String.format(Locale.getDefault(), "response, logCode = %d, url = %s, error = %s", setRoute().getLogId(), setReqUrl(), e);
                Logger.i(TAG, string);
            }
            BaseRequester.this.onError(e);
        }
    };

    public void execute(long delay) {
        getHttpModel().getHandler().postDelayed(this::execute, delay);
    }

    public void execute() {
        HttpModel httpModel = getHttpModel();
        httpModel.getExecutor().execute(() -> {
            OkHttpClient client = new OkHttpClient();
//            OkHttpClient.Builder cb=new OkHttpClient.Builder();
//            Proxy proxy=new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.77.42.221",8888));
//            cb.proxy(proxy);
//            client=cb.build();

            Map<String, Object> params = new HashMap<>();
            onPutParams(params);
            HttpMethod method = setMethod();
            RequestBody requestBody = null;
            String url = appendUrl(setReqUrl());
            switch (method) {
                case GET:
                    url = appendGetParams(url, params);
                    break;
                case POST:
                    requestBody = onBuildRequestBody(params);
                    break;
            }
            Request.Builder reqBuilder = new Request.Builder()
                    .url(url)
                    .method(setMethod().getMethod(), requestBody);
            preHandleRequest(reqBuilder);
            Request request = reqBuilder.build();
            if (JApp.isDebug()) {
                String string = String.format(Locale.getDefault(), "request, logCode = %d, url = %s", setRoute().getLogId(), setReqUrl() + "?" + logParams(params));
                Logger.i(TAG, string);
            }
            try {
                Response response = client.newCall(request).execute();
                if (response == null) {
                    httpModel.getHandler().post(() -> httpResultParser.onResult(ErrorCode.RESULT_BODY_EMPTY, null, ""));
                } else {
                    if (response.isSuccessful()) {
                        ResponseBody body = response.body();
                        String result = body != null ? body.string() : "";
                        httpModel.getHandler().post(() -> httpResultParser.onResult(response.code(), result, ""));
                    } else {
                        httpModel.getHandler().post(() -> httpResultParser.onResult(ErrorCode.RESULT_NET_ERROR, "", ""));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                httpModel.getHandler().post(() -> httpResultParser.onError(e));
            }
        });
    }

    @NonNull
    protected RequestBody onBuildRequestBody(Map<String, Object> params) {
        RequestBody body = null;
        Class<?> cls = this.getClass();
        while (cls != null && cls != Object.class) {
            BodyCreator creator = cls.getAnnotation(BodyCreator.class);
            if (creator == null) {
                cls = cls.getSuperclass();
            } else {
                try {
                    BodyCreateAction requestBodyCreator = creator.value().newInstance();
                    body = requestBodyCreator.onCreate(params);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
        //noinspection ConstantConditions
        return body;
    }

    private String appendGetParams(String url, Map<String, Object> params) {
        StringBuilder builder = new StringBuilder();
        for (String key : params.keySet()) {
            builder.append(key)
                    .append("=")
                    .append(params.get(key))
                    .append("&");
        }
        url = url + "?" + builder.toString();
        return url;
    }

    protected abstract void onPutParams(@NonNull Map<String, Object> params);

    /**
     * 获取要打印的请求参数
     *
     * @param params 表单
     * @return 拼接后的参数字符串
     */
    protected String logParams(Map<String, Object> params) {
        StringBuilder builder = new StringBuilder();
        for (String key : params.keySet()) {
            builder.append(key)
                    .append("=")
                    .append(params.get(key))
                    .append("&");
        }
        return builder.toString();
    }

    /**
     * request预处理
     *
     * @param reqBuilder reqBuilder
     */
    protected void preHandleRequest(@NonNull Request.Builder reqBuilder) {

    }

    /**
     * url拼接额外字段，一般在GET请求下会使用到
     *
     * @param url url
     * @return newUrl
     */
    protected String appendUrl(String url) {
        return url;
    }

    /**
     * 设置请求方式
     *
     * @return {@link HttpMethod})
     */
    @NonNull
    protected HttpMethod setMethod() {
        HttpMethod method = HttpMethod.GET;
        Class<?> cls = this.getClass();
        while (cls != null && cls != Object.class) {
            RequestMethod requestMethod = cls.getAnnotation(RequestMethod.class);
            if (requestMethod == null) {
                cls = cls.getSuperclass();
            } else {
                method = requestMethod.value();
                break;
            }
        }
        return method;
    }

    /**
     * 设置请求url
     *
     * @return url拼接路由得到的请求地址
     */
    protected String setReqUrl() {
        return getApi().getApiUrl() + setRoute().getRoute();
    }

    protected abstract ApiInterface getApi();

    /**
     * 设置请求路由
     *
     * @return {@link RouteInterface}
     */
    @NonNull
    protected abstract RouteInterface setRoute();

    /**
     * 统一解析请求的code码
     *
     * @param jsonObject jsonObject
     * @return code
     */
    protected int parseCode(JSONObject jsonObject) {
        return jsonObject.optBoolean("success") ? ErrorCode.RESULT_DATA_OK : ErrorCode.RESULT_FAILED;
    }

    /**
     * 统一解析请求的result
     *
     * @param jsonObject    jsonObject
     * @return  code
     */
    protected JSONObject parseResult(JSONObject jsonObject) {
        //这里做一下处理,因为服务器返回的只有 success字段来表示结果是否正确.
        return jsonObject.optJSONObject("result");
    }

    /**
     * 统一解析请求的message
     *
     * @param jsonObject jsonObject
     * @return msg
     */
    protected String parseMessage(JSONObject jsonObject) {
        return jsonObject.optString("message");
    }

    /**
     * 请求失败了
     *
     * @param exception 失败原因
     */
    protected void onError(Exception exception) {
        exception.printStackTrace();
        listener.onResult(OnResultListener.RESULT_NET_ERROR, null, exception.getMessage());
    }


    /**
     * 请求成功了  服务器已经返回结果
     *
     * @param code    请求成功的HTTP返回吗，，一般code等于200表示请求成功
     * @param content 从服务器获取到的数据
     */
    protected void onResult(int code, JSONObject content, String msg) throws JSONException {
        T data = null;
        if (code == OnResultListener.RESULT_DATA_OK) {
            data = onDumpData(content);
        }
        listener.onResult(code, data, msg);
    }

    protected abstract T onDumpData(@NonNull JSONObject jsonObject) throws JSONException;

    /**
     * 获取http执行环境管理器
     *
     * @return
     */
    protected HttpModel getHttpModel() {
        return JApp.getInstance().getModel(HttpModel.class);
    }
}
