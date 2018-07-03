package com.example.shamtay.giphytest.grid;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shamtay.giphytest.Presenter;
import com.example.shamtay.giphytest.SearchResultsViewModel;
import com.example.shamtay.giphytest.dagger.GiphyApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.example.shamtay.giphytest.GiphyApp.API_KEY;

public class ImagesGridPresenter extends Presenter<ImagesGridView> {

    @NonNull
    GiphyApi api;

    @Nullable
    private List<SearchResultsViewModel> items;

    public ImagesGridPresenter(@NonNull GiphyApi api) {
        this.api = api;
    }

    public void onCreate(@NonNull ImagesGridView view) {
        setView(view);
        if (items == null) {
            loadData();
        } else {
            view.addItems(items);
        }
    }

    private void loadData() {
        Disposable d =
                api.search(API_KEY, "test")
                        .map(resp -> resp.data)
                        .flatMapIterable(items -> items)
                        .map(SearchResultsViewModel::getInstance)
                        .toList()
                        // TODO: 03.07.2018 refactor, may be cashing in repo?
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(items -> {

                            saveItems(items);
                            Timber.d("responseOk");
                            if (getView() != null) {
                                getView().addItems(items);
                            }
                        }, Timber::e);

        unsubscribeOnDestroy(d);
    }

    private void saveItems(List<SearchResultsViewModel> items) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.addAll(items);
    }

    public void onImageClick(SearchResultsViewModel searchResultsViewModel) {
        if (getView() == null) {
            Timber.e("View is null!");
            return;
        }

        getView().openVideoScreen(searchResultsViewModel.videoUrl);
    }

}
