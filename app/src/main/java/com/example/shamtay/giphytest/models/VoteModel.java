package com.example.shamtay.giphytest.models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class VoteModel {

    @Id
    public long id;

    public String url;

    public boolean upVote;

}
