package com.snindustries.project.udacity.bake_o_bake.ui.stepdetail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.snindustries.project.udacity.bake_o_bake.R;
import com.snindustries.project.udacity.bake_o_bake.databinding.StepDetailFragmentBinding;
import com.snindustries.project.udacity.bake_o_bake.utils.AppDataBindingComponent;
import com.snindustries.project.udacity.bake_o_bake.webservice.Repository;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Step;


public class StepDetailFragment extends Fragment {

    private boolean playWhenReady = true;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private StepDetailViewModel viewModel;

    public static StepDetailFragment newInstance() {
        return new StepDetailFragment();
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer")).createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void initPlayer() {
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), new DefaultTrackSelector(), new DefaultLoadControl());
        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(viewModel.getCurrentWindow(), viewModel.getPlaybackPosition());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        StepDetailFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.step_detail_fragment, container, false, new AppDataBindingComponent());
        viewModel = ViewModelProviders.of(this).get(StepDetailViewModel.class);
        binding.setHandler(new Handler());
        binding.setModel(viewModel);
        binding.setLifecycleOwner(this);
        playerView = binding.videoPlayer;
        viewModel.getStep().observe(this, new Observer<Step>() {
            @Override
            public void onChanged(@Nullable Step step) {
                prepateSource(step);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initPlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void prepateSource(Step step) {
        Uri uri = Uri.parse(step.videoURL);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource);
    }

    private void releasePlayer() {
        if (player != null) {
            viewModel.setPlaybackPosition(player.getCurrentPosition());
            viewModel.setCurrentWindow(player.getCurrentWindowIndex());
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    public static class StepDetailViewModel extends ViewModel {
        private final LiveData<Step> step;
        private int currentWindow;
        private long playbackPosition;

        public StepDetailViewModel() {
            step = Repository.get().getCurrentStep();
        }

        public int getCurrentWindow() {
            return currentWindow;
        }

        public void setCurrentWindow(int currentWindow) {
            this.currentWindow = currentWindow;
        }

        public long getPlaybackPosition() {
            return playbackPosition;
        }

        public void setPlaybackPosition(long playbackPosition) {
            this.playbackPosition = playbackPosition;
        }

        public LiveData<Step> getStep() {
            return step;
        }
    }

    public class Handler {
        public void onClick(View view) {

        }

        public void onClickHideUi(View view){
            hideSystemUi();
        }
    }
}
