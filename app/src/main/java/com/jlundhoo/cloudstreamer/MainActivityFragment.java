package com.jlundhoo.cloudstreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private TextView welcomeTV;
    private EditText editTextSearch;
    private ListView artistListView;

    private List<Album> albumListMock = new ArrayList<Album>();

    private MyAdapter mAdapter; //Create own adapter for additional control


    public MainActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        createMockData();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        welcomeTV = (TextView)rootView.findViewById(R.id.welcomeTV);
        editTextSearch = (EditText) rootView.findViewById(R.id.editTextSearch);
        artistListView = (ListView) rootView.findViewById(R.id.artistLV);

        createMockData();
        mAdapter = new MyAdapter(getActivity(), albumListMock);


        artistListView.setAdapter(mAdapter);

        artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast myToast = Toast.makeText(getActivity(), mAdapter.getItemById(position).getArtistName(), Toast.LENGTH_SHORT);
                myToast.show();
            }
        });

        Log.i(getString(R.string.LOG_TAG), "" + mAdapter.getCount());
        return rootView;
    }
    private void createMockData() {
        Album myAlbum1 = new Album("Hej", "Grej");
        Album myAlbum2 = new Album("Coldwater", "Warmwater");
        Album myAlbum3 = new Album("Album", "Artist");

        albumListMock.add(myAlbum1);
        albumListMock.add(myAlbum2);
        albumListMock.add(myAlbum3);
    }
}
