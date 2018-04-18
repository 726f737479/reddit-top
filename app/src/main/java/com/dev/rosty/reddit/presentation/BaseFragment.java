package com.dev.rosty.reddit.presentation;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.rosty.reddit.injection.VMFactory;

public abstract class BaseFragment<VM extends ViewModel> extends Fragment {

    protected VM viewModel;
    protected View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders
                .of(this, new VMFactory(getContext()))
                .get(getViewModelClass());
    }

    @Override public View onCreateView(LayoutInflater inflater,
                                       ViewGroup container,
                                       Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), container, false);
        }

        return rootView;
    }

    protected abstract int getLayoutRes();
    protected abstract Class<VM> getViewModelClass();
}
