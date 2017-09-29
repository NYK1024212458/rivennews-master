package com.riven.lee.rivennews.ui.splash;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.riven.lee.rivennews.R;
import com.riven.lee.rivennews.widget.FullScreenVideoView;
import com.riven.lee.rivennews.widget.LazyLoadFragment;



/**
 * Created by rivenLee
 * 2017/9/28.
 * O -))))>
 */

public class VideoItemFragment extends LazyLoadFragment implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private int position;
    private int videoRes;
    private int imgRes;
    private int mVideoPosition;
    private FullScreenVideoView mVideoView;
    private boolean mHasPaused;


    public VideoItemFragment() {
        mHasPaused = false;
        mVideoPosition = 0;
    }

    @Override
    protected int setContentView() {
        return R.layout.video_viewpager_item;
    }

    @Override
    protected void lazyLoad() {
        if (getArguments() == null) {
            return;
        }
        position = getArguments().getInt("position");
        videoRes = getArguments().getInt("videoRes");
        imgRes = getArguments().getInt("imgRes");

        mVideoView = findViewById(R.id.vvSplash);
        ImageView mvSlogan = findViewById(R.id.ivSlogan);

        mVideoView.setOnErrorListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setVideoPath("android.resource://" + getActivity().getPackageName() + "/" + videoRes);
        mvSlogan.setImageResource(imgRes);

    }

    public static VideoItemFragment newInstance(int position, int videoRes, int imgRes) {
        VideoItemFragment fragment = new VideoItemFragment();
        Bundle localBundle = new Bundle();
        localBundle.putInt("position", position);
        localBundle.putInt("videoRes", videoRes);
        localBundle.putInt("imgRes", imgRes);
        fragment.setArguments(localBundle);
        return fragment;
    }


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        FragmentActivity localFragmentActivity = getActivity();
        if ((localFragmentActivity != null) && ((localFragmentActivity instanceof SplashActivity))) {
            ((SplashActivity) localFragmentActivity).next(position);
        }
        return true;
    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mVideoView != null) {
            mVideoView.requestFocus();
            mVideoView.setOnCompletionListener(this);
            mVideoView.seekTo(0);
            mVideoView.start();
        }
        return;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        FragmentActivity localFragmentActivity = getActivity();
        if ((localFragmentActivity != null) && ((localFragmentActivity instanceof SplashActivity))) {
            ((SplashActivity) localFragmentActivity).next(position);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHasPaused) {
            if (mVideoView != null) {
                mVideoView.seekTo(mVideoPosition);
                mVideoView.resume();
            }
        }
        return;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoPosition = mVideoView.getCurrentPosition();
        }
        mHasPaused = true;
    }

    public void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
        return;
    }

    public void pause() {
        if ((mVideoView != null) && (mVideoView.canPause())) {
            mVideoView.setOnCompletionListener(null);
            mVideoView.pause();
        }
        return;

    }

    public void play() {
        if (mVideoView != null) {
            mVideoView.requestFocus();
            mVideoView.setOnCompletionListener(this);
            mVideoView.seekTo(0);
        } else {
            return;
        }
        mVideoView.start();
    }

    public void reLoadVideo() {
        if (mVideoView != null) {
            mVideoView.setVideoPath("android.resource://" + getActivity().getPackageName() + "/" + videoRes);
        }
    }
}
