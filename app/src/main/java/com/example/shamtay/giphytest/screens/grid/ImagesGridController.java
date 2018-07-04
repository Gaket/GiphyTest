package com.example.shamtay.giphytest.screens.grid;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.shamtay.giphytest.GiphyApp;
import com.example.shamtay.giphytest.R;
import com.example.shamtay.giphytest.models.SearchResultsViewModel;
import com.example.shamtay.giphytest.screens.grid.recycler.SearchResultsRecyclerAdapter;
import com.example.shamtay.giphytest.screens.video.VideoViewController;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class ImagesGridController extends Controller implements ImagesGridView {

    private static final int COLUMNS_COUNT = 3;
    @BindView(R.id.search_results)
    RecyclerView searchResultsView;
    @BindView(R.id.progress_bar)
    View progressBar;
    @BindView(R.id.search_view)
    SearchView searchView;
    @Inject
    ImagesGridPresenter presenter;
    private SearchResultsRecyclerAdapter adapter;
    private LayoutInflater inflater;
    @Nullable
    private Disposable searchTextInputDisposable;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        this.inflater = inflater;

        View view = inflater.inflate(R.layout.view_images_grid, container, false);
        ButterKnife.bind(this, view);
        setRecyclerView();

        GiphyApp.getComponentInjector().getGridComponent().inject(this);

        presenter.onViewCreate(this);

        searchTextInputDisposable = RxSearchObservable.fromView(searchView)
                .debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> {
                    presenter.onSearchInput(text);
                }, Timber::e);

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
        adapter.setOnImageClickListener(image -> presenter.onImageClick(image));
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        adapter = null;
        searchResultsView = null;
        progressBar = null;
        searchView = null;

        if (searchTextInputDisposable != null && !searchTextInputDisposable.isDisposed()) {
            searchTextInputDisposable.dispose();
        }

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

    @Override
    public void clearItems() {
        adapter.clear();
    }

    @Override
    public void collapseSearch() {
        searchView.onActionViewCollapsed();
    }
}
