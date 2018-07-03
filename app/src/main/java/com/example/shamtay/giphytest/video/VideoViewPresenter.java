package com.example.shamtay.giphytest.video;

public class VideoViewPresenter {

    private VoteRepository repository;

    public VideoViewPresenter(VoteRepository repository, String url) {
        this.repository = repository;
    }

    public void onDownVoteClick() {
        repository.saveUpVote();
    }

    public void onUpVoteClick() {
        repository.saveDownVote();
    }
}
