package com.example.shamtay.giphytest;

import android.app.Application;

import com.example.shamtay.giphytest.dagger.ComponentInjector;

import timber.log.Timber;

public class GiphyApp extends Application {

    public static final String API_KEY = "GbwtEKZ13MivW7bdUxx3je5MIxRc634N";

    private static ComponentInjector componentInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        componentInjector = new ComponentInjector();
    }

    public static ComponentInjector getComponentInjector() {
        return componentInjector;
    }


}
