package com.swuos.mobile.api;

import android.support.annotation.NonNull;

import com.swuos.mobile.app.App;
import com.swuos.mobile.entity.UserInfo;
import com.swuos.mobile.models.http.HttpModel;
import com.swuos.mobile.models.user.UserModel;
import com.swuos.mobile.utils.LoggerKt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * 网络请求基类
 * Created by wangyu on 2018/3/6.
 */

public abstract class BaseRequester {

    private static final String TAG = "WEB";

    private HttpResultParser httpResultParser = new HttpResultParser() {
        @Override
        public void onResult(int code, String content, String msg) {
            if (App.isDebug()) {
                String serverContent = content == null ? "null" : content.replace("%", "%%");
                String string = String.format(Locale.getDefault(), "response, logCode = %d, url = %s, code = %d, content = %s", setApiUrl().getLogId(), setApiUrl().getUrl(), code, serverContent);
                LoggerKt.lgI(TAG, string);
            }
            try {
                if (code == HTTP_OK) {
                    JSONObject jsonObject = new JSONObject(content);
                    BaseRequester.this.onResult(parseCode(jsonObject), jsonObject, parseMessage(jsonObject));
                } else {
                    BaseRequester.this.onResult(code, null, "");
                }
            } catch (JSONException exception) {
                onError(exception);
            }
        }

        @Override
        void onError(Exception e) {
            if (App.isDebug()) {//调试模式下打印web请求日志
                String string = String.format(Locale.getDefault(), "response, logCode = %d, url = %s, error = %s", setApiUrl().getLogId(), setApiUrl().getUrl(), e);
                LoggerKt.lgI(TAG, string);
            }
            BaseRequester.this.onError(e);
        }
    };

    public void execute() {
        HttpModel httpModel = getHttpModel();
        httpModel.getExecutor().execute(() -> {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder builder = new FormBody.Builder();
            UserInfo userInfo = getUserModel().getUserInfo();
            Request request = new Request.Builder()
                    .url(appendUrl(setApiUrl().getUrl()))
                    .method(setMethod().getMethod(), onPutParams(builder).build())
                    .addHeader("token", userInfo == null ? "" : userInfo.getToken())//已登录用户自动填充token
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response == null) {
                    httpResultParser.onResult(ErrorCode.RESULT_BODY_EMPTY, null, "");
                } else {
                    if (response.isSuccessful()) {
                        ResponseBody body = response.body();
                        String result = body != null ? body.string() : "";
                        httpResultParser.onResult(response.code(), result, "");
                    } else {
                        httpResultParser.onResult(ErrorCode.RESULT_NET_ERROR, "", "");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                httpResultParser.onError(e);
            }
        });
    }

    /**
     * url拼接额外字段，一般在GET请求下会使用到
     * @param url
     * @return
     */
    protected String appendUrl(String url) {
        return url;
    }

    /**
     * 设置请求方式
     * @return
     */
    @NonNull
    protected abstract HttpMethod setMethod();

    /**
     * 设置请求url
     * @return
     */
    @NonNull
    protected abstract ApiUrl setApiUrl();

    /**
     * 传入参数
     * @return
     */
    @NonNull
    protected abstract FormBody.Builder onPutParams(FormBody.Builder builder);

    /**
     * 统一解析请求的code码
     * @param jsonObject
     * @return
     */
    protected int parseCode(JSONObject jsonObject) {
        return jsonObject.optInt("code");
    }

    /**
     * 统一解析请求的message
     * @param jsonObject
     * @return
     */
    protected String parseMessage(JSONObject jsonObject) {
        return jsonObject.optString("msg");
    }

    /**
     * 请求成功了  服务器已经返回结果
     *
     * @param code    请求成功的HTTP返回吗，，一般code等于200表示请求成功
     * @param content 从服务器获取到的数据
     */
    protected abstract void onResult(int code, JSONObject content, String msg) throws JSONException;

    /**
     * 请求失败了
     *
     * @param exception 失败原因
     */
    protected abstract void onError(Exception exception);

    /**
     * 获取http执行环境管理器
      * @return
     */
    protected HttpModel getHttpModel() {
        return App.getInstance().getModel(HttpModel.class);
    }

    /**
     * 获取用户管理器，主要用于获取token字段，未登录的用户token为“”
     * @return
     */
    protected UserModel getUserModel() {
        return App.getInstance().getModel(UserModel.class);
    }
}
