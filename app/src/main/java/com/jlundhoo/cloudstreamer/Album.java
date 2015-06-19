package com.jlundhoo.cloudstreamer;


import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Copyright;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.TrackSimple;

/**
 * Created by jlundhol on 2015-06-15.
 */
public class Album extends kaaes.spotify.webapi.android.models.Album {
    private List<ArtistSimple> artists;
    private List <Copyright> copyrights;
    private Map<String,String> external_ids;
    private List<String> genres;
    private Integer popularity;
    private String release_date;
    private String release_date_precision;
    private Pager<TrackSimple> tracks;

}
