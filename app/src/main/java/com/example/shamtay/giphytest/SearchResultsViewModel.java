package com.example.shamtay.giphytest;

import com.example.shamtay.giphytest.models.GifObject;

public class SearchResultsViewModel {

    public String imageUrl;
    public String videoUrl;

    public static SearchResultsViewModel getInstance(GifObject gifObject) {
        // TODO: 03.07.2018 null checks??
        SearchResultsViewModel model = new SearchResultsViewModel();
        model.imageUrl = gifObject.images.fixedWidthStill.url;
        model.videoUrl = gifObject.images.original.mp4;
        return model;
    }

}
