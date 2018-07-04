package com.example.shamtay.giphytest.screens.video;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;
import com.example.shamtay.giphytest.GiphyApp;
import com.example.shamtay.giphytest.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class VideoViewController extends Controller implements VideoView {

    private static final String EXTRA_VIDEO_URL = "video_url";

    @BindView(R.id.player)
    PlayerView playerView;

    @BindView(R.id.down_vote_count)
    TextView downVoteCountView;

    @BindView(R.id.up_vote_count)
    TextView upVoteCount;

    SimpleExoPlayer player;

    @Inject
    VideoViewPresenter presenter;

    @NonNull
    private String url;

    public VideoViewController(@NonNull String url) {
        this.url = url;
        getArgs().putString(EXTRA_VIDEO_URL, url);
    }

    public VideoViewController(@Nullable Bundle args) {
        super(args);

        if (getArgs().getString(EXTRA_VIDEO_URL) != null) {
            Timber.e("url is not saved in args!");
            url = "";
        } else {
            url = getArgs().getString(EXTRA_VIDEO_URL);
        }
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View v = inflater.inflate(R.layout.view_video, container, false);
        GiphyApp.getComponentInjector().getVideoComponent(url).inject(this);
        ButterKnife.bind(this, v);
        setUpPlayer();
        presenter.onViewCreate(this);
        return v;
    }

    private void setUpPlayer() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        if (getActivity() == null) {
            Timber.e("Activity not attached");
            return;
        }

        player =
                ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        playerView.setPlayer(player);

        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "GiphyTest"));

        MediaSource source =
                new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url));

        player.prepare(source);
        player.setPlayWhenReady(true);
    }

    @OnClick(R.id.down_vote)
    void downVoteView() {
        presenter.onDownVoteClick();
    }

    @OnClick(R.id.up_vote)
    void upVote() {
        presenter.onUpVoteClick();
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        playerView = null;
        player.release();
        player = null;
        upVoteCount = null;
        downVoteCountView = null;
        presenter.onDestroy();
        if (isBeingDestroyed()) {
            GiphyApp.getComponentInjector().clearVideoComponent();
        }
    }

    @Override
    public void showUpVoteCount(int count) {
        upVoteCount.setText(String.valueOf(count));
    }

    @Override
    public void showDownVoteCount(int count) {
        downVoteCountView.setText(String.valueOf(count));
    }
}
