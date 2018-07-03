package com.example.shamtay.giphytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity {

    @Inject
    GiphyApi api;

    @BindView(R.id.search_results)
    RecyclerView searchResultsView;

    private SearchResultsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerAppComponent.builder().build().inject(this);
        ButterKnife.bind(this);

        adapter = new SearchResultsRecyclerAdapter(this);

        searchResultsView.setLayoutManager(new GridLayoutManager(this, 3));
        searchResultsView.setAdapter(adapter);
    }

    @OnClick(R.id.hello_world)
    void onClick() {
        Timber.d("HelloWorldClick");
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

    }

}
