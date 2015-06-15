package com.jlundhoo.cloudstreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlundhol on 2015-06-15.
 */
public class MyAdapter extends ArrayAdapter{

    private List albumListMock = new ArrayList<Album>();

    private Album newAlbum;

    public MyAdapter(Context ctx, List<Album> albumList){
        super(ctx, 0);
        albumListMock = albumList;
    }

    /*
    //The adapter would inflate the layout for each row in its getView() method and assign the data to the individual views in the row.
    */

    //To large part copied from https://www.binpress.com/tutorial/smooth-out-your-listviews-with-a-viewholder/9
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder itemHolder;
        //Check if View has already been inflated and can be reused, otherwise inflate it.
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_artist, parent, false);

            itemHolder = new ViewHolder();
            itemHolder.artistName = (TextView) convertView.findViewById(R.id.artistTV); //Create textView for artist-name
            itemHolder.artistAlbum = (TextView) convertView.findViewById(R.id.albumTV);
            //itemHolder.artistImage = (TextView) convertView.findViewById(R.id.albumIV);

            convertView.setTag(itemHolder); //When a tag has been set, the convertView can retrieve information about the views from the tag in the future
        } else {
            itemHolder = (ViewHolder) convertView.getTag(); //All information about a view can be retrieved from the View-tag.
        }
        //Create widgets and inflate widget-ID

        itemHolder.artistName.setText("Grej"); //albumListMock.get(position).getArtistName);
        itemHolder.artistAlbum.setText("Nej"/*(albumListMock.get(position).getArtistAlbum*/);
        return convertView;
    }

    class ViewHolder {
        TextView artistName;
        TextView artistAlbum;
        ImageView artistImage; //Not yet implemented
    }



}
