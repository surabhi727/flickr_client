package com.demo.flickrclient.flickr_api;

import com.demo.flickrclient.model.PhotoCommentsResponse;
import com.demo.flickrclient.model.PhotoDetailsResponse;
import com.demo.flickrclient.model.SearchPhotosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrClientRestAPI {

    @GET("/services/rest/?format=json&nojsoncallback=1")
    Call<SearchPhotosResponse> searchPhotos(@Query("method") String method,
                                            @Query("api_key") String api_key,
                                            @Query("tags") String tags);

    @GET("/services/rest/?format=json&nojsoncallback=1")
    Call<PhotoDetailsResponse> getPhotoDetails(@Query("method") String method,
                                               @Query("api_key") String api_key,
                                               @Query("photo_id") String photoId);

    @GET("/services/rest/?format=json&nojsoncallback=1")
    Call<PhotoCommentsResponse> getPhotoComments(@Query("method") String method,
                                                 @Query("api_key") String api_key,
                                                 @Query("photo_id") String photoId);
}

