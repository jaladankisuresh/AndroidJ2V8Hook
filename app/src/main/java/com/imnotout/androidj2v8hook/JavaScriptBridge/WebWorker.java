package com.imnotout.androidj2v8hook.JavaScriptBridge;

import android.util.Log;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.V8Executor;

public class WebWorker {

    public void start(V8Object worker, String... s) {
        String script = (String) s[0];
        V8Executor executor = new V8Executor(script, true, "messageHandler") {
            @Override
            protected void setup(V8 runtime) {
                runtime.registerJavaMethod(WebWorker.this, "print", "print", new Class<?>[] { String.class });
                //configureWorker(runtime);
            }
        };
        worker.getRuntime().registerV8Executor(worker, executor);
        executor.start();
    }

    public void terminate(V8Object worker, Object... s) {
        V8Executor executor = worker.getRuntime().removeExecutor(worker);
        if (executor != null) {
            executor.shutdown();
        }
    }

    public void postMessage(V8Object worker, String... s) {
        V8Executor executor = worker.getRuntime().getExecutor(worker);
        if (executor != null) {
            executor.postMessage(s);
        }
    }

    public void print(String s) {
        Log.i("AndroidJ2V8Hook", "String : " + s);
    }

    public void start() throws InterruptedException {
        V8Executor mainExecutor = new V8Executor(
                "var w = new Worker('messageHandler = function(e) { print(e[0]); }');\n"
                        + "w.postMessage('message to send.');" + "w.postMessage('another message to send.');"
                        + "w.terminate();\n") {
            @Override
            protected void setup(V8 runtime) {
                configureWorker(runtime);
            }
        };
        mainExecutor.start();
        mainExecutor.join();
    }

    private void configureWorker(V8 runtime) {
        runtime.registerJavaMethod(this, "start", "Worker", new Class<?>[] { V8Object.class, String[].class }, true);
        V8Object worker = runtime.getObject("Worker");
        V8Object prototype = runtime.executeObjectScript("Worker.prototype");
        prototype.registerJavaMethod(this, "terminate", "terminate", new Class<?>[] { V8Object.class, Object[].class },
                true);
        prototype.registerJavaMethod(this, "postMessage", "postMessage",
                new Class<?>[] { V8Object.class, String[].class }, true);
        //runtime.registerJavaMethod(WebWorker.this, "print", "print", new Class<?>[] { String.class });
        worker.setPrototype(prototype);
        worker.release();
        prototype.release();
    }
}