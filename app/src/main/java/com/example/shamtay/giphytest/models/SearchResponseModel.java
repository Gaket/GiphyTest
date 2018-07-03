package com.example.shamtay.giphytest.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponseModel {

    @SerializedName("data")
    public List<GifObject> data;

    @SerializedName("Property")
    public Property property;

    public static class Property {

        @SerializedName("msg")
        public String msg;

        @SerializedName("status")
        public int status;

    }


}
