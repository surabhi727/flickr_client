package com.demo.flickrclient.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.flickrclient.R;
import com.demo.flickrclient.model.PhotoListItem;
import com.demo.flickrclient.util.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.CustomViewHolder> {

    private List<PhotoListItem> mPhotoItemList;
    private Context mContext;
    private int mRowView;
    private OnPhotoClickListener mOnPhotoClickListener;

    public PhotoListAdapter(Context context, List<PhotoListItem> photoItemList, int rowView, OnPhotoClickListener listener) {
        this.mPhotoItemList = photoItemList;
        this.mContext = context;
        this.mRowView = rowView;
        this.mOnPhotoClickListener = listener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(mRowView, null);
        return new CustomViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        final PhotoListItem item = mPhotoItemList.get(position);
        customViewHolder.update(item);
    }

    @Override
    public int getItemCount() {
        return (null != mPhotoItemList ? mPhotoItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView textView;
        private PhotoListItem photoListItem;

        CustomViewHolder(View view) {
            super(view);

            this.imageView = view.findViewById(R.id.image);
            this.textView = view.findViewById(R.id.title);
            view.setOnClickListener(this);
        }

        void update(PhotoListItem photo) {
            photoListItem = photo;
            if (!TextUtils.isEmpty(photo.getPhotoUrl())) {
                Picasso.with(mContext).load(photo.getPhotoUrl())
                        .resize(AppConstants.IMAGE_SIZE, AppConstants.IMAGE_SIZE)
                        .centerInside().into(imageView);
            }
            textView.setText(photo.getTitle());
        }

        @Override
        public void onClick(View view) {
            if (null != mOnPhotoClickListener) {
                mOnPhotoClickListener.onItemClick(photoListItem);
            }
        }
    }

    public interface OnPhotoClickListener {
        void onItemClick(PhotoListItem p);
    }

}
