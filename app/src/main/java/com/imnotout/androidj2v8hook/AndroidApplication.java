package com.imnotout.androidj2v8hook;

import android.app.Application;
import android.content.Context;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.utils.MemoryManager;
import com.imnotout.androidj2v8hook.JavaScriptBridge.JavaScriptApi;
import com.imnotout.androidj2v8hook.JavaScriptBridge.JsUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AndroidApplication extends Application {

    private static Context context;
    private static V8 jsRuntime;
    private static final String LOG_APP_TAG = "androidj2v8hook";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //Create JavaScript Runtime
        jsRuntime = V8.createV8Runtime();
        initJSRuntime(jsRuntime);
    }

    @Override
    public void onTerminate() {
        //Release JavaScript Runtime Native Handle (C++ Pointer)
        jsRuntime.release();
        super.onTerminate();
    }
    private void initJSRuntime(V8 runtime){
        MemoryManager mReferenceManager = new MemoryManager(runtime);

        try {

            JsUtil.getInstance().registerUtilFunctions(runtime);  // Init runtime with console.log and console.error fn()
            runtime.executeVoidScript(convertStreamToString(getAssets().open("js/app.bundle.js")));
            JavaScriptApi apiInstance = JavaScriptApi.getInstance();
            apiInstance.prepareIORequestJsFunc(runtime);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        mReferenceManager.release();
    }
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    public static Context getAppContext() {
        return context;
    }

    public static V8 getAppJsRuntime() {
        return jsRuntime;
    }

    public static String getAppTag() {
        return LOG_APP_TAG;
    }
}
