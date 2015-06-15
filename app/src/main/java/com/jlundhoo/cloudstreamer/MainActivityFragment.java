package com.jlundhoo.cloudstreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private TextView welcomeTV;
    private EditText editTextSearch;
    private ListView artistListView;

    private List<String> artistListMock;

    private MyAdapter mAdapter; //Create own adapter for additional control

    public MainActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        artistListMock = new ArrayList<String>(Arrays.asList("Coldplay - Warmplay", "Córdoba - Aruba", "La Plata - Patata"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mAdapter = new MyAdapter(getActivity(), artistListMock);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        welcomeTV = (TextView)rootView.findViewById(R.id.welcomeTV);
        editTextSearch = (EditText)rootView.findViewById(R.id.editTextSearch);
        artistListView = (ListView)rootView.findViewById(R.id.artistLV);


        artistListView.setAdapter(mAdapter);

        return rootView;
    }
}
