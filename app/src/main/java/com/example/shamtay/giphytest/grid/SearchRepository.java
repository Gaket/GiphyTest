package com.example.shamtay.giphytest.grid;

import com.example.shamtay.giphytest.GiphyApi;
import com.example.shamtay.giphytest.models.SearchResponseModel;

import io.reactivex.Observable;

import static com.example.shamtay.giphytest.GiphyApp.API_KEY;

public class SearchRepository {

    private GiphyApi api;

    public SearchRepository(GiphyApi api) {
        this.api = api;
    }

    public Observable<SearchResponseModel> search(String text, int limit, int offset) {
        return api.search(API_KEY, text, limit, offset);
    }


}
