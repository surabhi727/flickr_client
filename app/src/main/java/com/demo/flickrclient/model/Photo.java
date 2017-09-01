package com.demo.flickrclient.model;

import com.demo.flickrclient.util.AppConstants;
import com.google.gson.annotations.SerializedName;

public class Photo {

    private String id;
    private String secret;
    private String server;
    private String farm;
    private Details title;
    private Details description;
    private String views;
    private PhotoDates dates;
    @SerializedName("owner")
    private PhotoOwner photoOwner;
    private Details comments;

    public Details getComments() {
        return comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Details getTitle() {
        return title;
    }

    public Details getDescription() {
        return description;
    }

    public String getViews() {
        return views;
    }

    public PhotoDates getDates() {
        return dates;
    }

    public PhotoOwner getPhotoOwner() {
        return photoOwner;
    }

    public String getPhotoUrl() {
        return String.format(AppConstants.IMAGE_URL, farm, server, id, secret);
    }
}

