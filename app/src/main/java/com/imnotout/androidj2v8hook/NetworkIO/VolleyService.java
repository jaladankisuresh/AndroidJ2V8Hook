package com.imnotout.androidj2v8hook.NetworkIO;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.net.CookieHandler;
import java.net.CookieManager;

public class VolleyService {
    private static VolleyService volleyService;
    //private RequestQueue mHttpsRequestQueue;
    private RequestQueue mHttpRequestQueue;
    private static Context mCtx;

    private VolleyService(Context context) {
        mCtx = context;
    }

    public static synchronized VolleyService getInstance(Context context) {
        if (volleyService == null) {
            volleyService = new VolleyService(context);
        }
        return volleyService;
    }

//    public RequestQueue getHttpsRequestQueue() {
//        if (mHttpsRequestQueue == null) {
//            CookieHandler.setDefault(new CookieManager());
//            // getApplicationContext() is key, it keeps you from leaking the
//            // Activity or BroadcastReceiver if someone passes one in.
//            HurlStack stack = HttpsConnectionManager.getInstance();
//            mHttpsRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(), stack);
//        }
//        return mHttpsRequestQueue;
//    }

    public RequestQueue getHttpRequestQueue() {
        if (mHttpRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mHttpRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mHttpRequestQueue;
    }
}
