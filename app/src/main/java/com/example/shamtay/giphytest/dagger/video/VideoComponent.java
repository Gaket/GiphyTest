package com.example.shamtay.giphytest.dagger.video;

import com.example.shamtay.giphytest.dagger.FragmentScope;
import com.google.gson.annotations.SerializedName;

import dagger.Subcomponent;

@Subcomponent(modules = VideoModule.class)
@FragmentScope
public interface VideoComponent {
}
