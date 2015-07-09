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

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by jlundhol on 2015-06-15.
 */
public class ArtistAdapter extends ArrayAdapter{

    private ArrayList<Artist> artistList = new ArrayList<Artist>();


    public ArtistAdapter(Context ctx, ArrayList<Artist> artistList){
        super(ctx, 0);
        this.artistList = artistList;
    }

    public void add(Artist mArtist){
        artistList.add(mArtist);
    }


    public void addArtists(List<Artist> artistList){
        for(int i = 0; i < artistList.size(); i++){
            this.artistList.add(artistList.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        super.clear();
        artistList.clear();
    }

    @Override
    public int getCount(){
        return artistList.size();
    }

    public Artist getItemById(int position){
        return (Artist)artistList.get(position);
    }

    /*
    //The adapter would inflate the layout for each row in its getView() method and assign the data to the individual views in the row.
    //View Holder pattern is to large part copied from https://www.binpress.com/tutorial/smooth-out-your-listviews-with-a-viewholder/9
    */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        //Check if View has already been inflated and can be reused, otherwise inflate it. Using View Holder pattern.
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_artist, parent, false);

            holder = new ViewHolder();
            holder.artistName = (TextView) convertView.findViewById(R.id.artistTV); //Create textView for artist-name
            holder.artistImage = (ImageView) convertView.findViewById(R.id.imageIV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Needs to be casted.  since private implementation makes ArrayList return an object without a special type of Artist.
        Artist mArtist = (Artist)artistList.get(position);

        //Add artist name + image thumbnail
        if(mArtist != null){
            holder.artistName.setText(mArtist.name);

            //Some images take a while to load, Picasso meanwhile uses a placeholder-image.
            if(mArtist.images.size() > 0){
                Picasso.with(getContext())
                        .load(mArtist.images.get(0).url)
                        .placeholder(R.drawable.artist_placeholderimg)
                        .resize(250, 250)
                        .into(holder.artistImage);
            }
        }


        return convertView;
    }

    static class ViewHolder {
        TextView artistName;
        ImageView artistImage;
    }



}
