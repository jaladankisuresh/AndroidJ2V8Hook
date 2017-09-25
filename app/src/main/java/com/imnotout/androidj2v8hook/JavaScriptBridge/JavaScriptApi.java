package com.imnotout.androidj2v8hook.JavaScriptBridge;

import android.content.Context;
import android.util.Log;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;
import com.imnotout.androidj2v8hook.AndroidApplication;
import com.imnotout.androidj2v8hook.NetworkIO.IWebApi;
import com.imnotout.androidj2v8hook.NetworkIO.WebServiceClient;

public class JavaScriptApi {
    private static JavaScriptApi instance;
    private final String LOG_APP_TAG = AndroidApplication.getAppTag();
    //HashMap<String,String> httpRequestQueue;
    private JavaScriptApi() {
        //httpRequestQueue = new HashMap<String,String>();
    }

    public static JavaScriptApi getInstance() {
        if(instance == null) {
            instance = new JavaScriptApi();
        }
        return instance;
    }
    public void makeHttpRequest(final V8 runtime, final V8Array parameters) {
        String url, data;
        final String token = parameters.getString(0);
        //Mock of JavaScript function for reference only
        //httpGet : function(token, url, data) {
        //dump interface that would be overriden in the target environment (browser, android, iOS)
        //}
        IWebApi.Listener webApiListener = new IWebApi.Listener() {

            @Override
            public void onSuccessResponse(String dataJSON) {
                Log.i(LOG_APP_TAG, dataJSON);

                MemoryManager mReferenceManager = new MemoryManager(runtime);
                final V8Object iAmAjax = runtime.getObject("iAmApp").getObject("iAmAjax");
                V8Array onResponseParams = new V8Array(runtime).push(token).push(dataJSON);
                iAmAjax.executeVoidFunction("onSuccessResponse", onResponseParams);
                mReferenceManager.release();
            }
            @Override
            public void onErrorResponse(String errMessage) {
                Log.e(LOG_APP_TAG, errMessage);

                MemoryManager mReferenceManager = new MemoryManager(runtime);
                final V8Object iAmAjax = runtime.getObject("iAmApp").getObject("iAmAjax");
                V8Array onResponseParams = new V8Array(runtime).push(token).push(errMessage);
                iAmAjax.executeVoidFunction("onErrorResponse", onResponseParams);
                mReferenceManager.release();
            }
            @Override
            public Context getListenerContext() {
                return AndroidApplication.getAppContext();
            }
        };
        WebServiceClient proxyService = new WebServiceClient(webApiListener);
        switch(parameters.length()) {
            case 2:
                url  = parameters.getString(1);
                proxyService.makeGet(url);
                break;
            case 3:
                url  = parameters.getString(1);
                data  = parameters.getString(2);
                proxyService.makePost(url,data);
                break;
            default:
                return;
        }
    }

    public void prepareIORequestJsFunc(final V8 runtime) {
        MemoryManager mReferenceManager = new MemoryManager(runtime);
        JavaVoidCallback ioRequestJavaCallback = new JavaVoidCallback() {
            public void invoke(final V8Object receiver, final V8Array parameters) {
                JavaScriptApi.this.makeHttpRequest(runtime, parameters);
            }
        };
        final V8Object iAmAjax = runtime.getObject("iAmApp").getObject("iAmAjax");
        iAmAjax.registerJavaMethod(ioRequestJavaCallback, "httpGet");
        mReferenceManager.release();
    }
}
