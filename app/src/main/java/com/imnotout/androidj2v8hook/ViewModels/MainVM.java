package com.imnotout.androidj2v8hook.ViewModels;

import android.util.Log;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;
import com.imnotout.androidj2v8hook.AndroidApplication;
import com.imnotout.androidj2v8hook.Models.AppBaseModels;
import com.imnotout.androidj2v8hook.NetworkIO.GsonInstanceBuilder;
import com.imnotout.androidj2v8hook.Utils.RxBus;

import io.reactivex.Single;

public class MainVM
        implements IViewModel<AppBaseModels.RegistryBase> {
//    private Single<AppBaseModels.RegistryBase> registryObservable;
//    private AppBaseModels.RegistryBase registry;
    private MemoryManager mReferenceManager;
    private V8Object jsVM;

    private V8 runtime = AndroidApplication.getAppJsRuntime();
    private static final String LOG_APP_TAG = AndroidApplication.getAppTag();

    public MainVM() {

//        registryObservable = Single.create(emitter -> {
//            MemoryManager mReferenceManager = new MemoryManager(runtime);
//
//            try {
//                V8Object iAmApp = runtime.getObject("iAmApp");
//                iAmApp.registerJavaMethod((V8Object v8Object, V8Array v8Array) -> {
//                    if(v8Array.get(0) != null) {
//                        Log.e(LOG_APP_TAG, v8Array.getString(0));
//                        return;
//                    }
//                    final String modelJson = v8Array.getString(1);
//                    jsVM = v8Array.getObject(2);
//
//                    registry = GsonInstanceBuilder.getGsonInstance().fromJson(modelJson, AppBaseModels.RegistryBase.class);
//                    emitter.onSuccess(registry);
//                }, "getMainVMCallback");
//                V8Object getCallback = iAmApp.getObject("getMainVMCallback");
//                V8Array getParams = new V8Array(runtime).push("mainVM").push(getCallback);
//                runtime.getObject("iAmApp").executeVoidFunction("getViewModel", getParams);
//
//            } catch (Exception e) {
//                emitter.onError(e);
//            }
//            finally {
//                mReferenceManager.release();
//            }
//        });

        mReferenceManager = new MemoryManager(runtime);
        try {
            V8Object iAmApp = runtime.getObject("iAmApp");
            iAmApp.registerJavaMethod((V8Object v8Object, V8Array v8Array) -> {
                if(v8Array.get(0) != null) {
                    Log.e(LOG_APP_TAG, v8Array.getString(0));
                    return;
                }
                final String modelJson = v8Array.getString(1);
                Log.i(LOG_APP_TAG, modelJson);
                jsVM = v8Array.getObject(2);

                AppBaseModels.RegistryBase model = GsonInstanceBuilder.getGsonInstance().fromJson(modelJson, AppBaseModels.RegistryBase.class);
                RxBus.publish(RxBus.MessageSubjectType.REGISTRY_MODEL, model);
            }, "getMainVMCallback");
            V8Object getCallback = iAmApp.getObject("getMainVMCallback");
            V8Array getParams = new V8Array(runtime).push("mainVM").push(getCallback);
            runtime.getObject("iAmApp").executeVoidFunction("getViewModel", getParams);

        } catch (Exception e) {
            Log.d(LOG_APP_TAG, Log.getStackTraceString(e));
        }

    }

    public void addEstablishmentComment(AppBaseModels.EstablishmentBase establishment, String commentStr){
        String objectPath = establishment.getPath();

        jsVM.registerJavaMethod((V8Object v8Object, V8Array v8Array) -> {
            if(v8Array.get(0) != null) {
                Log.e(LOG_APP_TAG, v8Array.getString(0));
                return;
            }
            final String modelJson = v8Array.getString(1);

            AppBaseModels.EstablishmentBase updatedEstablishment = GsonInstanceBuilder.getGsonInstance().fromJson(modelJson, AppBaseModels.EstablishmentBase.class);
            RxBus.publish(RxBus.MessageSubjectType.NEW_COMMENT_RESULT, updatedEstablishment);
        }, "getAddCommentCallback");
        V8Object getCallback = jsVM.getObject("getAddCommentCallback");
        V8Array onResponseParams = new V8Array(runtime).push(objectPath).push("addComment").push(commentStr).push(getCallback);
        jsVM.getObject("model").executeVoidFunction("execute", onResponseParams);

    }

    @Override
    public void onDestroy() {
        mReferenceManager.release();
    }

}

