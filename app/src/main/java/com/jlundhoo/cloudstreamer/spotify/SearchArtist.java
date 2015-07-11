package com.jlundhoo.cloudstreamer.spotify;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.jlundhoo.cloudstreamer.SimpleArtist;
import com.jlundhoo.cloudstreamer.fragments.ArtistFragment;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by jlundhol on 2015-07-11.
 */
public class SearchArtist extends AsyncTask<String, Void, ArtistsPager> {

    private ArrayList<Artist> searchResult = new ArrayList<Artist>();

    SpotifyApi api = new SpotifyApi();
    SpotifyService spotify = api.getService();
    ArtistsPager myArtistPager;

    Context mContext;
    @Override
    protected ArtistsPager doInBackground(String...params) {
        String searchString = params[0];
        return spotify.searchArtists(searchString);
    }

    public SearchArtist(Context context){
        this.mContext = context;
    }

    @Override
    protected void onPostExecute(ArtistsPager artistPager) {
        super.onPostExecute(artistPager);
        myArtistPager = artistPager;
        searchResult = (ArrayList)artistPager.artists.items;
        //Clears previously saved artists, so as not to stack them on the old list.

        if(searchResult.isEmpty()) {
            Toast.makeText(mContext, "No artist found", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < searchResult.size(); i++) {
                //Adds search-results to parcelable ArrayList, so they can be restored on device reconfiguration
                SimpleArtist mSimpleArtist = new SimpleArtist();

                mSimpleArtist.id = searchResult.get(i).id;
                mSimpleArtist.name = searchResult.get(i).name;
                mSimpleArtist.images = searchResult.get(i).images;

                ArtistFragment.parcelableArtistList.add(mSimpleArtist);
            }
            ArtistFragment.artistSearchResult = searchResult;
            ArtistFragment.addArtistsToAdapter();
        }

    }
}
