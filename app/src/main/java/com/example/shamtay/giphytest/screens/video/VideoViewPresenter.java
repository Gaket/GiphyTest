package com.example.shamtay.giphytest.screens.video;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shamtay.giphytest.Presenter;
import com.example.shamtay.giphytest.models.VoteModel;

import java.util.List;

import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataSubscription;
import timber.log.Timber;

public class VideoViewPresenter extends Presenter<VideoView> {

    @NonNull
    private VoteRepository repository;

    @NonNull
    private String url;

    @Nullable
    private DataSubscription subscription;


    public VideoViewPresenter(@NonNull VoteRepository repository, @NonNull String url) {
        this.repository = repository;
        this.url = url;
    }

    public void onDownVoteClick() {
        repository.saveUpVote(url);
    }

    public void onUpVoteClick() {
        repository.saveDownVote(url);
    }

    public void onViewCreate(VideoView videoView) {
        setView(videoView);
        getVotes();
    }

    private void getVotes() {
        subscription = repository.getVotes(url)
                .on(AndroidScheduler.mainThread())
                .observer(this::showVotesCount);
    }

    private void showVotesCount(List<VoteModel> votes) {
        int upCount = 0;
        int downCount = 0;

        for (VoteModel voteModel : votes) {
            upCount += voteModel.upVote ? 1 : 0;
            downCount += voteModel.upVote ? 0 : 1;
        }

        if (getView() == null) {
            Timber.d("view is not attached!");
        } else {
            getView().showUpVoteCount(upCount);
            getView().showDownVoteCount(downCount);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isCanceled()) {
            subscription.cancel();
        }
    }
}
