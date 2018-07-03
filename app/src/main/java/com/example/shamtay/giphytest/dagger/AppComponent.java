package com.example.shamtay.giphytest.dagger;

import com.example.shamtay.giphytest.dagger.grid.GridComponent;
import com.example.shamtay.giphytest.dagger.grid.GridModule;
import com.example.shamtay.giphytest.dagger.video.VideoComponent;
import com.example.shamtay.giphytest.dagger.video.VideoModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    GridComponent addGridComponent(GridModule module);
    VideoComponent addVideoComponent(VideoModule module);
}
