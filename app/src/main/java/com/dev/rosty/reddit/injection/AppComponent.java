package com.dev.rosty.reddit.injection;

import com.dev.rosty.reddit.common.FileManager;
import com.dev.rosty.reddit.common.RxSchedulers;
import com.dev.rosty.reddit.data.DataSource;
import com.dev.rosty.reddit.presentation.image.ImageViewModel;
import com.dev.rosty.reddit.presentation.posts.PostsViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    DataSource dataSource();
    RxSchedulers rxSchedulers();
    FileManager fileManager();

    void inject(PostsViewModel viewModel);
    void inject(ImageViewModel viewModel);

    interface Injectable {

        void inject(AppComponent appComponent);
    }
}
