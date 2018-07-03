package com.example.shamtay.giphytest.grid;

import android.support.annotation.NonNull;

import com.example.shamtay.giphytest.Presenter;
import com.example.shamtay.giphytest.SearchResultsViewModel;
import com.example.shamtay.giphytest.dagger.GiphyApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.example.shamtay.giphytest.GiphyApp.API_KEY;

public class ImagesGridPresenter extends Presenter<ImagesGridView> {

    @NonNull
    GiphyApi api;

    public ImagesGridPresenter(@NonNull GiphyApi api) {
        this.api = api;
    }

    public void onAttach(@NonNull ImagesGridView view) {
        super.attacheView(view);

        Disposable d =
                api.search(API_KEY, "test")
                        .map(resp -> resp.data)
                        .flatMapIterable(items -> items)
                        .map(SearchResultsViewModel::getInstance)
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(items -> {
                            Timber.d("responseOk");
                            if (getView() != null) {
                                getView().addItems(items);
                            } else {
                                Timber.e("View is null!");
                            }
                        }, Timber::e);

        unsubscribeOnDestroy(d);
    }

    public void onImageClick(SearchResultsViewModel searchResultsViewModel) {

        if (getView() == null) {
            Timber.e("View is null!");
            return;
        }

        getView().openVideoScreen(searchResultsViewModel.videoUrl);
    }
}
