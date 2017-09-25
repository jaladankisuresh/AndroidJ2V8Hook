package com.imnotout.androidj2v8hook.Views;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.imnotout.androidj2v8hook.Models.AppModels;
import com.imnotout.androidj2v8hook.R;
import com.imnotout.androidj2v8hook.ViewModels.LauncherVM;
import com.imnotout.androidj2v8hook.databinding.ActivityLauncherBinding;

public class LauncherActivity extends AppCompatActivity
        implements View.OnClickListener{

    private LauncherVM vm;
    private AppModels.Launcher launcher;

    public AppModels.Launcher getData() {
        return launcher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vm = new LauncherVM();

        launcher = new AppModels.Launcher(vm.getData());

        ActivityLauncherBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_launcher);
        binding.setVm(vm);
        binding.setActivity(this);
    }

    @Override
    protected void onDestroy() {
        vm.getJsVM().release();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }
}
