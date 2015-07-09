package com.jlundhoo.cloudstreamer.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.jlundhoo.cloudstreamer.R;
import com.jlundhoo.cloudstreamer.SimpleArtist;
import com.jlundhoo.cloudstreamer.activities.TopTenTrackActivity;
import com.jlundhoo.cloudstreamer.adapters.ArtistAdapter;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistFragment extends Fragment {

    private String searchString;

    private EditText editTextSearch;
    private ListView artistListView;
    private ImageView searchImageView;

    private ArrayList<Artist> artistSearchResult = new ArrayList<Artist>();
    //ArrayList of downsized-artist objects, used to persist data between device reconfigurations.
    private ArrayList<SimpleArtist> parcelableArtistList = new ArrayList<SimpleArtist>();

    private ArtistAdapter mAdapter;

    public static final String ARTIST_NAME = "artist_name";
    public static final String ARTIST_ID = "artist_id";
    static final String ARTISTLIST_PARCEL = "artistlist_parcel";


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARTISTLIST_PARCEL, parcelableArtistList);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            ArrayList<SimpleArtist> restoredArtists = savedInstanceState.getParcelableArrayList(ARTISTLIST_PARCEL);
            parcelableArtistList = restoredArtists;

            for(SimpleArtist simpleArtist : parcelableArtistList) {
                Artist mArtist = new Artist();
                mArtist.id = simpleArtist.id;
                mArtist.name = simpleArtist.name;
                mArtist.images = simpleArtist.images;

                mAdapter.add(mArtist);
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        Log.i(this.getClass().getCanonicalName(), "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_artist, container, false);
        editTextSearch = (EditText) rootView.findViewById(R.id.editTextSearch);
        artistListView = (ListView) rootView.findViewById(R.id.artistLV);
        searchImageView = (ImageView) rootView.findViewById(R.id.searchImageView);

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchString = editTextSearch.getText().toString();

                //Clear out old artists in ListView to make room for new
                mAdapter.clear();
                SearchArtist mSearchClass = new SearchArtist();
                mSearchClass.execute(searchString);
            }
        });
        mAdapter = new ArtistAdapter(getActivity(), artistSearchResult);
        artistListView.setAdapter(mAdapter);

        artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist selectedArtist = mAdapter.getItemById(position);

                Intent intent = new Intent(getActivity(), TopTenTrackActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, selectedArtist.name);
                intent.putExtra(ARTIST_ID, selectedArtist.id);
                intent.putExtra(ARTIST_NAME, selectedArtist.name);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public class SearchArtist extends AsyncTask<String, Void, ArtistsPager>{
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
        ArtistsPager myArtistPager;

        @Override
        protected ArtistsPager doInBackground(String...params) {
            String searchString = params[0];
            return spotify.searchArtists(searchString);
        }

        @Override
        protected void onPostExecute(ArtistsPager artistPager) {
            super.onPostExecute(artistPager);
            myArtistPager = artistPager;
            artistSearchResult = (ArrayList)artistPager.artists.items;

            if(artistSearchResult.isEmpty()) {
                Toast.makeText(getActivity(), "No artist found", Toast.LENGTH_SHORT).show();
                return;
            }

            //Clears previously saved artists, so as not to stack them on the old list.
            parcelableArtistList.clear();

            for (int i = 0; i < artistSearchResult.size(); i++) {
                mAdapter.add(artistSearchResult.get(i));
                //Adds search-results to parcelable ArrayList, so they can be restored on device reconfiguration
                SimpleArtist mSimpleArtist = new SimpleArtist();

                mSimpleArtist.id = artistSearchResult.get(i).id;
                mSimpleArtist.name = artistSearchResult.get(i).name;
                mSimpleArtist.images = artistSearchResult.get(i).images;

                parcelableArtistList.add(mSimpleArtist);
            }
            mAdapter.notifyDataSetChanged();

        }
    }
}


