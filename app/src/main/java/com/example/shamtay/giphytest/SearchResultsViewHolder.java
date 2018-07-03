package com.example.shamtay.giphytest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchResultsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image)
    ImageView imageView;

    public SearchResultsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(SearchResultsViewModel model) {
        Picasso.get()
                .load(model.imageUrl)
                //.resize(200, 50)
                //.centerCrop()
                .into(imageView);

    }

}
