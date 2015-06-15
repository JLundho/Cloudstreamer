package com.jlundhoo.cloudstreamer;

/**
 * Created by jlundhol on 2015-06-15.
 */
public class Album {
    private String albumName;
    private String artistName;

    public Album(){

    }

    public Album(String albumName, String artistName){
        this.albumName = albumName;
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}
