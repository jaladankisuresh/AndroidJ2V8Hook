package com.imnotout.androidj2v8hook.ViewModels;

import android.util.Log;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;
import com.imnotout.androidj2v8hook.AndroidApplication;
import com.imnotout.androidj2v8hook.Models.AppBaseModels;
import com.imnotout.androidj2v8hook.NetworkIO.GsonInstanceBuilder;

import io.reactivex.Single;

public class MainVM
        implements IViewModel<AppBaseModels.RegistryBase> {
    private Single<AppBaseModels.RegistryBase> registryObservable;
    private AppBaseModels.RegistryBase registry;
    private V8Object jsVM;
    private static final String LOG_APP_TAG = AndroidApplication.getAppTag();

    public MainVM() {
        V8 runtime = AndroidApplication.getAppJsRuntime();

        registryObservable = Single.create(emitter -> {
            MemoryManager mReferenceManager = new MemoryManager(runtime);

            try {
                V8Object iAmApp = runtime.getObject("iAmApp");
                iAmApp.registerJavaMethod((V8Object v8Object, V8Array v8Array) -> {
                    if(v8Array.get(0) != null) {
                        Log.e(LOG_APP_TAG, v8Array.getString(0));
                        return;
                    }
                    final String modelJson = v8Array.getString(1);
                    jsVM = v8Array.getObject(2);

                    registry = GsonInstanceBuilder.getGsonInstance().fromJson(modelJson, AppBaseModels.RegistryBase.class);
                    emitter.onSuccess(registry);
                }, "getMainVMCallback");
                V8Object getCallback = iAmApp.getObject("getMainVMCallback");
                V8Array getParams = new V8Array(runtime).push("mainVM").push(getCallback);
                runtime.getObject("iAmApp").executeVoidFunction("getViewModel", getParams);

            } catch (Exception e) {
                emitter.onError(e);
            }
            finally {
                mReferenceManager.release();
            }
        });

    }
    @Override
    public Single<AppBaseModels.RegistryBase> getDataObservable() {
        return registryObservable;
    }
    @Override
    public AppBaseModels.RegistryBase getData() {
        return registry;
    }
    @Override
    public V8Object getJsVM() {
        return jsVM;
    }

}

