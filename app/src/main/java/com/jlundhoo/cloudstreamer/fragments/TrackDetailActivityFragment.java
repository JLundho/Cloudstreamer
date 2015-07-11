package com.jlundhoo.cloudstreamer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlundhoo.cloudstreamer.R;
import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class TrackDetailActivityFragment extends Fragment {

    private String TRACK_NAME = "track_name";
    private String ALBUM_NAME = "album_name";
    private String ALBUM_IMAGE_URL = "album_image_url";

    private String trackName;
    private String albumName;
    private String albumImageURL;

    private TextView trackNameTV;
    private TextView albumNameTV;
    private ImageView albumImageIV;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();

        albumImageURL = intent.getStringExtra(ALBUM_IMAGE_URL);
        trackName = intent.getStringExtra(TRACK_NAME);
        albumName = intent.getStringExtra(ALBUM_NAME);

        getActivity().setTitle(trackName);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_track_detail, container, false);

        albumImageIV = (ImageView)rootView.findViewById(R.id.DetailAlbumImageIV);
        trackNameTV = (TextView)rootView.findViewById(R.id.DetailTrackNameTV);
        albumNameTV = (TextView)rootView.findViewById(R.id.DetailAlbumNameTV);

        Picasso.with(getActivity().getApplicationContext())
                .load(albumImageURL)
                .placeholder(R.drawable.artist_placeholderimg)
                .into(albumImageIV);
        trackNameTV.setText(trackName);
        albumNameTV.setText(albumName);

        return rootView;
    }
}
