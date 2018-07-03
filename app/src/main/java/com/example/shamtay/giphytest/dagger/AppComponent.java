package com.example.shamtay.giphytest.dagger;

import com.example.shamtay.giphytest.ImagesGridController;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(ImagesGridController mainActivity);
}
