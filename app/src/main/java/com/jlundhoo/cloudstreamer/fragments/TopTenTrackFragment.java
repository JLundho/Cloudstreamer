package com.jlundhoo.cloudstreamer.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jlundhoo.cloudstreamer.R;
import com.jlundhoo.cloudstreamer.SimpleTrack;
import com.jlundhoo.cloudstreamer.activities.TrackDetailActivity;
import com.jlundhoo.cloudstreamer.adapters.TopTenAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopTenTrackFragment extends Fragment {

    private TextView artistNameTV;
    private ListView topTenTrackLV;

    private List<Track> topTenTrackList;

    //ArrayList of downsized-artist objects, used to persist data between device reconfigurations.
    private ArrayList<SimpleTrack> parcelableTrackList = new ArrayList<SimpleTrack>();

    private TopTenAdapter mAdapter;

    private String artistName;
    private String artistID;

    private static String TRACKLIST_PARCEL = "tracklist_parcel";

    private static String TRACK_NAME = "track_name";
    private static String ALBUM_NAME = "album_name";
    private static String ALBUM_IMAGE_URL = "album_image_url";

    Map<String, Object> countryMap = new HashMap<>();


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Persist downsized Track-objects with necessary information after device reconfiguration
        //outState.putParcelableArrayList(TRACKLIST_PARCEL, parcelableTrackList);
    }

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
                String url = selectedTrack.album.images.get(0).url;             //URL for the album image

                Intent intent = new Intent(getActivity(), TrackDetailActivity.class);
                intent.putExtra(TRACK_NAME, selectedTrack.name);
                intent.putExtra(ALBUM_NAME, selectedTrack.album.name);
                intent.putExtra(ALBUM_IMAGE_URL, url);
                startActivity(intent);
            }
        });

        //Get top ten (or less) tracks, for given artist, using the US-locale.
        countryMap.put("country", "US");
        SearchTopTenTracks mSearchTopTenTracks = new SearchTopTenTracks();
        mSearchTopTenTracks.execute(artistID);

        return rootView;
    }


    public class SearchTopTenTracks extends AsyncTask<String, Void, Tracks> {
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();

        @Override
        protected Tracks doInBackground(String...params) {
            String artistID = params[0];
            return spotify.getArtistTopTrack(artistID, countryMap);
        }

        @Override
        protected void onPostExecute(Tracks tracks) {
            super.onPostExecute(tracks);

            if(tracks.tracks.size() < 1){
                getActivity().finish();
                Toast toast = Toast.makeText(getActivity(), "No top tracks found", Toast.LENGTH_SHORT);
                toast.show();
            }
            topTenTrackList = (ArrayList)tracks.tracks;

            for (int i = 0; i < topTenTrackList.size() | i == 9; i++) {
                mAdapter.add(topTenTrackList.get(i));
                //Adds search-results to parcelable ArrayList, so they can be restored on device reconfiguration
                SimpleTrack mSimpleTrack = new SimpleTrack();

                mSimpleTrack.name = topTenTrackList.get(i).name;
                mSimpleTrack.album = topTenTrackList.get(i).album;
                mSimpleTrack.album.images = topTenTrackList.get(i).album.images;

                parcelableTrackList.add(mSimpleTrack);
            }
            mAdapter.notifyDataSetChanged();

        }

    }

}
