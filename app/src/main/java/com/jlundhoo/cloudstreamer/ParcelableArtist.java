package com.jlundhoo.cloudstreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jlundhol on 2015-06-21.
 */
public class ParcelableArtist implements Parcelable {

    private String artistID;
    private String artistName;
    private String artistImageURL;

    public ParcelableArtist(){

    }

    public String getArtistID() {
        return artistID;
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistImageURL() {
        return artistImageURL;
    }

    public void setArtistImageURL(String artistImageURL) {
        this.artistImageURL = artistImageURL;
    }

    public ParcelableArtist(String artistID, String artistName, String artistImageURL){
        this.artistID = artistID;
        this.artistName = artistName;
        this.artistImageURL = artistImageURL;
    }

    public ParcelableArtist(Parcel in) {
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
    public static final Parcelable.Creator<ParcelableArtist> CREATOR = new Parcelable.Creator<ParcelableArtist>() {
        @Override
        public ParcelableArtist createFromParcel(Parcel in) {
            return new ParcelableArtist(in);
        }

        @Override
        public ParcelableArtist[] newArray(int size) {
            return new ParcelableArtist[size];
        }
    };
}
