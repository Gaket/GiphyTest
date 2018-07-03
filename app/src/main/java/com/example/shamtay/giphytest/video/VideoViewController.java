package com.example.shamtay.giphytest.video;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.example.shamtay.giphytest.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoViewController extends Controller {

    private static final String EXTRA_VIDEO_URL = "video_url";

    @NonNull
    private String url;

    @BindView(R.id.player)
    PlayerView playerView;

    public VideoViewController(@NonNull String url) {
        this.url = url;
        getArgs().putString(EXTRA_VIDEO_URL, url);
    }

    protected VideoViewController(@Nullable Bundle args) {
        super(args);
        // TODO: 03.07.2018
        url = getArgs().getString(EXTRA_VIDEO_URL);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View v = inflater.inflate(R.layout.view_video, container, false);
        ButterKnife.bind(this, v);

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // todo check getActivity null
        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        playerView.setPlayer(player);

        // todo getActivity = null
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "GiphyTest"));

        MediaSource source =
                new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url));

        player.prepare(source);
        player.setPlayWhenReady(true);

        return v;
    }

}
