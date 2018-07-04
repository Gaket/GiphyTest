package com.example.shamtay.giphytest.models;

import timber.log.Timber;

public class SearchResultsViewModel {

    public String imageUrl;
    public String videoUrl;

    public static SearchResultsViewModel getInstance(GifObjectModel gifObjectModel) {
        SearchResultsViewModel model = new SearchResultsViewModel();

        if (gifObjectModel.images == null || gifObjectModel.images.fixedWidthStill == null
                || gifObjectModel.images.original == null) {
            Timber.e("inconsistent data");
            return model;
        }


        model.imageUrl = gifObjectModel.images.fixedWidthStill.url;
        model.videoUrl = gifObjectModel.images.original.mp4;
        return model;
    }

}
