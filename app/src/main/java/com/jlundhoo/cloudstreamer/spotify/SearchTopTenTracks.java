package com.jlundhoo.cloudstreamer.spotify;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.jlundhoo.cloudstreamer.SimpleTrack;
import com.jlundhoo.cloudstreamer.fragments.TopTenTrackFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by jlundhol on 2015-07-11.
 */
public class SearchTopTenTracks extends AsyncTask<String, Void, Tracks> {
    private SpotifyApi api = new SpotifyApi();
    private SpotifyService spotify = api.getService();

    private Map<String, Object> countryMap = new HashMap<>();
    private Context mContext;

    private ArrayList<Track> searchResult;

    public SearchTopTenTracks(Context context){
        mContext = context;
        countryMap.put("country", "US");
    }

    @Override
    protected Tracks doInBackground(String...params) {
        String artistID = params[0];
        return spotify.getArtistTopTrack(artistID, countryMap);
    }

    @Override
    protected void onPostExecute(Tracks tracks) {
        super.onPostExecute(tracks);

        searchResult = (ArrayList)tracks.tracks;

        if(searchResult.isEmpty()){
            Toast toast = Toast.makeText(mContext, "No top tracks found", Toast.LENGTH_SHORT);
            toast.show();
        } else{
            for (int i = 0; i < searchResult.size(); i++) {
                //Adds search-results to parcelable ArrayList, so they can be restored on device reconfiguration
                SimpleTrack mSimpleTrack = new SimpleTrack();

                mSimpleTrack.name = searchResult.get(i).name;
                mSimpleTrack.album = searchResult.get(i).album;
                mSimpleTrack.album.images = searchResult.get(i).album.images;

                TopTenTrackFragment.parcelableTrackList.add(mSimpleTrack);
            }
            TopTenTrackFragment.topTenTrackList = searchResult;
            TopTenTrackFragment.addTracksToAdapter();
        }
    }

}
