package com.demo.flickrclient;

import android.app.Application;

import com.demo.flickrclient.flickr_api.FlickrClientRestAPI;
import com.demo.flickrclient.util.AppConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlickrClientApplication extends Application {

    private FlickrClientRestAPI flickrClientRestAPI;

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.FLICKR_LOCAL_SEARCH_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        flickrClientRestAPI = retrofit.create(FlickrClientRestAPI.class);
    }

    public FlickrClientRestAPI getFlickrClientRestAPI() {
        return flickrClientRestAPI;
    }
}
