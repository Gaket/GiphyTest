package com.example.shamtay.giphytest.dagger;

import android.support.annotation.NonNull;

import com.example.shamtay.giphytest.models.SearchResponseModel;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyApi {

    @GET("/v1/gifs/search")
    Observable<SearchResponseModel> search(@NonNull @Query("api_key") String apiKey,
                                           @NonNull @Query("q") String query,
                                           @Query("limit") int limit,
                                           @Query("offset") int offset);

}
