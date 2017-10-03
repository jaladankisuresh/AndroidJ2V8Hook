package com.imnotout.androidj2v8hook.Views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.imnotout.androidj2v8hook.AndroidApplication;
import com.imnotout.androidj2v8hook.Models.AppModels;
import com.imnotout.androidj2v8hook.R;
import com.imnotout.androidj2v8hook.Utils.RxBus;
import com.imnotout.androidj2v8hook.databinding.ActivityMainBinding;
import com.imnotout.androidj2v8hook.databinding.FragmentCreateEditCommentBinding;

public class CreateEditCommentFragment
        extends DialogFragment implements View.OnClickListener {

    private static final String LOG_APP_TAG = AndroidApplication.getAppTag();
    private FragmentCreateEditCommentBinding binding;

    public CreateEditCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_create_edit_comment, container, false);
        View view = binding.getRoot();
        int index =  getArguments().getInt("index", -1);
        AppModels.Establishment establishment =  (AppModels.Establishment) getArguments().getSerializable("model");
        binding.setIndex(index);
        binding.setModel(establishment);
        binding.setMView(this);
        binding.btnSubmitComment.setOnClickListener(this);

        getDialog().setTitle("CreateEditCommentFragment");
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
        return view;
    }

    @Override
    public void onClick(View view) {
        Bundle args = getArguments();
        args.putString("newComment", binding.txtAddEditComment.getText().toString());
        RxBus.publish(RxBus.MessageSubjectType.POST_NEW_COMMENT, args);
    }
}
