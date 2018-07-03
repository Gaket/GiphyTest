package com.example.shamtay.giphytest;

import com.example.shamtay.giphytest.models.GifObject;

public class SearchResultsViewModel {

    public String imageUrl;
    public String videoUrl;

    public static SearchResultsViewModel getInstance(GifObject gifObject) {
        SearchResultsViewModel model = new SearchResultsViewModel();
        model.imageUrl = gifObject.images.fixedWidthStill.url;
        return model;
    }

}
