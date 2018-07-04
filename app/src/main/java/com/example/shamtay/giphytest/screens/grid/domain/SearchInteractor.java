package com.example.shamtay.giphytest.screens.grid.domain;

import com.example.shamtay.giphytest.models.SearchResponseModel;

import io.reactivex.Observable;


public class SearchInteractor {

    private SearchRepository repository;

    public SearchInteractor(SearchRepository repository) {
        this.repository = repository;
    }

    public Observable<SearchResponseModel> search(String text, int limit, int offset) {
        return repository.search(text, limit, offset);
    }

}
