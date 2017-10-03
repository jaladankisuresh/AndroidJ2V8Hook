package com.imnotout.androidj2v8hook;

import android.app.Application;
import android.content.Context;

import com.eclipsesource.v8.V8;
import com.imnotout.androidj2v8hook.JavaScriptBridge.JavaScriptApi;
import com.imnotout.androidj2v8hook.JavaScriptBridge.JsUtil;
import com.imnotout.androidj2v8hook.Models.AppBaseModels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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
        try {

            JsUtil.getInstance().registerUtilFunctions(runtime);  // Init runtime with console.log and console.error fn()
            runtime.executeVoidScript(convertStreamToString(getAssets().open("js/app.bundle.js")));
            JavaScriptApi apiInstance = JavaScriptApi.getInstance();
            apiInstance.prepareIORequestJsFunc(runtime);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

    public static Object pathParser(Object parseObj, String path) throws Exception {
        int index = path.indexOf('/', 1);
        String propName = index > -1 ? path.substring(1, index) : path.substring(1);
        Object childObj;
        if(isInteger(propName)) {
            int arrPosition = Integer.parseInt(propName);
            String getterName = "get";
            Method getterFn = parseObj.getClass().getMethod(getterName, int.class);
            childObj = getterFn.invoke(parseObj, arrPosition);
        }
        else {
            String getterName = "get" + Character.toUpperCase(propName.charAt(0)) + propName.substring(1);
            Method getterFn = parseObj.getClass().getMethod(getterName);
            childObj = getterFn.invoke(parseObj);
        }
        if(index == -1) {
            return childObj;
        }
        return pathParser(childObj, path.substring(index));
    }

    public static void copyObject(Object targetObj, Object sourceObj) throws Exception {
        //Code implemented based on reference from
        // https://stackoverflow.com/questions/2989560/how-to-get-the-fields-in-an-object-via-reflection

//        for (Field field : sourceObj.getClass().getDeclaredFields()) {
//            field.setAccessible(true);
//            Object value = field.get(sourceObj);
//            field.getName();
//            field.set(targetObj, value);
//        }

        for (Method method : sourceObj.getClass().getMethods()) {
            if ( Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && method.getDeclaringClass() != Object.class
                    && (method.getName().startsWith("get") || method.getName().startsWith("is")) ) {
                Object value = method.invoke(sourceObj);
                Method targetMethod = targetObj.getClass().getMethod(method.getName());
                if( value.equals(targetMethod.invoke(targetObj)) ){
                    continue;
                }
                String propertyName = method.getName().substring(3);
                String setMethodName = "set" + propertyName;
                try {
                    Method setterFn = targetObj.getClass().getMethod(setMethodName, method.getReturnType());
                    setterFn.invoke(targetObj, value);
                } catch (NoSuchMethodException e) {
                    // Do nothing and continue
                }
            }
        }
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
