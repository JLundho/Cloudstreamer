package com.jlundhoo.cloudstreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
    private TopTenAdapter mAdapter;

    private String artistName;
    private String artistID;

    Map<String, Object> countryMap = new HashMap<>();


    private String countryName = "Sweden";
    private String countryCode= "SE";

    static final String ARTIST_TAG = "artist";

    public TopTenTrackFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        topTenTrackList = new ArrayList<Track>();
        Intent intent = getActivity().getIntent();  //Retrieves the activity, to receive the context from which to get intent
        artistName = intent.getStringExtra(getString(R.string.ARTIST_NAME));
        artistID = intent.getStringExtra(getString(R.string.ARTIST_ID));

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
            for (Track track : tracks.tracks)
            {
                mAdapter.add(track);
            }
            mAdapter.notifyDataSetChanged();
        }

    }

}
