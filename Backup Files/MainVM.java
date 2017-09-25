package com.imnotout.androidj2v8hook.ViewModels;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;
import com.imnotout.androidj2v8hook.AndroidApplication;
import com.imnotout.androidj2v8hook.Models.AppModels;
import com.imnotout.androidj2v8hook.NetworkIO.GsonInstanceBuilder;

import io.reactivex.Single;

public class MainVM
        implements IViewModel<AppModels.Registry> {
    private Single<AppModels.Registry> registryObservable;
    private AppModels.Registry registry;
    private V8Object jsVM;

    public MainVM() {
        V8 runtime = AndroidApplication.getAppJsRuntime();

        registryObservable = Single.create(emitter -> {
            try {
                MemoryManager mReferenceManager = new MemoryManager(runtime);
                V8Object iAmApp = runtime.getObject("iAmApp");
                iAmApp.registerJavaMethod((V8Object v8Object, V8Array v8Array) -> {
                    final String modelJson = v8Array.getString(0);
                    jsVM = v8Array.getObject(1);

                    registry = GsonInstanceBuilder.getGsonInstance().fromJson(modelJson, AppModels.Registry.class);
                    emitter.onSuccess(registry);
                }, "getMainVMCallback");
                V8Object getCallback = iAmApp.getObject("getMainVMCallback");
                V8Array getParams = new V8Array(runtime).push("mainVM").push(getCallback);
                runtime.getObject("iAmApp").executeVoidFunction("getViewModel", getParams);

                mReferenceManager.release();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

    }
    @Override
    public Single<AppModels.Registry> getDataObservable() {
        return registryObservable;
    }
    @Override
    public AppModels.Registry getData() {
        return registry;
    }
    @Override
    public V8Object getJsVM() {
        return jsVM;
    }

}

