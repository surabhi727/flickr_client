package com.demo.flickrclient.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.flickrclient.FlickrClientApplication;
import com.demo.flickrclient.flickr_api.FlickrClientRestAPI;
import com.demo.flickrclient.details.PhotoDetailsActivity;
import com.demo.flickrclient.R;
import com.demo.flickrclient.model.PhotoListItem;
import com.demo.flickrclient.model.SearchPhotosResponse;
import com.demo.flickrclient.util.AppConstants;
import com.demo.flickrclient.util.AppUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoListActivity extends AppCompatActivity implements PhotoListAdapter.OnPhotoClickListener {

    private RecyclerView mPhotoListView;
    private PhotoListAdapter mAdapter;
    private List<PhotoListItem> mPhotosList = new ArrayList<>();
    private EditText mSearchView;
    private static final String DEFAULT_SEARCH = "Northern Lights";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
        mPhotoListView = (RecyclerView) findViewById(R.id.recycler_view);
        mPhotoListView.setLayoutManager(new LinearLayoutManager(this));
        mSearchView = (EditText) findViewById(R.id.search_field);
        mSearchView.setText(DEFAULT_SEARCH);
        mAdapter = new PhotoListAdapter(PhotoListActivity.this, mPhotosList, R.layout.photo_list_row, this);
        mPhotoListView.setAdapter(mAdapter);
        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchPhotosByTags(textView.getText().toString());
                }
                InputMethodManager imm = (InputMethodManager) PhotoListActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                return true;
            }
        });

        searchPhotosByTags(DEFAULT_SEARCH);
    }

    private void searchPhotosByTags(String searchTag) {
        if (AppUtils.isNetworkAvailable(getApplicationContext())) {
            FlickrClientRestAPI restAPI =
                    ((FlickrClientApplication) getApplicationContext()).getFlickrClientRestAPI();
            Call<SearchPhotosResponse> call =
                    restAPI.searchPhotos(AppConstants.SEARCH_PHOTO_METHOD,
                            AppConstants.FLICKR_CLIENT_API_KEY, searchTag);
            call.enqueue(new Callback<SearchPhotosResponse>() {
                @Override
                public void onResponse(Call<SearchPhotosResponse> call, Response<SearchPhotosResponse> response) {
                    if (response.isSuccessful()) {
                        mPhotosList.clear();
                        mPhotosList.addAll(Arrays.asList(response.body().getPhotos().getPhoto()));
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(PhotoListActivity.this, R.string.no_internet_error, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SearchPhotosResponse> call, Throwable t) {
                    Toast.makeText(PhotoListActivity.this, R.string.no_internet_error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(PhotoListActivity.this, R.string.no_internet_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(PhotoListItem photoListItem) {
        Intent intent = new Intent(this, PhotoDetailsActivity.class);
        intent.putExtra(PhotoDetailsActivity.EXTRA_PHOTO_ID, photoListItem.getId());
        startActivity(intent);
    }
}
