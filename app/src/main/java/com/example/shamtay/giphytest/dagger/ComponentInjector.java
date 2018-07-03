package com.example.shamtay.giphytest.dagger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shamtay.giphytest.dagger.grid.GridComponent;
import com.example.shamtay.giphytest.dagger.grid.GridModule;

public class ComponentInjector {

    private @NonNull AppComponent appComponent;
    private @Nullable GridComponent gridComponent;

    public ComponentInjector() {
        appComponent = DaggerAppComponent.builder().build();
    }

    public @NonNull GridComponent getGridComponent() {
        if (gridComponent == null) {
            gridComponent = appComponent.addGridComponent(new GridModule());
        }
        return gridComponent;
    }

    public void clearGridComponent() {
        gridComponent = null;
    }



}
