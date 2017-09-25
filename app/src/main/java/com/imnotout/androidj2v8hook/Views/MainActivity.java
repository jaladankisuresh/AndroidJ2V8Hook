package com.imnotout.androidj2v8hook.Views;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.imnotout.androidj2v8hook.AndroidApplication;
import com.imnotout.androidj2v8hook.Models.AppModels;
import com.imnotout.androidj2v8hook.R;
import com.imnotout.androidj2v8hook.BR;
import com.imnotout.androidj2v8hook.ViewModels.MainVM;
import com.imnotout.androidj2v8hook.databinding.ActivityMainBinding;

import io.reactivex.disposables.Disposable;

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
public class MainActivity
        extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_APP_TAG = AndroidApplication.getAppTag();

    private MainVM vm;
    private final ObservableField<AppModels.Registry> registry = new ObservableField<>();
//    private AppModels.Registry registry;
    private Disposable mainDisposable;

    public ObservableField<AppModels.Registry> getData() {
        return registry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vm = new MainVM();
        mainDisposable = vm.getDataObservable()
            .subscribe(registryBase -> {

                    registry.set(new AppModels.Registry(registryBase));
                },  throwable -> {
                    // handle error event
                }
            );
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVm(vm);
        binding.setActivity(this);
    }

    @Override
    public void onClick(View view) {
        Log.i(LOG_APP_TAG, "Item Count : " + vm.getData().getEstablishments().size());
        Log.i(LOG_APP_TAG, "Removing Item : " + vm.getData().getEstablishments().get(0).getName());
        registry.get().getEstablishments().remove(0);
        registry.get().notifyPropertyChanged(BR.establishments);
        registry.get().setName("Yellow Pages India");
        registry.get().setDescription("Yellow Pages of all the establishments");
        Log.i(LOG_APP_TAG, "Item Count : " + vm.getData().getEstablishments().size());
    }

    @Override
    protected void onDestroy() {
        vm.getJsVM().release();
        mainDisposable.dispose();
        super.onDestroy();
    }
}
