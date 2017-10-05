package com.example.marco.faq.web;

import com.example.marco.faq.category.CategoryActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Marco on 05.10.2017.
 */

public class WebCommunication {

    public void performRequest(String url, Callback callback){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);

        call.enqueue(callback);
    }
}
