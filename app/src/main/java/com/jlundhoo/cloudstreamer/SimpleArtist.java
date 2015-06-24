package com.jlundhoo.cloudstreamer;

import android.os.Parcel;
import android.os.Parcelable;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by jlundhol on 2015-06-24.
 */
public class SimpleArtist extends Artist implements Parcelable {

    private String artistID;
    private String artistName;
    private String artistImageURL;


    public SimpleArtist(){

    }

    public SimpleArtist(String artistID, String artistName, String artistImageURL) {
        this.artistID = artistID;
        this.artistName = artistName;
        this.artistImageURL = artistImageURL;
    }

    protected SimpleArtist(Parcel in) {
        artistID = in.readString();
        artistName = in.readString();
        artistImageURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artistID);
        dest.writeString(artistName);
        dest.writeString(artistImageURL);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SimpleArtist> CREATOR = new Parcelable.Creator<SimpleArtist>() {
        @Override
        public SimpleArtist createFromParcel(Parcel in) {
            return new SimpleArtist(in);
        }

        @Override
        public SimpleArtist[] newArray(int size) {
            return new SimpleArtist[size];
        }
    };
}
