package com.example.shamtay.giphytest.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shamtay.giphytest.R;
import com.example.shamtay.giphytest.SearchResultsViewHolder;
import com.example.shamtay.giphytest.SearchResultsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;

    @NonNull
    private List<SearchResultsViewModel> searchResults;

    public SearchResultsRecyclerAdapter(@NonNull Context context) {
        this.context = context;
        searchResults = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_search_result, parent, false);

        return new SearchResultsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((SearchResultsViewHolder) holder).bind(searchResults.get(position));
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public void addItems(@NonNull List<SearchResultsViewModel> items) {
        searchResults.addAll(items);
        notifyDataSetChanged();
    }


}
