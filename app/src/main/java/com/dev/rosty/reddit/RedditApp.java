package com.dev.rosty.reddit;

import android.app.Application;

import com.dev.rosty.reddit.injection.AppComponent;
import com.dev.rosty.reddit.injection.AppModule;
import com.dev.rosty.reddit.injection.DaggerAppComponent;

public class RedditApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
