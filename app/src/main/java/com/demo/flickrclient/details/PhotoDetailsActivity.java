package com.demo.flickrclient.details;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.flickrclient.FlickrClientApplication;
import com.demo.flickrclient.flickr_api.FlickrClientRestAPI;
import com.demo.flickrclient.R;
import com.demo.flickrclient.model.Photo;
import com.demo.flickrclient.model.PhotoDetailsResponse;
import com.demo.flickrclient.util.AppConstants;
import com.demo.flickrclient.util.AppUtils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_PHOTO_ID = "photo_id";
    private String mPhotoId;
    private TextView mTakenDateView;
    private TextView mCommentCountView;
    private TextView mViewCountView;
    private TextView mOwnerNameView;
    private TextView mDescriptionView;
    private ImageView mPhotoView;
    private ImageView mOwnerPhotoView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);
        mTakenDateView = (TextView) findViewById(R.id.taken_date);
        mCommentCountView = (TextView) findViewById(R.id.comments);
        mViewCountView = (TextView) findViewById(R.id.views);
        mOwnerNameView = (TextView) findViewById(R.id.owner_name);
        mDescriptionView = (TextView) findViewById(R.id.description);
        mPhotoView = (ImageView) findViewById(R.id.photo_image);
        mOwnerPhotoView = (ImageView) findViewById(R.id.owner_image);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        mPhotoId =  getIntent().getStringExtra(EXTRA_PHOTO_ID);

        mCommentCountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment bottomSheetDialogFragment = new CommentsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_PHOTO_ID, mPhotoId);
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(),
                        bottomSheetDialogFragment.getTag());
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(!TextUtils.isEmpty(mPhotoId)) {
            fetchPhotoDetails(mPhotoId);
        } else {
            finish();
        }
    }

    private void fetchPhotoDetails(String photoId) {
        if (AppUtils.isNetworkAvailable(getApplicationContext())) {

            FlickrClientRestAPI restAPI =
                    ((FlickrClientApplication)getApplicationContext()).getFlickrClientRestAPI();
            Call<PhotoDetailsResponse> call =
                    restAPI.getPhotoDetails(AppConstants.GET_PHOTO_DETAILS_METHOD,
                            AppConstants.FLICKR_CLIENT_API_KEY, photoId);
            call.enqueue(new Callback<PhotoDetailsResponse>() {
                @Override
                public void onResponse(Call<PhotoDetailsResponse> call, Response<PhotoDetailsResponse> response) {
                    if (response.isSuccessful()) {
                        updatePhotoDetails(response.body().getPhotoDetails());
                    } else {
                        Toast.makeText(PhotoDetailsActivity.this, R.string.no_internet_error, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PhotoDetailsResponse> call, Throwable t) {
                    Toast.makeText(PhotoDetailsActivity.this, R.string.no_internet_error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(PhotoDetailsActivity.this, R.string.no_internet_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePhotoDetails(Photo photoDetails) {
        if(null != photoDetails) {
            mCollapsingToolbarLayout.setTitle(photoDetails.getTitle().getContent());
            mDescriptionView.setText(Html.fromHtml(photoDetails.getDescription().getContent()));
            mCommentCountView.setText(getString(R.string.comments_text,photoDetails.getComments().getContent()));
            mViewCountView.setText(getString(R.string.views_text, photoDetails.getViews()));
            mOwnerNameView.setText(photoDetails.getPhotoOwner().getRealName());
            mTakenDateView.setText(photoDetails.getDates().getTaken());
            if (!TextUtils.isEmpty(photoDetails.getPhotoUrl())) {
                Picasso.with(this).load(photoDetails.getPhotoUrl())
                        .resize(AppConstants.IMAGE_SIZE, AppConstants.IMAGE_SIZE)
                        .centerInside().into(mPhotoView);
            }
            if (!TextUtils.isEmpty(photoDetails.getPhotoOwner().getBuddyImageUrl())) {
                Picasso.with(this).load(photoDetails.getPhotoOwner().getBuddyImageUrl())
                        .resize(AppConstants.IMAGE_SIZE, AppConstants.IMAGE_SIZE)
                        .centerInside().into(mOwnerPhotoView);
            }
        }
    }
}
