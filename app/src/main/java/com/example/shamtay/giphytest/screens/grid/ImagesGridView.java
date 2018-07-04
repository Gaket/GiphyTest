package com.example.shamtay.giphytest.screens.grid;

import android.support.annotation.NonNull;

import com.example.shamtay.giphytest.models.SearchResultsViewModel;

import java.util.List;

public interface ImagesGridView {
    void addItems(@NonNull List<SearchResultsViewModel> items);

    void openVideoScreen(String videoUrl);

    void showProgress();

    void hideProgress();

    void clearItems();

    void collapseSearch();
}
