package com.demo.flickrclient.util;

public class AppConstants {
    public static final String FLICKR_LOCAL_SEARCH_API = "https://api.flickr.com";
    public static final String FLICKR_CLIENT_API_KEY = "d5ce19d59065da4ba9c83de79c55e6c7";
    public static final String FLICKR_CLIENT_SECRET_KEY = "ad5632fd33e499bc";

    public static final String TEMPORARY_TOKEN_REQUEST_URL = "https://m.flickr.com/services/oauth/request_token";
    public static final String AUTHORIZATION_VERIFIER_SERVER_URL = "https://m.flickr.com/services/oauth/authorize";
    public static final String TOKEN_SERVER_URL = "https://m.flickr.com/services/oauth/access_token";
    public static final String REDIRECT_URL = "http://localhost/Callback";

    public static final String GET_PHOTO_DETAILS_METHOD = "flickr.photos.getInfo";
    public static final String GET_PHOTO_COMMENTS_METHOD = "flickr.photos.comments.getList";
    public static final String SEARCH_PHOTO_METHOD = "flickr.photos.search";
    public static final String IMAGE_URL = "https://farm%s.staticflickr.com/%s/%s_%s.jpg";
    public static final String USER_IMAGE_URL = "http://farm%s.staticflickr.com/%s/buddyicons/%s.jpg";
    public static final String DEFAULT_USER_IMAGE_URL = "https://www.flickr.com/images/buddyicon.gif";

    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 1024;
    public static final int IMAGE_SIZE = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
}
