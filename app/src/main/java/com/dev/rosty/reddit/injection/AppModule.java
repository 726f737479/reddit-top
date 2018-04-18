package com.dev.rosty.reddit.injection;

import android.content.Context;
import android.os.Environment;

import com.dev.rosty.reddit.common.FileManager;
import com.dev.rosty.reddit.common.FileManagerImpl;
import com.dev.rosty.reddit.common.RxSchedulers;
import com.dev.rosty.reddit.data.DataSource;
import com.dev.rosty.reddit.data.DataSourceImpl;
import com.dev.rosty.reddit.data.remote.RedditService;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public class AppModule {

    private final Context appContext;

    public AppModule(Context appContext) {
        this.appContext = appContext;
    }

    @Singleton
    @Provides
    public Context provideAppContext(){
        return appContext;
    }

    @Singleton
    @Provides
    public DataSource provideDataSource(RedditService redditService,
                                        RxSchedulers rxSchedulers) {

        return new DataSourceImpl(rxSchedulers, redditService);
    }

    @Provides
    public FileManager provideFileManager() {

        String dir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .getAbsolutePath();

        return new FileManagerImpl(dir);
    }

    @Singleton
    @Provides
    public RedditService provideRedditService(){

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        final Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
                .baseUrl(RedditService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(RedditService.class);
    }

    @Singleton
    @Provides
    public RxSchedulers provideRxSchedulers(){

        return new RxSchedulers() {

            @Override public Scheduler main() {
                return AndroidSchedulers.mainThread();
            }

            @Override public Scheduler io() {
                return Schedulers.io();
            }
        };
    }
}
