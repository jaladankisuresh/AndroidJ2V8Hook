package com.imnotout.androidj2v8hook.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.imnotout.androidj2v8hook.BR;

public class VMObservable<T> extends BaseObservable {

    private T vm;

    @Bindable
    public T getVm() {
        return vm;
    }

    public void setVm(T vm) {
        this.vm = vm;
        notifyPropertyChanged(BR.vm);
    }
}
