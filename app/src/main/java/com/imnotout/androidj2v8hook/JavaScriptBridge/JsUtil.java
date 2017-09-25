package com.imnotout.androidj2v8hook.JavaScriptBridge;

import android.util.Log;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import com.imnotout.androidj2v8hook.AndroidApplication;

public class JsUtil {

    private static JsUtil instance;
    private static final String LOG_APP_TAG = AndroidApplication.getAppTag();

    private JsUtil() {
    }

    public static JsUtil getInstance() {
        if(instance == null) {
            instance = new JsUtil();
        }
        return instance;
    }

    public void registerUtilFunctions(V8 runtime) {
        Console console = new Console();
        V8Object v8Console = new V8Object(runtime);
        runtime.add("jConsole", v8Console);
        v8Console.registerJavaMethod(console, "log", "log", new Class<?>[] { String.class });
        v8Console.registerJavaMethod(console, "error", "error", new Class<?>[] { String.class });
        v8Console.release();
    }

    public static class Console {
        public void log(final String message) {
            Log.i(LOG_APP_TAG, "[LOG] " + message);
        }
        public void error(final String message) {
            Log.e(LOG_APP_TAG, "[ERROR] " + message);
        }
    }
}
