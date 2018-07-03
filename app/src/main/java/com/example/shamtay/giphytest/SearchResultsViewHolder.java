package com.example.shamtay.giphytest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image)
    ImageView imageView;

    public SearchResultsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull SearchResultsViewModel model) {
        Picasso.get()
                .load(model.imageUrl)
                //.resize(200, 50)
                //.centerCrop()
                .into(imageView);
    }

    public void setOnImageClickListener(@NonNull View.OnClickListener onClickListener) {
        imageView.setOnClickListener(onClickListener);
    }

}
