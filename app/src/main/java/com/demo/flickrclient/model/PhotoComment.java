package com.demo.flickrclient.model;

import com.demo.flickrclient.util.AppConstants;
import com.google.gson.annotations.SerializedName;

public class PhotoComment {
    private String id;
    private String author;
    private String datecreate;
    private String permalink;
    @SerializedName("_content")
    private String content;
    @SerializedName("iconserver")
    private String iconServer;
    @SerializedName("realname")
    private String realName;
    private String iconfarm;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getContent()
    {
        return content;
    }

    public String getRealName ()
    {
        return realName;
    }

    public String getBuddyImageUrl() {
        if(Integer.valueOf(iconServer) > 0) {
            return String.format(AppConstants.USER_IMAGE_URL, iconfarm, iconServer, author);
        } else {
            return AppConstants.DEFAULT_USER_IMAGE_URL;
        }

    }
}
