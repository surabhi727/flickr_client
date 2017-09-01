package com.demo.flickrclient.model;

import com.google.gson.annotations.SerializedName;

public class PhotoComments {

    @SerializedName("comment")
    private PhotoComment[] photoComment;

    @SerializedName("photo_id")
    private String photoId;

    public PhotoComment[] getComment ()
    {
        return photoComment;
    }

}
