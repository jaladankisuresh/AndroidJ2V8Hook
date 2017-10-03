package com.imnotout.androidj2v8hook.Views;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.imnotout.androidj2v8hook.AndroidApplication;
import com.imnotout.androidj2v8hook.DataParsers.EstablishmentModelizer;
import com.imnotout.androidj2v8hook.Models.AppBaseModels;
import com.imnotout.androidj2v8hook.Models.AppModels;
import com.imnotout.androidj2v8hook.R;
import com.imnotout.androidj2v8hook.BR;
import com.imnotout.androidj2v8hook.Utils.RxBus;
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
//    private Disposable mainDisposable;

    public ObservableField<AppModels.Registry> getData() {
        return registry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RxBus.subscribe(RxBus.MessageSubjectType.REGISTRY_MODEL,this, data -> {
            registry.set(new AppModels.Registry((AppBaseModels.RegistryBase) data));
        });
        vm = new MainVM();

        RxBus.subscribe(RxBus.MessageSubjectType.ESTABLISHMENT_ADD_COMMENT,this, data -> {
            Bundle args = (Bundle) data;

            DialogFragment showCommentFragment = new CreateEditCommentFragment();
            showCommentFragment.setArguments(args);
            showCommentFragment.show(getSupportFragmentManager(),"detail_ESTABLISHMENT_ADD_COMMENT");

        });
        RxBus.subscribe(RxBus.MessageSubjectType.POST_NEW_COMMENT,this, data -> {
            Bundle args = (Bundle) data;
            AppModels.Establishment establishment =  (AppModels.Establishment) args.getSerializable("model");
            String newComment = args.getString("newComment");
            vm.addEstablishmentComment(establishment.getDataObject(), newComment);
            DialogFragment showCommentFragment = (DialogFragment) getSupportFragmentManager()
                    .findFragmentByTag("detail_ESTABLISHMENT_ADD_COMMENT");
            showCommentFragment.dismiss();
        });
        RxBus.subscribe(RxBus.MessageSubjectType.NEW_COMMENT_RESULT,this, obj -> {
            AppBaseModels.EstablishmentBase establishmentBase = (AppBaseModels.EstablishmentBase) obj;
            AppModels.Establishment establishment = (AppModels.Establishment)
                    AndroidApplication.pathParser(registry.get(), establishmentBase.getPath());
            //deep copy establishment object
            AndroidApplication.copyObject(establishment, EstablishmentModelizer.getInstance().modelize(establishmentBase));
        });

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVm(vm);
        binding.setMView(this);

        binding.btnDeleteItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.i(LOG_APP_TAG, "Removing Item : " + registry.get().getEstablishments().get(0).getName());
        registry.get().getEstablishments().remove(0);
        registry.get().notifyPropertyChanged(BR.establishments);
        registry.get().setName("Yellow Pages India");
        registry.get().setDescription("Yellow Pages of all the establishments");
    }

    @Override
    protected void onDestroy() {
        vm.onDestroy();
        RxBus.unregister(this);

        super.onDestroy();
    }
}
