package com.jlundhoo.cloudstreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by jlundhol on 2015-06-20.
 */
public class TopTenAdapter extends ArrayAdapter{

    private List<Track> topTenTrackList;

    public TopTenAdapter(Context ctx, List<Track> topTenTrackList){
        super(ctx, 0);
        this.topTenTrackList = topTenTrackList;
    }

    @Override
    public void clear() {
        topTenTrackList.clear();
    }

    public void add(Track mTrack){
        topTenTrackList.add(mTrack);
    }

    @Override
    public int getCount() {
        return topTenTrackList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder trackHolder;
        if(convertView == null){    //View has not been initialized, create view from layout-XML and set tags

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_topten, parent, false);

            trackHolder = new ViewHolder();
            trackHolder.trackName = (TextView) convertView.findViewById(R.id.songNameTV);
            trackHolder.albumName = (TextView) convertView.findViewById(R.id.albumNameTV);
            trackHolder.albumImage = (ImageView) convertView.findViewById(R.id.albumImageIV);

            convertView.setTag(trackHolder);
        } else{     //View has been initialized, get view-information directly from View(through getTag)
            trackHolder = (ViewHolder) convertView.getTag();
        }

        Track mTrack = (Track) topTenTrackList.get(position);
        if(mTrack.name != null){
            trackHolder.trackName.setText(mTrack.name);
        } else {
            trackHolder.trackName.setText("No track title");
        }

        if(mTrack.album != null){
            trackHolder.albumName.setText(mTrack.album.name);
        } else {
            trackHolder.albumName.setText("No album title");
        }

        return convertView;
    }

    static class ViewHolder {
        TextView trackName;
        TextView albumName;
        ImageView albumImage; //Not yet implemented
    }

}
