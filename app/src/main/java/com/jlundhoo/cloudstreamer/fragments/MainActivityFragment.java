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
import android.widget.TextView;
import android.widget.Toast;

import com.jlundhoo.cloudstreamer.ParcelableArtist;
import com.jlundhoo.cloudstreamer.R;
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
public class MainActivityFragment extends Fragment {

    private TextView welcomeTV;
    private EditText editTextSearch;
    private ListView artistListView;
    private ImageView searchImageView;

    private ArrayList<Artist> artistSearchResult = new ArrayList<Artist>();

    //ArrayList of downsized-artist objects, used to persist data between device reconfigurations.
    private ArrayList<ParcelableArtist> parcelableArtistList = new ArrayList<ParcelableArtist>();

    private ArtistAdapter mAdapter; //Create own adapter for additional control

    private String searchString;

    static final String ARTIST_TAG = "artist";
    static final String ARTIST_ID = "artist_id";

    public MainActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(getString(R.string.ARTISTLIST_PARCEL), parcelableArtistList);
    }

    /**
     * Good idea, but work on adding whole Artist-objects to Parcelable.
     */
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            ArrayList<ParcelableArtist> restoredArtists = savedInstanceState.getParcelableArrayList(getString(R.string.ARTISTLIST_PARCEL));
            for(ParcelableArtist pArtist : restoredArtists){

                Artist mArtist = new Artist();
                mArtist.id = pArtist.getArtistID();
                mArtist.name = pArtist.getArtistName();
                mArtist.images.get(0).url = pArtist.getArtistImageURL();
                
                artistSearchResult.add(mArtist);
            }

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //createMockData();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        welcomeTV = (TextView) rootView.findViewById(R.id.welcomeTV);
        editTextSearch = (EditText) rootView.findViewById(R.id.editTextSearch);
        artistListView = (ListView) rootView.findViewById(R.id.artistLV);
        searchImageView = (ImageView) rootView.findViewById(R.id.searchImageView);

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchString = editTextSearch.getText().toString();

                //Clear out old artists in ListView to make room for new
                mAdapter.clear();
                SearchClass mSearchClass = new SearchClass();
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
                intent.putExtra(ARTIST_TAG, selectedArtist.name);
                startActivity(intent);
            }
        });



        return rootView;
    }

    public class SearchClass extends AsyncTask<String, Void, ArtistsPager>{
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

            Log.i("Artist", String.valueOf(artistSearchResult.size()));
            if (artistSearchResult.size() > 0) {
                //Adds search-results to Adapter for current use

                mAdapter.addArtists(artistSearchResult);

                for (int i = 0; i < artistSearchResult.size(); i++) {
                    //Adds search-results to parcelable ArrayList, so they can be restored on device reconfiguration
                    addParcelableArtist(artistSearchResult.get(i));
                }
                //notifyDataSetChanged is called internally within adapter.
            } else {
                Toast errorToast = Toast.makeText(getActivity(), "No results found for "+searchString, Toast.LENGTH_SHORT);
                errorToast.show();
            }

        }

    }

    private void addParcelableArtist(Artist artist){
        try{
            ParcelableArtist mParcelableArtist = new ParcelableArtist();
            mParcelableArtist.setArtistID(artist.id);
            mParcelableArtist.setArtistName(artist.name);
            mParcelableArtist.setArtistImageURL(artist.images.get(0).url);

            parcelableArtistList.add(mParcelableArtist);
        } catch (Exception e){
            e.printStackTrace();


        }
    }


}


