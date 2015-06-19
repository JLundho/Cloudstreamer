package com.jlundhoo.cloudstreamer;

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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.android.MainThreadExecutor;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private TextView welcomeTV;
    private EditText editTextSearch;
    private ListView artistListView;
    private ImageView searchImageView;

    private Album newAlbum;

    private List<Artist> artistSearchResult = new ArrayList<Artist>();


    private MyAdapter mAdapter; //Create own adapter for additional control


    public MainActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        mAdapter = new MyAdapter(getActivity(), artistSearchResult);
        artistListView.setAdapter(mAdapter);

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = editTextSearch.getText().toString();
                searchForArtist(searchString);
            }
        });

        artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast myToast = Toast.makeText(getActivity(), mAdapter.getItemById(position).name, Toast.LENGTH_SHORT);
                myToast.show();
            }
        });
        return rootView;
    }

    private void searchForArtist(String searchString){

        SpotifyApi api = new SpotifyApi(Executors.newSingleThreadExecutor(),
                new MainThreadExecutor());
        SpotifyService spotify = api.getService();
        spotify.searchArtists(searchString, new Callback<ArtistsPager>() {
            @Override
            public void success(ArtistsPager artistsPager, Response response) {
                artistSearchResult = artistsPager.artists.items;
                mAdapter.addArtists(artistSearchResult);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(getString(R.string.LOG_TAG), error.toString());
            }
        });

    }


}


