package com.example.shamtay.giphytest.screens.grid.recycler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shamtay.giphytest.R;
import com.example.shamtay.giphytest.models.SearchResultsViewModel;
import com.example.shamtay.giphytest.screens.grid.ImageClickListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsRecyclerAdapter extends RecyclerView.Adapter {

    @NonNull
    private LayoutInflater inflater;

    @NonNull
    private List<SearchResultsViewModel> searchResults;

    @Nullable
    private ImageClickListener onImageClickListener;

    public SearchResultsRecyclerAdapter(@NonNull LayoutInflater inflater) {
        this.inflater = inflater;
        searchResults = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.row_search_result, parent, false);
        return new SearchResultsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchResultsViewHolder viewHolder = (SearchResultsViewHolder) holder;

        viewHolder.bind(searchResults.get(position), onImageClickListener);
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public void addItems(@NonNull List<SearchResultsViewModel> items) {
        searchResults.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnImageClickListener(@NonNull ImageClickListener onClickListener) {
        onImageClickListener = onClickListener;
    }

    public void clear() {
        searchResults.clear();
        notifyDataSetChanged();
    }
}
