package com.example.shamtay.giphytest.dagger.grid;

import com.example.shamtay.giphytest.dagger.FragmentScope;
import com.example.shamtay.giphytest.grid.ImagesGridController;

import dagger.Subcomponent;

@Subcomponent(modules = {GridModule.class})
@FragmentScope
public interface GridComponent {
    void inject(ImagesGridController controller);
}
