package com.imnotout.androidj2v8hook.ViewModels;

import android.util.Log;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;
import com.imnotout.androidj2v8hook.AndroidApplication;
import com.imnotout.androidj2v8hook.Models.AppBaseModels;
import com.imnotout.androidj2v8hook.NetworkIO.GsonInstanceBuilder;
import com.imnotout.androidj2v8hook.Utils.RxBus;

import io.reactivex.Single;

public class LauncherVM
        implements IViewModel<AppBaseModels.LauncherBase> {
    private V8Object jsVM;
    private MemoryManager mReferenceManager;
    private static final String LOG_APP_TAG = AndroidApplication.getAppTag();

    public LauncherVM() {
        V8 runtime = AndroidApplication.getAppJsRuntime();
        mReferenceManager = new MemoryManager(runtime);
        try {
            V8Object iAmApp = runtime.getObject("iAmApp");
            iAmApp.registerJavaMethod((V8Object v8Object, V8Array v8Array) -> {

                if(v8Array.getString(0) != null) {
                    Log.e(LOG_APP_TAG, v8Array.getString(0));
                    return;
                }
                final String modelJson = v8Array.getString(1);
                Log.i(LOG_APP_TAG, modelJson);
                jsVM = v8Array.getObject(2);

                AppBaseModels.LauncherBase model = GsonInstanceBuilder.getGsonInstance().fromJson(modelJson, AppBaseModels.LauncherBase.class);
                RxBus.publish(RxBus.MessageSubjectType.LAUNCHER_MODEL, model);
            }, "getLauncherVMCallback");
            V8Object getCallback = iAmApp.getObject("getLauncherVMCallback");
            V8Array getParams = new V8Array(runtime).push("launcherVM").push(getCallback);
            runtime.getObject("iAmApp").executeVoidFunction("getViewModel", getParams);

        } catch (Exception e) {
            Log.d(LOG_APP_TAG, Log.getStackTraceString(e));
        }
    }

    @Override
    public void onDestroy() {
        mReferenceManager.release();
    }
}
