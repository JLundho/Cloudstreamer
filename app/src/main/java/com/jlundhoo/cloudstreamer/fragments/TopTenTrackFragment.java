package com.jlundhoo.cloudstreamer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jlundhoo.cloudstreamer.R;
import com.jlundhoo.cloudstreamer.spotify.SearchTopTenTracks;
import com.jlundhoo.cloudstreamer.SimpleTrack;
import com.jlundhoo.cloudstreamer.activities.TrackDetailActivity;
import com.jlundhoo.cloudstreamer.adapters.TopTenAdapter;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Track;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopTenTrackFragment extends Fragment {

    private static String TRACK_NAME = "track_name";
    private static String ALBUM_NAME = "album_name";
    private static String ALBUM_IMAGE_URL = "album_image_url";

    private TextView artistNameTV;
    private ListView topTenTrackLV;

    public static List<Track> topTenTrackList;
    public static TopTenAdapter mAdapter;
    //ArrayList of downsized-artist objects, used to persist data between device reconfigurations.
    public static ArrayList<SimpleTrack> parcelableTrackList = new ArrayList<SimpleTrack>();

    private String artistName;
    private String artistID;



    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        topTenTrackList = new ArrayList<Track>();
        Intent intent = getActivity().getIntent();  //Retrieves the activity, to receive the context from which to get intent
        this.artistName = intent.getStringExtra(ArtistFragment.ARTIST_NAME);
        this.artistID = intent.getStringExtra(ArtistFragment.ARTIST_ID);

        getActivity().setTitle(artistName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_toptentracks, container, false);
        topTenTrackLV = (ListView) rootView.findViewById(R.id.topTenTrackLV);

        mAdapter = new TopTenAdapter(getActivity(), topTenTrackList);
        topTenTrackLV.setAdapter(mAdapter);

        topTenTrackLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Track selectedTrack = (Track) mAdapter.getItemById(position);
                String albumImageURL = selectedTrack.album.images.get(0).url;

                Intent intent = new Intent(getActivity(), TrackDetailActivity.class);
                intent.putExtra(TRACK_NAME, selectedTrack.name);
                intent.putExtra(ALBUM_NAME, selectedTrack.album.name);
                intent.putExtra(ALBUM_IMAGE_URL, albumImageURL);
                startActivity(intent);
            }
        });

        //Get top ten (or less) tracks, for given artist, using the US-locale.
        SearchTopTenTracks mSearchTopTenTracks = new SearchTopTenTracks(getActivity());
        mSearchTopTenTracks.execute(artistID);

        return rootView;
    }

    public static void addTracksToAdapter(){
        //Clear out old artists in ListView to make room for new
        mAdapter.clear();
        parcelableTrackList.clear();

        for(Track track : topTenTrackList){
            mAdapter.add(track);
        }
        mAdapter.notifyDataSetChanged();
    }


}
