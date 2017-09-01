package com.demo.flickrclient.model;

import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("_content")
    private String content;

    public String getContent() {
        return content;
    }

}
