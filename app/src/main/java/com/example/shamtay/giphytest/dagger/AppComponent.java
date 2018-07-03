package com.example.shamtay.giphytest.dagger;

import com.example.shamtay.giphytest.dagger.grid.GridComponent;
import com.example.shamtay.giphytest.dagger.grid.GridModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    GridComponent addGridComponent(GridModule gridModule);
}
