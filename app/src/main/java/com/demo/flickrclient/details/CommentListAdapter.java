package com.demo.flickrclient.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.flickrclient.R;
import com.demo.flickrclient.model.PhotoComment;
import com.demo.flickrclient.util.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {

    private List<PhotoComment> mComments;
    private Context mContext;
    private int mRowView;

    public CommentListAdapter(Context context, List<PhotoComment> comments, int rowView) {
        this.mComments = comments;
        this.mContext = context;
        this.mRowView = rowView;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(mRowView, null);
        return new CommentViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public void onBindViewHolder(CommentViewHolder customViewHolder, int position) {
        final PhotoComment item = mComments.get(position);
        customViewHolder.update(item);
    }

    @Override
    public int getItemCount() {
        return (null != mComments ? mComments.size() : 0);
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView commentText;

        public CommentViewHolder(View view) {
            super(view);

            this.imageView = view.findViewById(R.id.image);
            this.name = view.findViewById(R.id.name);
            commentText = view.findViewById(R.id.comment_text);
        }

        public void update(PhotoComment photo) {
            if (!TextUtils.isEmpty(photo.getBuddyImageUrl())) {
                Picasso.with(mContext).load(photo.getBuddyImageUrl())
                        .resize(AppConstants.IMAGE_SIZE, AppConstants.IMAGE_SIZE)
                        .centerInside().into(imageView);
            }
            name.setText(photo.getRealName());
            commentText.setText(Html.fromHtml(photo.getContent()));
        }
    }
}
