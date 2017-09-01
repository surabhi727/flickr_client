package com.demo.flickrclient.model;

import com.demo.flickrclient.util.AppConstants;
import com.google.gson.annotations.SerializedName;

public class PhotoOwner {

    private String username;
    private String location;
    @SerializedName("iconserver")
    private String iconServer;
    @SerializedName("realname")
    private String realName;
    private String nsid;
    private String iconfarm;

    public String getRealName()
    {
        return realName;
    }

    public String getBuddyImageUrl() {
        if(Integer.valueOf(iconServer) > 0) {
            return String.format(AppConstants.USER_IMAGE_URL, iconfarm, iconServer, nsid);
        } else {
            return AppConstants.DEFAULT_USER_IMAGE_URL;
        }
    }
}
