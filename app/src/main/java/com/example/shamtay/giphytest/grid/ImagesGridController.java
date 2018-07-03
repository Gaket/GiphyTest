package com.example.shamtay.giphytest.grid;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.shamtay.giphytest.GiphyApp;
import com.example.shamtay.giphytest.R;
import com.example.shamtay.giphytest.SearchResultsViewModel;
import com.example.shamtay.giphytest.models.SearchResultsRecyclerAdapter;
import com.example.shamtay.giphytest.video.VideoViewController;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesGridController extends Controller implements ImagesGridView {

    @BindView(R.id.search_results)
    RecyclerView searchResultsView;

    @BindView(R.id.progress_bar)
    View progressBar;

    private SearchResultsRecyclerAdapter adapter;

    @Inject
    ImagesGridPresenter presenter;
    private LayoutInflater inflater;

    private static final int COLUMNS_COUNT = 3;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        this.inflater = inflater;

        View view = inflater.inflate(R.layout.view_images_grid, container, false);
        ButterKnife.bind(this, view);
        setRecyclerView();

        GiphyApp.getComponentInjector().getGridComponent().inject(this);

        presenter.onCreate(this);


        return view;
    }

    private void setRecyclerView() {
        adapter = new SearchResultsRecyclerAdapter(inflater);
        searchResultsView.setLayoutManager(new GridLayoutManager(getApplicationContext(), COLUMNS_COUNT));

        searchResultsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int lastCompletelyVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                presenter.onScrollStateChanged(lastCompletelyVisibleItem);

            }

        });

        searchResultsView.setAdapter(adapter);
        adapter.setOnImageClickListener( image -> presenter.onImageClick(image));
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        adapter = null;
        if (isBeingDestroyed()) {
            GiphyApp.getComponentInjector().clearGridComponent();
            presenter.onDestroy();
        } else {
            presenter.onDetach();
        }
    }

    @Override
    public void addItems(@NonNull List<SearchResultsViewModel> items) {
        adapter.addItems(items);
    }

    @Override
    public void openVideoScreen(String videoUrl) {
        getRouter().pushController(
                RouterTransaction.with(
                        new VideoViewController(videoUrl))
        );
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
