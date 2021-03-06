package com.example.shamtay.giphytest.screens.grid;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shamtay.giphytest.Presenter;
import com.example.shamtay.giphytest.models.SearchResultsViewModel;
import com.example.shamtay.giphytest.screens.grid.domain.SearchInteractor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class ImagesGridPresenter extends Presenter<ImagesGridView> {

    private static final int PAGINATION_LIMIT = 50;
    private static final String VIEW_NOT_ATTACHED = "View is not attached!";


    @Nullable
    private List<SearchResultsViewModel> items;

    // TODO: 04.07.2018 move pagination responsibility to separate class
    private boolean isLoading;

    private boolean allDataLoaded;

    private int paginationOffset = 0;

    @Nullable
    private Disposable searchDisposable;

    @Nullable
    private String currentTextSearch;

    @NonNull
    private SearchInteractor interactor;

    public ImagesGridPresenter(@NonNull SearchInteractor interactor) {
        this.interactor = interactor;
    }

    public void onViewCreate(@NonNull ImagesGridView view) {
        setView(view);

        if (items == null && !isLoading) {
            loadData();
        } else if (items != null) {
            view.addItems(items);
        } else {
            view.showProgress();
        }
    }

    private void loadData() {

        if (allDataLoaded) return;

        if (currentTextSearch == null) return;

        searchDisposable =
                interactor.search(currentTextSearch, PAGINATION_LIMIT, paginationOffset)
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
                        }, Timber::e);
        unsubscribeOnDestroy(searchDisposable);
    }

    private void showProgress() {
        isLoading = true;
        if (getView() != null) {
            getView().showProgress();
        } else {
            Timber.e(VIEW_NOT_ATTACHED);
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
            Timber.e(VIEW_NOT_ATTACHED);
            return;
        }
        getView().collapseSearch();
        getView().openVideoScreen(searchResultsViewModel.videoUrl);
    }

    public void onScrollStateChanged(int lastCompletelyVisibleItem) {
        if (items == null) return;

        if (lastCompletelyVisibleItem >= items.size() - 10 && !isLoading) {
            loadData();
        }
    }

    public void onSearchInput(@NonNull String text) {
        if (text.isEmpty() || text.equals(currentTextSearch)) {
            return;
        }

        if (searchDisposable != null && !searchDisposable.isDisposed()) {
            searchDisposable.dispose();
        }

        if (getView() == null) {
            Timber.e(VIEW_NOT_ATTACHED);
        } else {
            getView().clearItems();
            items = null;
        }

        currentTextSearch = text;
        allDataLoaded = false;
        isLoading = false;
        loadData();
    }
}
