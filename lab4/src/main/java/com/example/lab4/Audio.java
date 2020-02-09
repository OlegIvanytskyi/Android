package com.example.lab4;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class Audio extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    final String DATA_STREAM = "http://www.hochmuth.com/mp3/Vivaldi_Sonata_eminor_.mp3";

    MediaPlayer mediaPlayer;
    AudioManager am;
    Button moveForw, moveBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        am = (AudioManager) getSystemService(AUDIO_SERVICE);

        moveForw = findViewById(R.id.forward);
        moveBack = findViewById(R.id.back);
    }

    public void onClickStart(View view) {
        releaseMP();

        try {
            switch (view.getId()) {
                case R.id.internet:
                    moveBack.setEnabled(false);
                    moveForw.setEnabled(false);

                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(DATA_STREAM);
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.prepareAsync();
                    break;
                case R.id.downloaded:
                    moveBack.setEnabled(true);
                    moveForw.setEnabled(true);

                    mediaPlayer = MediaPlayer.create(this, R.raw.adele_hello);
                    mediaPlayer.start();
                    break;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mediaPlayer == null)
            return;
    }

    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClick(View view) {
        if (mediaPlayer == null)
            return;
        switch (view.getId()) {
            case R.id.pauseVideo:
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                break;
            case R.id.resume:
                if (!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                break;
            case R.id.stopVideo:
                mediaPlayer.stop();

                moveBack.setEnabled(true);
                moveForw.setEnabled(true);
                break;
            case R.id.back:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 3000);
                break;
            case R.id.forward:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 3000);
                break;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        moveBack.setEnabled(true);
        moveForw.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }
}
