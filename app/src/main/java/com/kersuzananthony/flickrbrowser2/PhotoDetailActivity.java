package com.kersuzananthony.flickrbrowser2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {

    private static final String TAG = "PhotoDetailActivity";
    private static final String PHOTO_EXTRA = "PHOTO_EXTRA";
    private Photo mPhoto;

    public static Intent newIntent(Context context, Photo photo) {
        Intent intent = new Intent(context, PhotoDetailActivity.class);
        intent.putExtra(PHOTO_EXTRA, photo);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        activateToolbar(true);

        Intent intent = getIntent();
        if (intent.hasExtra(PHOTO_EXTRA)) {
            mPhoto = intent.getParcelableExtra(PHOTO_EXTRA);
            Log.d(TAG, "onCreate: Photo is " + mPhoto.toString());

            updateUI();
        }
    }

    private void updateUI() {
        if (mPhoto == null) return;
        TextView photoTitleTextView = (TextView) findViewById(R.id.photo_title);
        TextView photoAuthorTextView = (TextView) findViewById(R.id.photo_author);
        TextView photoTagsTextView = (TextView) findViewById(R.id.photo_tags);
        ImageView photoImageView = (ImageView) findViewById(R.id.photo_image);

        photoTitleTextView.setText(getResources().getString(R.string.photo_title_text, mPhoto.getTitle()));
        photoAuthorTextView.setText(getResources().getString(R.string.photo_author_text, mPhoto.getAuthor()));
        photoTagsTextView.setText(getResources().getString(R.string.photo_tags_text, mPhoto.getTags()));

        Picasso.with(this)
                .load(mPhoto.getImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(photoImageView);
    }
}
