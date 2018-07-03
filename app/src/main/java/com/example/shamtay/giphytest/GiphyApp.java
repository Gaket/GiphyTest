package com.example.shamtay.giphytest;

import android.app.Application;

import timber.log.Timber;

public class GiphyApp extends Application {

    public static final String API_KEY = "GbwtEKZ13MivW7bdUxx3je5MIxRc634N";

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
