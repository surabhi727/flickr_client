package com.demo.flickrclient.model;

public class SearchPhotosResponse extends FlickrResponse{

    private Photos photos;

    public Photos getPhotos ()
    {
        return photos;
    }

    public void setPhotos (Photos photos) {
        this.photos = photos;
    }
}
