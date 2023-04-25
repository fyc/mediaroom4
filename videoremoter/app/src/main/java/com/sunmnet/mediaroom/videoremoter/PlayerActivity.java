package com.sunmnet.mediaroom.videoremoter;

import android.app.Activity;
import android.os.Bundle;

public class PlayerActivity extends Activity {
    //IjkVideoView mVideoView;

    /*VlcVideoView mVideoView;
    String mVideoPath;
    MediaListenerEvent listenerEvent = new MediaListenerEvent() {
        @Override
        public void eventBuffing(int event, float buffing) {

        }

        @Override
        public void eventStop(boolean isPlayError) {

        }

        @Override
        public void eventError(int event, boolean show) {
            RunningLog.debug("VLC播放出错：" + mVideoPath);
        }

        @Override
        public void eventPlay(boolean isPlaying) {
            RunningLog.debug("VLC.isplaying：" + isPlaying);
        }

        @Override
        public void eventPlayInit(boolean openClose) {
        }
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        /*IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        this.mVideoView = findViewById(R.id.vlc);*/

        /*mMediaController = new AndroidMediaController(getApplicationContext(), false);
        mMediaController.setShowable(false);
        mMediaController.setPrevNextListeners(onNextClickListener, onPrevClickListener);

        mVideoView.setMediaController(mMediaController);
        mVideoView.setOnCompletionListener(onCompletionListener);
        mVideoView.setOnErrorListener(onErrorListener);*/
       // mVideoView.setRender(IjkVideoView.RENDER_TEXTURE_VIEW);

    }

    @Override
        protected void onResume() {
            super.onResume();
    String path = getIntent().getStringExtra("url");
            System.out.println(path);
           // mVideoView.setVideoPath(path);
            //mVideoView.start();
}

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }
}
