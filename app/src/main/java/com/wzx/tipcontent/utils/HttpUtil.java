package com.wzx.tipcontent.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.ByteArrayEntity;

public class HttpUtil {
    private static Context context;
//    private static AsyncHttpClient client;
    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

    	static {
		client.setTimeout(11000);

	}


    public static void get(String urlString, AsyncHttpResponseHandler res) {
        client.get(urlString, res);
    }

    public static void get(String urlString, RequestParams params, AsyncHttpResponseHandler res, Context context) {
        client.get(urlString, params, res);
    }


    public static void get(String urlString, RequestParams params, AsyncHttpResponseHandler res) {
        client.get(urlString, params, res);
    }

    public static void get(String urlString, JsonHttpResponseHandler res) {
        client.get(urlString, res);
    }

    public static void get(String urlString, RequestParams params, JsonHttpResponseHandler res) {
        client.get(urlString, params, res);
    }

    public static void get(String uString, BinaryHttpResponseHandler bHandler) {
        client.get(uString, bHandler);
    }

    public static AsyncHttpClient getClient() {
        return client;
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        // TODO Auto-generated method stub
        client.post(url, params, asyncHttpResponseHandler);

    }


    public  static void post(Context context,String url,ByteArrayEntity entity,String type,AsyncHttpResponseHandler asyncHttpResponseHandler){
         client.post(context,url,entity,type,asyncHttpResponseHandler);
    }

}