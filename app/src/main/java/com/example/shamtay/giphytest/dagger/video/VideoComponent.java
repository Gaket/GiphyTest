package com.example.shamtay.giphytest.dagger.video;

import com.example.shamtay.giphytest.dagger.FragmentScope;
import com.example.shamtay.giphytest.screens.video.VideoViewController;

import dagger.Subcomponent;

@Subcomponent(modules = VideoModule.class)
@FragmentScope
public interface VideoComponent {
    void inject(VideoViewController controller);
}
