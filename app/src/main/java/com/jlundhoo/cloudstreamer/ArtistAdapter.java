package com.jlundhoo.cloudstreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by jlundhol on 2015-06-15.
 */
public class ArtistAdapter extends ArrayAdapter{

    private List artistListMock;


    public ArtistAdapter(Context ctx, List<Artist> artistList){
        super(ctx, 0);
        artistListMock = artistList;
    }

    public void add(Artist mArtist){
        artistListMock.add(mArtist);
    }

    public void addArtists(List<Artist> artistList){
        for(int i = 0; i < artistList.size(); i++){
            artistListMock.add(artistList.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        super.clear();
        artistListMock.clear();
    }

    @Override
    public int getCount(){
        return artistListMock.size();
    }

    public Artist getItemById(int position){
        return (Artist)artistListMock.get(position);
    }

    /*
    //The adapter would inflate the layout for each row in its getView() method and assign the data to the individual views in the row.
    */

    //To large part copied from https://www.binpress.com/tutorial/smooth-out-your-listviews-with-a-viewholder/9
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder artistHolder;

        //Check if View has already been inflated and can be reused, otherwise inflate it.
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_artist, parent, false);

            artistHolder = new ViewHolder();
            artistHolder.artistName = (TextView) convertView.findViewById(R.id.artistTV); //Create textView for artist-name

            artistHolder.artistAlbum = (TextView) convertView.findViewById(R.id.albumTV);

            artistHolder.artistImage = (ImageView) convertView.findViewById(R.id.imageIV);
            //itemHolder.artistImage = (TextView) convertView.findViewById(R.id.albumIV);

            convertView.setTag(artistHolder); //When a tag has been set, the convertView can retrieve information about the views from the tag in the future
        } else {
            artistHolder = (ViewHolder) convertView.getTag(); //All information about a view can be retrieved from the View-tag.
        }
        //Needs to be casted, since private implementation makes ArrayList return an object without a special type of Album.

        Artist mArtist = (Artist)artistListMock.get(position);
        if(mArtist.name != null){
            artistHolder.artistName.setText(mArtist.name);
        } else {
            artistHolder.artistName.setText("Hej!");
        }
        return convertView;
    }

    static class ViewHolder {
        TextView artistName;
        TextView artistAlbum;
        ImageView artistImage; //Not yet implemented
    }



}
