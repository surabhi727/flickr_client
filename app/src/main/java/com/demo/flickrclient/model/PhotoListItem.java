package com.demo.flickrclient.model;

import com.demo.flickrclient.util.AppConstants;

public class PhotoListItem {
    private String id;
    private String secret;
    private String server;
    private String farm;
    private String title;

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return String.format(AppConstants.IMAGE_URL, farm, server, id, secret);
    }

}
