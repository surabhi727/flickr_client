
package com.demo.flickrclient.flickr_api;

import com.demo.flickrclient.model.FlickrResponse;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.util.Key;

import java.io.IOException;

public class Flickr extends AbstractGoogleJsonClient {

    public static final String DEFAULT_ROOT_URL = "https://secure.flickr.com/";

    public static final String DEFAULT_SERVICE_PATH = "services/rest/";

    public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

    Flickr(Builder builder) {
        super(builder);
    }

    @Override
    protected void initialize(AbstractGoogleClientRequest<?> httpClientRequest) throws IOException {
        super.initialize(httpClientRequest);
    }

    public PostComment createPostCommentRequest() throws IOException {
        PostComment request = new PostComment();
        initialize(request);
        return request;
    }

    public class PostComment extends
            FlickrRequest<FlickrResponse> {

        private static final String METHOD_NAME = "flickr.photos.comments.addComment";

        @Key("photo_id")
        private String photoId;

        @Key("comment_text")
        private String commentText;

        public PostComment setPhoto_id(String photoId) {
            this.photoId = photoId;
            return this;
        }

        public PostComment setComment_text(String commentText) {
            this.commentText = commentText;
            return this;
        }

        protected PostComment() {
            super(Flickr.this,
                    HttpMethods.POST,
                    "",
                    null,
                    FlickrResponse.class);
            setMethod(METHOD_NAME);
        }

        @Override
        public PostComment set(String fieldName, Object value) {
            return (PostComment) super.set(fieldName, value);
        }

        @Override
        public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
        }

        @Override
        public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
        }

    }

    public static final class Builder extends
            AbstractGoogleJsonClient.Builder {

        public Builder(com.google.api.client.http.HttpTransport transport,
                com.google.api.client.json.JsonFactory jsonFactory,
                HttpRequestInitializer httpRequestInitializer) {
            super(transport,
                    jsonFactory,
                    DEFAULT_ROOT_URL,
                    DEFAULT_SERVICE_PATH,
                    httpRequestInitializer,
                    false);
        }

        @Override
        public Flickr build() {
            return new Flickr(this);
        }

        @Override
        public Builder setRootUrl(String rootUrl) {
            return (Builder) super.setRootUrl(rootUrl);
        }

        @Override
        public Builder setServicePath(String servicePath) {
            return (Builder) super.setServicePath(servicePath);
        }

        @Override
        public Builder setGoogleClientRequestInitializer(
                GoogleClientRequestInitializer googleClientRequestInitializer) {
            return (Builder) super
                    .setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }

        @Override
        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
        }

        @Override
        public Builder setApplicationName(String applicationName) {
            return (Builder) super.setApplicationName(applicationName);
        }

        @Override
        public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
            return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
        }

        @Override
        public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
            return (Builder) super
                    .setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
        }

        @Override
        public Builder setSuppressAllChecks(boolean suppressAllChecks) {
            return (Builder) super.setSuppressAllChecks(suppressAllChecks);
        }

        public Builder setFlickrRequestInitializer(
                FlickrRequestInitializer instagramRequestInitializer) {
            return (Builder) super.setGoogleClientRequestInitializer(instagramRequestInitializer);
        }

    }
}
