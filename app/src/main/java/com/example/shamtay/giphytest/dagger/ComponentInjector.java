package com.example.shamtay.giphytest.dagger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shamtay.giphytest.dagger.grid.GridComponent;
import com.example.shamtay.giphytest.dagger.grid.GridModule;
import com.example.shamtay.giphytest.dagger.video.VideoComponent;
import com.example.shamtay.giphytest.dagger.video.VideoModule;

public class ComponentInjector {
    @NonNull
    private AppComponent appComponent;

    @Nullable
    private GridComponent gridComponent;

    @Nullable
    private VideoComponent videoComponent;

    public ComponentInjector(Context context) {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(context)).build();
    }

    @NonNull
    public GridComponent getGridComponent() {
        if (gridComponent == null) {
            gridComponent = appComponent.addGridComponent(new GridModule());
        }
        return gridComponent;
    }

    public void clearGridComponent() {
        gridComponent = null;
    }

    @NonNull
    public VideoComponent getVideoComponent(String url) {
        if (videoComponent == null) {
            videoComponent = appComponent.addVideoComponent(new VideoModule(url));
        }
        return videoComponent;
    }

    public void clearVideoComponent() {
        videoComponent = null;
    }

}
