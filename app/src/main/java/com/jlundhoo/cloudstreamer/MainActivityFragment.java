package com.jlundhoo.cloudstreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
        mAdapter = new MyAdapter(getActivity(), albumListMock);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        welcomeTV = (TextView)rootView.findViewById(R.id.welcomeTV);
        try {
            editTextSearch = (EditText) rootView.findViewById(R.id.editTextSearch);
            artistListView = (ListView) rootView.findViewById(R.id.artistLV);
            artistListView.setAdapter(mAdapter);
        } catch (Exception e){
            Log.i(getString(R.string.LOG_TAG), e.toString());
            e.printStackTrace();
        }


        return rootView;
    }
}
