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

import io.reactivex.Single;

public class LauncherVM
        implements IViewModel<AppBaseModels.LauncherBase>, JavaVoidCallback {
    private AppBaseModels.LauncherBase launcher;
    private V8Object jsVM;
    private static final String LOG_APP_TAG = AndroidApplication.getAppTag();

    public LauncherVM() {
        //launcherObj = new AppModels.Launcher();
        V8 runtime = AndroidApplication.getAppJsRuntime();
        MemoryManager mReferenceManager = new MemoryManager(runtime);
        V8Object iAmApp = runtime.getObject("iAmApp");
        iAmApp.registerJavaMethod(this, "getLauncherVMCallback");
        V8Object getCallback = iAmApp.getObject("getLauncherVMCallback");
        V8Array getParams = new V8Array(runtime).push("launcherVM").push(getCallback);
        runtime.getObject("iAmApp").executeVoidFunction("getViewModel", getParams);
        mReferenceManager.release();
    }

    @Override
    public Single<AppBaseModels.LauncherBase> getDataObservable() {
        return null;
    }
    @Override
    public AppBaseModels.LauncherBase getData() {
        return launcher;
    }
    @Override
    public V8Object getJsVM() {
        return jsVM;
    }

    @Override
    public void invoke(V8Object v8Object, V8Array v8Array) {
        if(v8Array.getString(0) != null) {
            Log.e(LOG_APP_TAG, v8Array.getString(0));
            return;
        }
        final String modelJson = v8Array.getString(1);
        jsVM = v8Array.getObject(2);

        launcher = GsonInstanceBuilder.getGsonInstance().fromJson(modelJson, AppBaseModels.LauncherBase.class);
    }
}
