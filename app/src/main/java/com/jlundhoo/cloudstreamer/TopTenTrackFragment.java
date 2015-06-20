package com.jlundhoo.cloudstreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Track;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopTenTrackFragment extends Fragment {

    private TextView artistNameTV;
    private ListView topTenTrackLV;

    private List<Track> topTenTrackList;
    private TopTenAdapter mAdapter;

    private String artistName;
    static final String ARTIST_TAG = "artist";

    public TopTenTrackFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        topTenTrackList = new ArrayList<Track>();
        Intent intent = getActivity().getIntent();  //Retrieves the activity, to receive the context from which to get intent
        artistName = intent.getStringExtra(Intent.EXTRA_TEXT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //rootView is created by inflating the main-view XML for that Activity/fragment
        View rootView = inflater.inflate(R.layout.fragment_toptentracks, container, false);

        artistNameTV = (TextView) rootView.findViewById(R.id.topTenArtistTV);
        topTenTrackLV = (ListView) rootView.findViewById(R.id.topTenTrackLV);

        artistNameTV.setText(artistName);

        mAdapter = new TopTenAdapter(getActivity(), topTenTrackList);
        topTenTrackLV.setAdapter(mAdapter);

        return inflater.inflate(R.layout.fragment_toptentracks, container, false);
    }
}
