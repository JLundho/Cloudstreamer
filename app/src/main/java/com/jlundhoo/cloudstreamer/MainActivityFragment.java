package com.jlundhoo.cloudstreamer;

import android.net.Uri;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Album;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private TextView welcomeTV;
    private EditText editTextSearch;
    private ListView artistListView;
    private ImageView searchImageView;

    private Album newAlbum;

    private List<Album> albumListMock = new ArrayList<Album>();

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

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String artistName = editTextSearch.getText().toString();
                searchForArtist(artistName, "artist");
                Log.i(getString(R.string.LOG_TAG), artistName);
            }
        });

        //mAdapter = new MyAdapter(getActivity(), albumListMock);
        artistListView.setAdapter(mAdapter);
        artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast myToast = Toast.makeText(getActivity(), mAdapter.getItemById(position).getArtistName(), Toast.LENGTH_SHORT);
                //myToast.show();
            }
        });

        return rootView;
    }

    private String searchForArtist(String searchString, String type) {

        final String SPOTIFY_BASE_URL = "https://api.spotify.com/v1/search";
        final String QUERY_PARAM = "q";
        final String TYPE_PARAM = "type";

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        URL url = null;

        String artistJsonStr = null;

        try {
            Uri buildUri = Uri.parse(SPOTIFY_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, searchString)   //Location in sharedPreference
                    .appendQueryParameter(TYPE_PARAM, type)
                    .build();

            try {
                url = new URL(buildUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            //Read input stream to string
            InputStream inputStream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            //Wraps the inputstream in a buffered reader, in order to read small chunks of data continuously
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }

            artistJsonStr = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Disconnects network connections and closes readers to prevent memory-leaks
        finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return artistJsonStr;
    }




        //Create HTTP-connection, will I need authorisation?
        /*SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();

        spotify.getArtist("ID", new Callback<Artist>() {
            @Override
            public void success(Artist artist, Response response) {
                Log.d("Artist success", response.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Album failure", error.toString());
            }
        });*/



        //Construct query

        //Return results
    /*private void createMockData() {
        Album myAlbum1 = new Album("Hej", "Grej");
        Album myAlbum2 = new Album("Coldwater", "Warmwater");
        Album myAlbum3 = new Album("Album", "Artist");

        albumListMock.add(myAlbum1);
        albumListMock.add(myAlbum2);
        albumListMock.add(myAlbum3);
    }*/
}
