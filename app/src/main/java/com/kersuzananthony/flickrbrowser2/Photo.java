package com.kersuzananthony.flickrbrowser2;

import org.json.JSONException;
import org.json.JSONObject;

class Photo {

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
}
