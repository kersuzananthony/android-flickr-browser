package com.kersuzananthony.flickrbrowser2;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickrJsonData extends AsyncTask<String, Void, List<Photo>> implements GetRawData.OnDownloadCompleted {

    interface OnDataAvailable {
        void onDataAvailable(List<Photo> photos, DownloadStatus status);
    }

    private static final String TAG = "GetFlickrJsonData";
    private static final String KEY_LANGUAGE = "lang";
    private static final String KEY_MATCH_ALL = "tagmode";
    private static final String KEY_SEARCH_CRITERIA = "tags";
    private static final String KEY_FORMAT = "format";
    private static final String FORMAT = "json";
    private static final String KEY_NOJSON_CALLBACK = "nojsoncallback";
    private static final String KEY_ITEMS = "items";

    private List<Photo> mPhotoList = null;
    private String mBaseUrl;
    private String mLanguage;
    private boolean mMatchAll;
    private DownloadStatus mDownloadStatus;
    private final OnDataAvailable mOnDataAvailable;

    GetFlickrJsonData(OnDataAvailable onDataAvailable, String baseUrl, String language, boolean matchAll) {
        mBaseUrl = baseUrl;
        mLanguage = language;
        mMatchAll = matchAll;
        mOnDataAvailable = onDataAvailable;
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        if (mOnDataAvailable != null) {
            mOnDataAvailable.onDataAvailable(mPhotoList, mDownloadStatus);
        }
    }

    @Override
    protected List<Photo> doInBackground(String... strings) {
        if (strings == null || strings.length < 1) return null;

        String destinationUri = createUri(strings[0]);

        GetRawData getRawData = new GetRawData(this);
        getRawData.runFromBackgroundThread(destinationUri);

        return mPhotoList;
    }

    private String createUri(String criteria) {
        if (criteria == null) return null;

        return Uri.parse(mBaseUrl)
                .buildUpon()
                .appendQueryParameter(KEY_LANGUAGE, mLanguage)
                .appendQueryParameter(KEY_MATCH_ALL, mMatchAll ? "all" : "any")
                .appendQueryParameter(KEY_SEARCH_CRITERIA, criteria)
                .appendQueryParameter(KEY_FORMAT, FORMAT)
                .appendQueryParameter(KEY_NOJSON_CALLBACK, "1")
                .build()
                .toString();
    }

    @Override
    public void onDownloadCompleted(String data, DownloadStatus downloadStatus) {
        mDownloadStatus = downloadStatus;

        if (downloadStatus == DownloadStatus.OK) {
            mPhotoList = new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray jsonPhotoArray = jsonData.getJSONArray(KEY_ITEMS);

                for (int i = 0; i < jsonPhotoArray.length(); i++) {
                    mPhotoList.add(Photo.buildPhotoFromJson(jsonPhotoArray.getJSONObject(i)));
                }
            } catch (JSONException e) {
                Log.e(TAG, "onDownloadCompleted: JSONException " + e.getMessage());
                mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
}
