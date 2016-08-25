package com.sunny.mediaplay;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunny.mediaplay.adapter.SongListAdapter;
import com.sunny.mediaplay.model.Song;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by home on 2016/8/24.
 */
public class MediaPlayActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static String Tag = "MediaPlayActivity";
    private Button b_play ;
    private Button b_stop;
    private Button b_finish;
    private Button b_replay;
    private Button b_refresh;
    private Button b_back;
    private Button b_quick;
    private ListView song_list;
    private SongListAdapter adapter;
    private List<Song> data = new ArrayList<Song>();
    private  MediaPlayer mediaPlayer ;
    private final int JUMP_MILS = 5000;


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
        b_refresh = (Button)findViewById(R.id.media_refresh);
        b_quick = (Button)findViewById(R.id.media_quick);
        b_back = (Button)findViewById(R.id.media_back);
        b_replay.setOnClickListener(this);
        b_finish.setOnClickListener(this);
        b_play.setOnClickListener(this);
        b_stop.setOnClickListener(this);
        b_refresh.setOnClickListener(this);
        b_quick.setOnClickListener(this);
        b_back.setOnClickListener(this);

        song_list = (ListView)findViewById(R.id.song_list);
        adapter = new SongListAdapter(this,data);
        song_list.setAdapter(adapter);
        song_list.setOnItemClickListener(this);

        initTable(false);
    }




    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.media_play:
                Log.d(Tag,"play");
                if(mediaPlayer == null)
                    mediaPlayer =  new MediaPlayer();
                try {
                    if(mediaPlayer.getCurrentPosition()>0)
                        mediaPlayer.start();
                    else if(data.size()>0){
                        mediaPlayer.setDataSource(data.get(0).getUrl());
                        mediaPlayer.prepare();
                    }else{
                        Toast.makeText(getApplicationContext(),"There is no media to Play!",Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                initTable(true);
                break;
            case R.id.media_stop:
                if(mediaPlayer.isPlaying()) {
                    Log.d(Tag,"pause");
                    mediaPlayer.pause();
                }
                initTable(false);
                break;
            case R.id.media_replay:
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
                Log.d(Tag, "replay");
                initTable(true);
                break;
            case R.id.media_quick:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+JUMP_MILS);
                break;
            case R.id.media_back:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-JUMP_MILS);
                break;
            case R.id.media_refresh:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        getLocalSong(data,Environment.getExternalStorageDirectory());// 刷新音乐文件
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
            case R.id.media_finish:
                Log.d(Tag, "exit");
                exit();
                break;
        }
    }

    private void getLocalSong(final List<Song> list, File file) {
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                // sdCard找到视频名称
                String name = file.getName();

                int i = name.indexOf('.');
                if (i != -1) {
                    name = name.substring(i);
                    if(name.equalsIgnoreCase(".mp3")){
                        Song s = new Song();
                        s.setName(file.getName());
                        s.setAuthor("Unkwon");
                        s.setUrl(file.getAbsolutePath());
                        list.add(s);
                        return true;
                    }
                } else if (file.isDirectory()) {
                    getLocalSong(list, file);
                }
                return false;
            }
        });
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView song_url = (TextView)view.findViewById(R.id.media_url);
        if(mediaPlayer == null)
            mediaPlayer =  new MediaPlayer();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(song_url.getText().toString());
            mediaPlayer.prepare();
            mediaPlayer.start();
            initTable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTable(boolean isPlay){
        if(isPlay){
            b_play.setEnabled(false);
            b_stop.setEnabled(true);
            b_quick.setEnabled(true);
            b_back.setEnabled(true);
        }else{
            b_quick.setEnabled(false);
            b_back.setEnabled(false);
            b_stop.setEnabled(false);
            b_play.setEnabled(true);
        }
    }

}
