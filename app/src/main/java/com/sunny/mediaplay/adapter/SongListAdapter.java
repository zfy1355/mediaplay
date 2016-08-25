package com.sunny.mediaplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunny.mediaplay.R;
import com.sunny.mediaplay.model.Song;

import java.util.List;

/**
 * Created by home on 2016/8/25.
 */
public class SongListAdapter extends BaseAdapter {
    private List<Song> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public SongListAdapter(Context context,List<Song> data){
        this.context=context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SongHolder song = null;
        if(convertView == null){
            song = new SongHolder();
            convertView = layoutInflater.inflate(R.layout.song_item_list,null);
            song.name = (TextView)convertView.findViewById(R.id.media_name);
            song.author = (TextView)convertView.findViewById(R.id.media_author);
            song.url = (TextView)convertView.findViewById(R.id.media_url);
            convertView.setTag(song);
        }else{
            song = (SongHolder)convertView.getTag();
        }
        song.name.setText(data.get(position).getName());
        song.author.setText(data.get(position).getAuthor());
        song.url.setText(data.get(position).getUrl());
        return convertView;
    }

    class SongHolder{
        public TextView name;
        public TextView author;
        public TextView url;
    }
}
