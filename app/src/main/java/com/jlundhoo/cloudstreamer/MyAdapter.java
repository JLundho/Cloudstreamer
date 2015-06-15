package com.jlundhoo.cloudstreamer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlundhol on 2015-06-15.
 */
public class MyAdapter extends ArrayAdapter{

    private List artistListMock = new ArrayList<String>();

    public MyAdapter(Context ctx, List<String> artistList){
        super(ctx, 0);
        artistListMock = artistList;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {



        return null;
    }



}
