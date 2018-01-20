package com.swuos.mobile.ui;

import android.os.Bundle;

import com.swuos.mobile.R;
import com.swuos.mobile.api.ApiUrl;
import com.swuos.mobile.api.HttpMethod;
import com.swuos.mobile.api.HttpRequester;
import com.swuos.mobile.api.OnHttpResultListener;
import com.swuos.mobile.app.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showProgressDialog();
        new HttpRequester.Builder(ApiUrl.TEST_URL)
                .body(null)
                .method(HttpMethod.GET)
                .build()
                .execute(new OnHttpResultListener<String>() {
                    @Override
                    public void onResult(int code, String s) {
                        dismissProgressDialog();
                        if (code == RESULT_DATA_OK) {
                            showToast(s);
                        } else {
                            showToast("error");
                        }
                    }
                });
    }
}
