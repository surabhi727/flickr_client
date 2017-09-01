package com.demo.flickrclient.model;

import com.google.gson.annotations.SerializedName;

public class PhotoDetailsResponse extends FlickrResponse {
    @SerializedName("photo")
    private Photo photoDetails;

    public Photo getPhotoDetails() {
        return photoDetails;
    }

    public void setPhotoDetails(Photo photoDetails) {
        this.photoDetails = photoDetails;
    }
}
