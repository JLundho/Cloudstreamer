package com.jlundhoo.cloudstreamer;

import android.os.Parcel;
import android.os.Parcelable;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by jlundhol on 2015-06-24.
 */

public class SimpleTrack extends Track implements Parcelable {
    private String trackName;
    private String trackAlbum;

    public SimpleTrack(){

    }

    protected SimpleTrack(Parcel in) {
        trackName = in.readString();
        trackAlbum = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trackName);
        dest.writeString(trackAlbum);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SimpleTrack> CREATOR = new Parcelable.Creator<SimpleTrack>() {
        @Override
        public SimpleTrack createFromParcel(Parcel in) {
            return new SimpleTrack(in);
        }

        @Override
        public SimpleTrack[] newArray(int size) {
            return new SimpleTrack[size];
        }
    };
}
