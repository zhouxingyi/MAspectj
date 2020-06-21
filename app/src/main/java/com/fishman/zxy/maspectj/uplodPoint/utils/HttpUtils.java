package com.fishman.zxy.maspectj.uplodPoint.utils;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    private String url;
    private static OkHttpClient mClientInstance;
    private Handler mHandler;
    private static volatile HttpUtils insance;
    private HttpUtils(){
        mClientInstance = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }
    public static HttpUtils getInstance(){
        if(insance==null){
           synchronized (HttpUtils.class){
               if(insance==null){
                   insance=new HttpUtils ();
               }
           }
        }
       return insance;
    }

    /**
     * 封装一个request方法，不管post或者get方法中都会用到
     */
    public void request(final Request request, final HttpCallback callback) {        //在请求之前所做的事，比如弹出对话框等
        mClientInstance.newCall (request).enqueue (new Callback () {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onfailure (e);
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(response!=null&&response.isSuccessful ()){
                            callback.onSuccess (response);
                        }else{
                            callback.onfailure (new IOException ("解析失败"));
                        }

                    }
                });
            }
        });
    }
    /**
     * 对外公开的get方法
     *
     * @param url
     * @param callback
     */
    public void get(String url, HttpCallback callback) {
        Request request = buildRequest(url, null, HttpMethodType.GET);
        request(request, callback);
    }    /**
     * 对外公开的post方法
     *
     * @param url
     * @param requstBody
     * @param callback
     */
    public <T> void post(String url, T requstBody, HttpCallback callback) {
        Request request = buildRequest(url, requstBody, HttpMethodType.POST);
        request(request, callback);
    }    /**
     * 构建请求对象
     *
     * @param url
     * @param requstBody
     * @param type
     * @return
     */
    private <T> Request buildRequest(String url, T requstBody, HttpMethodType type) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (type == HttpMethodType.GET) {
            builder.get();
        } else if (type == HttpMethodType.POST) {
            String data = JSON.toJSONString (requstBody);
            MediaType josn=MediaType.parse("application/json; charset=utf-8");
            RequestBody body=RequestBody.create(josn,data) ;
            builder.post(body);
        }
        return builder.build();
    }
    /**
     * 这个枚举用于指明是哪一种提交方式
     */
    enum HttpMethodType {
        GET,
        POST
    }

}
