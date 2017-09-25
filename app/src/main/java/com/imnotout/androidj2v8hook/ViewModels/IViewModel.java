package com.imnotout.androidj2v8hook.ViewModels;

import com.eclipsesource.v8.V8Object;
import io.reactivex.Single;

public interface IViewModel<T> {
    public Single<T> getDataObservable();
    public T getData();
    public V8Object getJsVM();
}
