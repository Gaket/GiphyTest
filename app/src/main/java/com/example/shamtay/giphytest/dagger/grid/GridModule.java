package com.example.shamtay.giphytest.dagger.grid;

import com.example.shamtay.giphytest.GiphyApi;
import com.example.shamtay.giphytest.dagger.FragmentScope;
import com.example.shamtay.giphytest.screens.grid.ImagesGridPresenter;
import com.example.shamtay.giphytest.screens.grid.domain.SearchInteractor;
import com.example.shamtay.giphytest.screens.grid.domain.SearchRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class GridModule {

    @Provides
    @FragmentScope
    SearchRepository getRepository(GiphyApi api) {
        return new SearchRepository(api);
    }


    @Provides
    @FragmentScope
    SearchInteractor getInteractor(SearchRepository repository) {
        return new SearchInteractor(repository);
    }


    @Provides
    @FragmentScope
    ImagesGridPresenter getImagesGridPresenter(SearchInteractor interactor) {
        return new ImagesGridPresenter(interactor);
    }


}
