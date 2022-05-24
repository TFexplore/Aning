package com.tf.aning;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyOkhttp {

    OkHttpClient okHttpClient;
    Request request;
    RequestBody requestBody;
    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    HttpUrl.Builder builder;
    Call call;
    public static MyOkhttp INSTANCE = new MyOkhttp();

    public MyOkhttp() {
        this.okHttpClient = new OkHttpClient();
    }

    public static MyOkhttp getINSTANCE() {
        return INSTANCE;
    }

    public Call getCall() {
        return call;
    }

    public void login(String body){

        RequestBody requestBody = RequestBody.create(JSON, body);//设置请求body
        HttpUrl.Builder builder = HttpUrl.parse("http://42.193.48.88:8080/aning/login").newBuilder();//url
        Request request = new Request.Builder()
                .url(builder.build())
                .post(requestBody)
                .build();
        call = okHttpClient.newCall(request);//发起请求

    }
    public void register(String body){
        RequestBody requestBody = RequestBody.create(JSON, body);//设置请求body
        HttpUrl.Builder builder = HttpUrl.parse("http://42.193.48.88:8080/aning/register").newBuilder();//url
        Request request = new Request.Builder()
                .url(builder.build())
                .post(requestBody)
                .build();
        call = okHttpClient.newCall(request);//发起请求

    }

}