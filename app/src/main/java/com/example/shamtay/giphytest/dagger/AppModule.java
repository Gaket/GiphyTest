package com.example.shamtay.giphytest.dagger;

import android.content.Context;

import com.example.shamtay.giphytest.GiphyApi;
import com.example.shamtay.giphytest.models.MyObjectBox;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.giphy.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    @Provides
    @Singleton
    GiphyApi getApi(Retrofit retrofit) {
        return retrofit.create(GiphyApi.class);
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return context;
    }


    @Provides
    @Singleton
    BoxStore getObjectBox(Context context) {
        return MyObjectBox.builder().androidContext(this).build();
    }

}
