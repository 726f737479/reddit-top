package com.dev.rosty.reddit.injection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.dev.rosty.reddit.RedditApp;

public class VMFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppComponent appComponent;

    public VMFactory(Context context) {

        this.appComponent = ((RedditApp) context.getApplicationContext()).getAppComponent();
    }

    @NonNull
    @Override public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        final T t = super.create(modelClass);

        if (t instanceof AppComponent.Injectable)
            ((AppComponent.Injectable) t).inject(appComponent);

        return t;
    }
}
