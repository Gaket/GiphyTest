package com.example.shamtay.giphytest.models;

public class SearchResultsViewModel {

    public String imageUrl;
    public String videoUrl;

    public static SearchResultsViewModel getInstance(GifObjectModel gifObjectModel) {
        // TODO: 03.07.2018 null checks??
        SearchResultsViewModel model = new SearchResultsViewModel();
        model.imageUrl = gifObjectModel.images.fixedWidthStill.url;
        model.videoUrl = gifObjectModel.images.original.mp4;
        return model;
    }

}
