package com.example.shamtay.giphytest.models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class VoteModel {

    @Id
    public long id;

    public boolean upVote;

    public String url;

    public VoteModel() {
        super();
    }

    public VoteModel(boolean upVote, String url) {
        this.upVote = upVote;
        this.url = url;
    }


}
