package com.imnotout.androidj2v8hook.NetworkIO;

import android.content.Context;

public interface IWebApi {
    public void makeGet(String uri);
    public void makePost(String uri, String data);

    public interface Listener {
        void onSuccessResponse(String dataJSON);
        void onErrorResponse(String errMessage);
        Context getListenerContext();
    }
}

