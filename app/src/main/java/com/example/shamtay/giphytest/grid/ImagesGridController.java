package com.example.shamtay.giphytest.grid;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.example.shamtay.giphytest.R;
import com.example.shamtay.giphytest.SearchResultsViewModel;
import com.example.shamtay.giphytest.dagger.DaggerAppComponent;
import com.example.shamtay.giphytest.models.SearchResultsRecyclerAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesGridController extends Controller implements ImagesGridView {

    @BindView(R.id.search_results)
    RecyclerView searchResultsView;

    private SearchResultsRecyclerAdapter adapter;

    @Inject
    ImagesGridPresenter presenter;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.view_images_grid, container, false);
        ButterKnife.bind(this, view);
        DaggerAppComponent.builder().build().inject(this);
        adapter = new SearchResultsRecyclerAdapter(inflater);

        searchResultsView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        searchResultsView.setAdapter(adapter);

        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        presenter.onDestroy();
    }

    @Override
    public void addItems(List<SearchResultsViewModel> items) {
        adapter.addItems(items);
    }
}
