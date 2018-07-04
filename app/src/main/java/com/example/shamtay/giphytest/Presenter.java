package com.example.shamtay.giphytest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class Presenter<V> {

    private CompositeDisposable disposable = new CompositeDisposable();

    @Nullable
    private V view;

    protected void unsubscribeOnDestroy(@NonNull final Disposable subscription) {
        disposable.add(subscription);
    }

    public @Nullable
    V getView() {
        return view;
    }

    protected void setView(@NonNull V view) {
        this.view = view;
    }

    public void onDestroy() {
        view = null;
        disposable.clear();
    }

    public void onDetach() {
        view = null;
    }
}
