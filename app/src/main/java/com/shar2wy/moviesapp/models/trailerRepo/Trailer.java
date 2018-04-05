package com.shar2wy.moviesapp.models.trailerRepo;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by shar2wy on 4/8/17.
 */

public class Trailer extends RealmObject{
    @SerializedName("id")
    private String id;

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    @SerializedName("site")
    private String site;

    @SerializedName("type")
    private String type;

    private int mMovieId;

    public Trailer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    public int getMovieId() {
        return mMovieId;
    }
}
