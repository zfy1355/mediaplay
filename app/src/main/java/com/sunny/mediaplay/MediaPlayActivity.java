package com.sunny.mediaplay;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

/**
 * Created by home on 2016/8/24.
 */
public class MediaPlayActivity extends AppCompatActivity implements View.OnClickListener {
    private static String Tag = "MediaPlayActivity";
    private Button b_play ;
    private Button b_stop;
    private Button b_finish;
    private Button b_replay;
    private  MediaPlayer mediaPlayer ;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_activity);
        initView();
    }

    private void initView() {
        b_play = (Button)findViewById(R.id.media_play);
        b_stop = (Button)findViewById(R.id.media_stop);
        b_finish = (Button)findViewById(R.id.media_finish);
        b_replay = (Button)findViewById(R.id.media_replay);
        b_replay.setOnClickListener(this);
        b_finish.setOnClickListener(this);
        b_play.setOnClickListener(this);
        b_stop.setOnClickListener(this);
        b_stop.setEnabled(false);

        mediaPlayer =  new MediaPlayer();
        try {
            //        mediaPlayer = MediaPlayer.create(this,R.raw.music_1);
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+File.separator+"1.mp3");
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        mediaPlayer.setWakeMode(this, PowerManager.ON_AFTER_RELEASE);
        mediaPlayer.setLooping(true);
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.media_play:
                Log.i(Tag,"play");
                mediaPlayer.start();
                b_play.setEnabled(false);
                b_stop.setEnabled(true);
                break;
            case R.id.media_stop:
                if(mediaPlayer.isPlaying()) {
                    Log.i(Tag,"pause");
                    mediaPlayer.pause();
                }
                b_play.setEnabled(true);
                b_stop.setEnabled(false);
                break;
            case R.id.media_replay:
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
                Log.i(Tag, "replay");
                b_play.setEnabled(false);
                b_stop.setEnabled(true);
                break;
            case R.id.media_finish:
                Log.i(Tag, "exit");
                exit();
                break;
        }
    }

    private void exit() {
        new AlertDialog.Builder(this).setTitle("Are you sure to exit?")
                .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        MediaPlayActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

    }

}
