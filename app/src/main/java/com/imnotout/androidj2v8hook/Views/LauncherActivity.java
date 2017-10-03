package com.imnotout.androidj2v8hook.Views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.imnotout.androidj2v8hook.Models.AppBaseModels;
import com.imnotout.androidj2v8hook.Models.AppModels;
import com.imnotout.androidj2v8hook.R;
import com.imnotout.androidj2v8hook.Utils.RxBus;
import com.imnotout.androidj2v8hook.ViewModels.LauncherVM;
import com.imnotout.androidj2v8hook.databinding.ActivityLauncherBinding;

public class LauncherActivity extends AppCompatActivity implements View.OnClickListener {

    private LauncherVM vm;
    private final ObservableField<AppModels.Launcher> launcher = new ObservableField<>();

    public ObservableField<AppModels.Launcher> getData() {
        return launcher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RxBus.subscribe(RxBus.MessageSubjectType.LAUNCHER_MODEL,this, data -> {
            launcher.set(new AppModels.Launcher((AppBaseModels.LauncherBase) data));
        });
        vm = new LauncherVM();

        ActivityLauncherBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_launcher);
        binding.setMView(this);

        binding.btnLaunchDirectory.setOnClickListener(this::onClick);
    }

    @Override
    protected void onDestroy() {
        vm.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }
}
