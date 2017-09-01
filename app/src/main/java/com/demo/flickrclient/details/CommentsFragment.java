package com.demo.flickrclient.details;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.flickrclient.FlickrClientApplication;
import com.demo.flickrclient.R;
import com.demo.flickrclient.flickr_api.Flickr;
import com.demo.flickrclient.flickr_api.FlickrClientRestAPI;
import com.demo.flickrclient.flickr_api.FlickrRequestInitializer;
import com.demo.flickrclient.flickr_api.OAuth;
import com.demo.flickrclient.model.FlickrResponse;
import com.demo.flickrclient.model.PhotoComment;
import com.demo.flickrclient.model.PhotoCommentsResponse;
import com.demo.flickrclient.util.AppConstants;
import com.demo.flickrclient.util.AppUtils;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.Lists;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsFragment extends BottomSheetDialogFragment implements LoaderManager.LoaderCallbacks<FlickrResponse> {

    public static final int POST_COMMENT_LOADER = 1;
    private RecyclerView mCommentsView;
    private CommentListAdapter mAdapter;
    private ArrayList<PhotoComment> mComments = new ArrayList<>();
    private EditText mAddCommentView;
    private ProgressBar mProgressBar;
    private String mPhotoId;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback =
            new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.fragment_comments, null);
        dialog.setContentView(contentView);
        mCommentsView = (RecyclerView) contentView.findViewById(R.id.recycler_view);
        mAddCommentView = (EditText) contentView.findViewById(R.id.add_comment);
        mProgressBar = (ProgressBar) contentView.findViewById(R.id.progress_bar);

        mAddCommentView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(mAddCommentView.getText().toString())) {
                    getActivity().getSupportLoaderManager().initLoader(
                            POST_COMMENT_LOADER, null, CommentsFragment.this).forceLoad();
                }
                return true;
            }
        });
        mCommentsView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CommentListAdapter(getContext(), mComments, R.layout.comment_row);
        mCommentsView.setAdapter(mAdapter);

        mPhotoId = getArguments().getString(PhotoDetailsActivity.EXTRA_PHOTO_ID);

        fetchPhotoDetails(mPhotoId);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    private void fetchPhotoDetails(String photoId) {
        if (AppUtils.isNetworkAvailable(getContext().getApplicationContext())) {
            FlickrClientRestAPI restAPI =
                    ((FlickrClientApplication) getContext().getApplicationContext()).getFlickrClientRestAPI();
            Call<PhotoCommentsResponse> call =
                    restAPI.getPhotoComments(AppConstants.GET_PHOTO_COMMENTS_METHOD,
                            AppConstants.FLICKR_CLIENT_API_KEY, photoId);
            call.enqueue(new Callback<PhotoCommentsResponse>() {
                @Override
                public void onResponse(Call<PhotoCommentsResponse> call, Response<PhotoCommentsResponse> response) {
                    mProgressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        updateComments(response.body());
                    } else {
                        Toast.makeText(getContext(), R.string.flickr_api_error, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PhotoCommentsResponse> call, Throwable throwable) {
                    Toast.makeText(getContext(), R.string.flickr_api_error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), R.string.no_internet_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateComments(PhotoCommentsResponse response) {
        if (hasComments(response)) {
            mComments.clear();
            mComments.addAll(Arrays.asList(response.getComments().getComment()));
            mAdapter.notifyDataSetChanged();
        }
    }

    private boolean hasComments(PhotoCommentsResponse response) {
        return response.getComments() != null && response.getComments().getComment() != null;
    }

    @Override
    public Loader<FlickrResponse> onCreateLoader(int id, Bundle args) {
        return new AddCommentTask(
                getContext(),
                getActivity().getSupportFragmentManager(),
                mPhotoId,
                mAddCommentView.getText().toString());
    }

    @Override
    public void onLoadFinished(Loader<FlickrResponse> loader, FlickrResponse data) {
        if(data != null && data.getStat().equals("ok")) {
            fetchPhotoDetails(mPhotoId);
            mAddCommentView.setText("");
        }
    }

    @Override
    public void onLoaderReset(Loader<FlickrResponse> loader) {

    }

    static class AddCommentTask extends AsyncTaskLoader<FlickrResponse> {

        private final OAuth mOauth;
        private final String mPhotoId;
        private final String mComment;

        AddCommentTask(Context context, FragmentManager manager, String photoId, String comment) {
            super(context);
            mPhotoId = photoId;
            mComment = comment;
            mOauth = OAuth.newInstance(getContext().getApplicationContext(),
                    manager,
                    new ClientParametersAuthentication(AppConstants.FLICKR_CLIENT_API_KEY,
                            AppConstants.FLICKR_CLIENT_SECRET_KEY),
                    AppConstants.AUTHORIZATION_VERIFIER_SERVER_URL,
                    AppConstants.TOKEN_SERVER_URL,
                    AppConstants.REDIRECT_URL,
                    Lists.<String>newArrayList(),
                    AppConstants.TEMPORARY_TOKEN_REQUEST_URL);
        }

        @Override
        public FlickrResponse loadInBackground() {
            try {
                Credential credential = mOauth.authorize10a(
                        getContext().getResources().getString(R.string.token_flickr)).getResult();

                Flickr flickr =
                        new Flickr.Builder(OAuth.HTTP_TRANSPORT, OAuth.JSON_FACTORY, credential)
                                .setApplicationName(getContext().getResources().getString(R.string.app_name))
                                .setFlickrRequestInitializer(new FlickrRequestInitializer())
                                .build();
                FlickrResponse response = flickr
                        .createPostCommentRequest()
                        .setPhoto_id(mPhotoId)
                        .setComment_text(mComment)
                        .execute();
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
