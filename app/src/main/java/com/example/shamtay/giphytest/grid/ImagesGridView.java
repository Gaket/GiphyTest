package com.example.shamtay.giphytest.grid;

import com.example.shamtay.giphytest.SearchResultsViewModel;

import java.util.List;

public interface ImagesGridView {
    void addItems(List<SearchResultsViewModel> items);

    void openVideoScreen(String videoUrl);
}
