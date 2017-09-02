package com.kersuzananthony.flickrbrowser2;

class Photo {

    private String mTitle;
    private String mAuthorId;
    private String mAuthor;
    private String mLink;
    private String mTags;
    private String mImage;

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
