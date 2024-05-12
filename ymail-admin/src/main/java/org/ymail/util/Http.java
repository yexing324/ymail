package org.ymail.util;


import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Http {
    OkHttpClient okhttp = new OkHttpClient();

    public Call getCode(String uri) {
        Request request = new Request.Builder()
                .url(uri)
                .get()
                .build();
        return okhttp.newCall(request);
    }
}
