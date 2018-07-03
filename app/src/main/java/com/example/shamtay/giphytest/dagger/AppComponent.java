package com.example.shamtay.giphytest.dagger;

import com.example.shamtay.giphytest.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
