package com.example.shamtay.giphytest.video;

import io.objectbox.BoxStore;

public class VoteRepository {
    private BoxStore boxStore;

    public VoteRepository(BoxStore boxStore) {
        this.boxStore = boxStore;
    }

    public void saveUpVote() {
    
    }

    public void saveDownVote() {

    }
}
