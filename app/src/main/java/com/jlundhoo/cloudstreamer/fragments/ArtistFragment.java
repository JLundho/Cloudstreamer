package com.jlundhoo.cloudstreamer.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.jlundhoo.cloudstreamer.Utility;
import com.jlundhoo.cloudstreamer.activities.TopTenTrackActivity;
import com.jlundhoo.cloudstreamer.adapters.ArtistAdapter;
import com.jlundhoo.cloudstreamer.spotify.SearchArtist;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Artist;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistFragment extends Fragment {

    public static final String ARTIST_NAME = "artist_name";
    public static final String ARTIST_ID = "artist_id";
    private static final String ARTISTLIST_PARCEL = "artistlist_parcel";

    private EditText searchFieldEditText;
    private ListView artistListView;
    private ImageView searchImageView;

    //ArrayList of downsized-artist objects, used to persist data between device reconfigurations.
    public static ArrayList<SimpleArtist> parcelableArtistList = new ArrayList<SimpleArtist>();
    public static ArrayList <Artist> artistSearchResult = new ArrayList<Artist>();

    public static ArtistAdapter mAdapter;

    private ConnectivityManager networkChecker;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARTISTLIST_PARCEL, parcelableArtistList);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            parcelableArtistList = savedInstanceState.getParcelableArrayList(ARTISTLIST_PARCEL);

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
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        networkChecker = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        View rootView = inflater.inflate(R.layout.fragment_artist, container, false);
        searchFieldEditText = (EditText) rootView.findViewById(R.id.editTextSearch);
        artistListView = (ListView) rootView.findViewById(R.id.artistLV);
        searchImageView = (ImageView) rootView.findViewById(R.id.searchImageView);

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = searchFieldEditText.getText().toString();

                if(!searchString.trim().isEmpty() && Utility.isOnline()){
                    SearchArtist mSearchClass = new SearchArtist(getActivity());
                    mSearchClass.execute(searchString);
                } else if (!Utility.isOnline()){
                    Toast toast = Toast.makeText(getActivity(), "No internet connection available", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Please search for an artist", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        mAdapter = new ArtistAdapter(getActivity(), artistSearchResult);
        artistListView.setAdapter(mAdapter);

        artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(Utility.isOnline()){
                    Artist selectedArtist = mAdapter.getItemById(position);

                    Intent intent = new Intent(getActivity(), TopTenTrackActivity.class)
                            .putExtra(Intent.EXTRA_TEXT, selectedArtist.name);
                    intent.putExtra(ARTIST_ID, selectedArtist.id);
                    intent.putExtra(ARTIST_NAME, selectedArtist.name);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getActivity(), "No internet connection available", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return rootView;
    }

    public static void addArtistsToAdapter(){
            //Clear out old artists in ListView to make room for new
            mAdapter.clear();
            parcelableArtistList.clear();

            for(Artist artist : artistSearchResult){
                mAdapter.add(artist);
            }
            mAdapter.notifyDataSetChanged();
    }


}


