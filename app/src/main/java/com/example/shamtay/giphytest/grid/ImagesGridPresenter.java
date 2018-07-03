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

    private boolean isLoading;

    private boolean allDataLoaded;

    private static final int PAGINATION_LIMIT = 50;

    private int paginationOffset = 0;

    public ImagesGridPresenter(@NonNull GiphyApi api) {
        this.api = api;
    }

    public void onCreate(@NonNull ImagesGridView view) {
        setView(view);
        if (items == null && !isLoading) {
            loadData();
        } else if (items != null){
            view.addItems(items);
        } else {
            view.showProgress();
        }
    }

    private void loadData() {

        if (allDataLoaded) return;

        Disposable d =
                api.search(API_KEY, "test", PAGINATION_LIMIT, paginationOffset)
                        // TODO: 03.07.2018 remove before release
                        //.delay(5, TimeUnit.SECONDS)
                        .doOnNext(response -> {
                            paginationOffset += PAGINATION_LIMIT;
                            if (response.pagination.count == 0) {
                                allDataLoaded = true;
                            }
                        })
                        .map(resp -> resp.data)
                        .flatMapIterable(items -> items)
                        .map(SearchResultsViewModel::getInstance)
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> showProgress())
                        .doFinally(this::hideProgress)
                        .subscribe(items -> {
                            saveItems(items);
                            if (getView() != null) {
                                getView().addItems(items);
                            }
                            Timber.d("responseOk");
                        }, Timber::e);
        unsubscribeOnDestroy(d);
    }

    private void showProgress() {
        isLoading = true;
        if (getView() != null) {
            getView().showProgress();
        } else {
            Timber.e("View is not attached!");
        }
    }

    private void hideProgress() {
        isLoading = false;
        if (getView() != null) {
            getView().hideProgress();
        }
    }


    private void saveItems(List<SearchResultsViewModel> items) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.addAll(items);
    }

    public void onImageClick(SearchResultsViewModel searchResultsViewModel) {
        if (getView() == null) {
            Timber.e("View is not attached!");
            return;
        }
        getView().openVideoScreen(searchResultsViewModel.videoUrl);
    }

    public void onScrollStateChanged(int lastCompletelyVisibleItem) {
        if (items == null) return;

        if (lastCompletelyVisibleItem >= items.size() - 10 && !isLoading) {
            loadData();
        }
    }
}
