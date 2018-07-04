package com.example.shamtay.giphytest.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponseModel {

    @SerializedName("data")
    public List<GifObjectModel> data;

    @SerializedName("pagination")
    public Pagination pagination;

    public static class Pagination {
        @SerializedName("offset")
        public int offset;

        @SerializedName("total_count")
        public int totalCount;

        @SerializedName("count")
        public int count;
    }

}
