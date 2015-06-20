package com.jlundhoo.cloudstreamer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlundhoo.cloudstreamer.R;
import com.squareup.picasso.Picasso;

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

            artistHolder.artistImage = (ImageView) convertView.findViewById(R.id.imageIV);


            convertView.setTag(artistHolder); //When a tag has been set, the convertView can retrieve information about the views from the tag in the future
        } else {
            artistHolder = (ViewHolder) convertView.getTag(); //All information about a view can be retrieved from the View-tag.
        }

        //Needs to be casted, since private implementation makes ArrayList return an object without a special type of Artist.
        Artist mArtist = (Artist)artistListMock.get(position);

        //Add artist name + image thumbnail
        if(mArtist != null){
            artistHolder.artistName.setText(mArtist.name);

            //Some images take a while to load, Picasso meanwhile uses a placeholder-image.
            if(mArtist.images.size() > 0){
                Picasso.with(getContext())
                        .load(mArtist.images.get(0).url)
                        .placeholder(R.mipmap.artist_placeholderimg)
                        .resize(250, 250)
                        .into(artistHolder.artistImage);
            }
        }


        return convertView;
    }



    static class ViewHolder {
        TextView artistName;
        ImageView artistImage;
    }



}
