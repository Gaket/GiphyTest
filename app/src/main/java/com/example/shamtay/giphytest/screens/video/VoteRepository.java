package com.example.shamtay.giphytest.screens.video;

import android.support.annotation.NonNull;

import com.example.shamtay.giphytest.models.VoteModel;
import com.example.shamtay.giphytest.models.VoteModel_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataSubscriptionList;
import io.objectbox.reactive.SubscriptionBuilder;
import timber.log.Timber;

public class VoteRepository {

    @NonNull
    private Box<VoteModel> box;

    public VoteRepository(BoxStore boxStore) {
        box = boxStore.boxFor(VoteModel.class);
    }

    public void saveUpVote(String url) {
        box.put(new VoteModel(true, url));
    }

    public void saveDownVote(String url) {
        box.put(new VoteModel(false, url));
    }

    public SubscriptionBuilder<List<VoteModel>> getVotes(String url) {
        Query<VoteModel> query = box.query().equal(VoteModel_.url, url).build();


        return query.subscribe(new DataSubscriptionList())
                .onError(Timber::e);


    }
}
