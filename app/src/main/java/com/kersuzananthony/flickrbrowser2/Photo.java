package com.kersuzananthony.flickrbrowser2;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

class Photo implements Parcelable {

    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_AUTHOR_ID = "author_id";
    private static final String KEY_TAGS = "tags";
    private static final String KEY_LINK = "link";
    private static final String KEY_MEDIA = "media";
    private static final String KEY_IMAGE = "m";

    private String mTitle;
    private String mAuthorId;
    private String mAuthor;
    private String mLink;
    private String mTags;
    private String mImage;

    static Photo buildPhotoFromJson(JSONObject jsonObject) throws JSONException {
        String title = jsonObject.getString(KEY_TITLE);
        String authorId = jsonObject.getString(KEY_AUTHOR_ID);
        String author = jsonObject.getString(KEY_AUTHOR);
        String link = jsonObject.getString(KEY_LINK);
        String tags = jsonObject.getString(KEY_TAGS);

        JSONObject mediaObject = jsonObject.getJSONObject(KEY_MEDIA);
        String image = mediaObject.getString(KEY_IMAGE).replaceFirst("_m.", "_b.");

        return new Photo(title, authorId, author, link, tags, image);
    }

    private Photo(Parcel in) {
        mTitle = in.readString();
        mAuthorId = in.readString();
        mAuthor = in.readString();
        mTags = in.readString();
        mLink = in.readString();
        mImage = in.readString();
    }

    Photo(String title, String authorId, String author, String link, String tags, String image) {
        mTitle = title;
        mAuthorId = authorId;
        mAuthor = author;
        mLink = link;
        mTags = tags;
        mImage = image;
    }

    String getTitle() {
        return mTitle;
    }

    String getAuthorId() {
        return mAuthorId;
    }

    String getAuthor() {
        return mAuthor;
    }

    String getLink() {
        return mLink;
    }

    String getTags() {
        return mTags;
    }

    String getImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mAuthorId);
        dest.writeString(mAuthor);
        dest.writeString(mLink);
        dest.writeString(mTags);
        dest.writeString(mImage);
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
