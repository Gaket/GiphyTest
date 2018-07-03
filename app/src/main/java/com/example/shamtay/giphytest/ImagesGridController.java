package com.example.shamtay.giphytest;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.example.shamtay.giphytest.dagger.DaggerAppComponent;
import com.example.shamtay.giphytest.dagger.GiphyApi;
import com.example.shamtay.giphytest.models.SearchResultsRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static com.example.shamtay.giphytest.GiphyApp.API_KEY;

public class ImagesGridController extends Controller {

    @Inject
    GiphyApi api;

    @BindView(R.id.search_results)
    RecyclerView searchResultsView;

    private SearchResultsRecyclerAdapter adapter;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.view_images_grid, container, false);
        ButterKnife.bind(this, view);
        DaggerAppComponent.builder().build().inject(this);
        adapter = new SearchResultsRecyclerAdapter(inflater);

        searchResultsView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        searchResultsView.setAdapter(adapter);

        api.search(API_KEY, "test")
                .map(resp -> resp.data)
                .flatMapIterable(items -> items)
                .map(item -> SearchResultsViewModel.getInstance(item))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> {
                    Timber.d("responseOk");
                    adapter.addItems(items);
                }, e -> {
                    Timber.e(e);
                });

        return view;
    }

}
