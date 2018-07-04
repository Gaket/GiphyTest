package com.example.shamtay.giphytest.dagger.video;

import android.support.annotation.NonNull;

import com.example.shamtay.giphytest.dagger.FragmentScope;
import com.example.shamtay.giphytest.screens.video.VideoViewPresenter;
import com.example.shamtay.giphytest.screens.video.VoteRepository;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

@Module
public class VideoModule {

    @NonNull
    private String url;

    public VideoModule(String url) {
        this.url = url;
    }

    @Provides
    @FragmentScope
    VoteRepository getRepository(BoxStore boxStore) {
        return new VoteRepository(boxStore);
    }

    @Provides
    @FragmentScope
    VideoViewPresenter getPresenter(VoteRepository repository) {
        return new VideoViewPresenter(repository, url);
    }


}
