package com.kersuzananthony.flickrbrowser2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {

    private static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> mPhotoList;
    private Context mContext;

    FlickrRecyclerViewAdapter(Context context, List<Photo> photoList) {
        this.mContext = context;
        this.mPhotoList = photoList;
    }

    @Override
    public int getItemCount() {
        int q = (this.mPhotoList == null || this.mPhotoList.size() == 0) ? 1 : this.mPhotoList.size();
        Log.d(TAG, "getItemCount: " + q);
        return q;
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        if (mPhotoList == null || mPhotoList.size() == 0) {
            holder.bindNoResult(mContext);
            return;
        }

        Photo currentPhoto = this.mPhotoList.get(position);
        holder.bind(this.mContext, currentPhoto);
    }

    public Photo getPhoto(int position) {
        return this.mPhotoList != null && this.mPhotoList.size() > position ? this.mPhotoList.get(position) : null;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.mPhotoList = photoList;
        notifyDataSetChanged();
    }

    static class FlickrImageViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "FlickrImageViewHolder";
        ImageView thumbnailImageView = null;
        TextView titleTextView = null;

        FlickrImageViewHolder(View itemView) {
            super(itemView);

            this.thumbnailImageView = (ImageView) itemView.findViewById(R.id.browser_thumbnail);
            this.titleTextView = (TextView) itemView.findViewById(R.id.browser_title);
        }

        void bind(Context context, Photo photo) {
            Picasso.with(context)
                    .load(photo.getImage())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(this.thumbnailImageView);

            this.titleTextView.setText(photo.getTitle());
        }

        void bindNoResult(Context context) {
            this.thumbnailImageView.setImageResource(R.drawable.placeholder);
            this.titleTextView.setText(context.getResources().getString(R.string.search_no_photo));
        }
    }
}
