package com.imnotout.androidj2v8hook.Views;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.imnotout.androidj2v8hook.AndroidApplication;
import com.imnotout.androidj2v8hook.Models.AppModels;
import com.imnotout.androidj2v8hook.R;
import com.imnotout.androidj2v8hook.ViewAdapters.EstablishmentsArrayAdapter;
import com.imnotout.androidj2v8hook.ViewModels.MainVM;
import com.imnotout.androidj2v8hook.databinding.ActivityMainBinding;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.ConsumerSingleObserver;
import io.reactivex.schedulers.Schedulers;

/*
09-09 21:43:58.159 8022-8022/com.imnotout.androidj2v8hook I/androidj2v8hook: Making HttpRequest
09-09 21:43:58.332 8022-8022/com.imnotout.androidj2v8hook I/androidj2v8hook: [INFO] k : 1497001500
09-09 21:43:58.333 8022-8022/com.imnotout.androidj2v8hook I/androidj2v8hook: [INFO] inside makeRequest

----------------------------------------------------------------------------------------------------------
09-09 21:48:11.311 8132-8132/com.imnotout.androidj2v8hook I/androidj2v8hook: Preparing Making of HttpRequest
09-09 21:48:11.372 8132-8132/com.imnotout.androidj2v8hook I/androidj2v8hook: K :1497001500
09-09 21:48:11.372 8132-8132/com.imnotout.androidj2v8hook I/androidj2v8hook: Making HttpRequest
09-09 21:48:11.463 8132-8132/com.imnotout.androidj2v8hook I/androidj2v8hook: [INFO] k : 1497001500
09-09 21:48:11.464 8132-8132/com.imnotout.androidj2v8hook I/androidj2v8hook: [INFO] inside makeRequest

----------------------------------------------------------------------------------------------------------------
09-09 21:50:23.373 8244-8244/com.imnotout.androidj2v8hook I/androidj2v8hook: Preparing Making of HttpRequest
09-09 21:50:23.411 8244-8244/com.imnotout.androidj2v8hook I/androidj2v8hook: K :1497001500
09-09 21:50:23.411 8244-8244/com.imnotout.androidj2v8hook I/androidj2v8hook: Making HttpRequest
09-09 21:50:23.542 8244-8244/com.imnotout.androidj2v8hook I/androidj2v8hook: [INFO] k : 1497001500
09-09 21:50:23.542 8244-8244/com.imnotout.androidj2v8hook I/androidj2v8hook: [INFO] inside makeRequest

*/
public class MainActivity extends AppCompatActivity {
    private static final String LOG_APP_TAG = AndroidApplication.getAppTag();

    private MainVM vm;
    private Disposable mainDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vm = new MainVM();
        mainDisposable = vm.getDataObservable()
                .subscribe(registry -> {
                    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
                    binding.setVm(vm);
                    binding.setActivity(this);
//                    binding.listEstablishments.setLayoutManager(
//                            new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//                    binding.listEstablishments.setAdapter(new EstablishmentsArrayAdapter(vm.getData().getEstablishments()));
                },  throwable -> {
                    // handle error event
                }
        );
    }

    @Override
    protected void onDestroy() {
        vm.getJsVM().release();
        mainDisposable.dispose();
        super.onDestroy();
    }
}
