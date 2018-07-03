package com.example.shamtay.giphytest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.shamtay.giphytest.grid.ImageClickListener;
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

    public void bind(@NonNull SearchResultsViewModel model, @Nullable ImageClickListener onClickListener) {
        Picasso.get()
                .load(model.imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);

        imageView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onImageClick(model);
            }
        });
    }

}
