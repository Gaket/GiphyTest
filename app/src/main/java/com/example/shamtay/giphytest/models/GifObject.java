package com.example.shamtay.giphytest.models;

import com.google.gson.annotations.SerializedName;

public class GifObject {

    @SerializedName("type")
    public String type;

    @SerializedName("images")
    public Images images;

    public static class Images {
        @SerializedName("fixed_width_still")
        public FixedWidthStill fixedWidthStill;

        @SerializedName("original")
        public Original original;
    }

    public static class FixedWidthStill {
        @SerializedName("url")
        public String url;
    }

    public static class Original {
        @SerializedName("mp4")
        public String mp4;
    }

}
