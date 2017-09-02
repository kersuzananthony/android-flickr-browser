package com.kersuzananthony.flickrbrowser2;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus { IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK }

class GetRawData extends AsyncTask<String, Void, String> {

    interface OnDownloadCompleted {
        void onDownloadCompleted(String data, DownloadStatus downloadStatus);
    }

    private static final String TAG = "GetRawData";
    private DownloadStatus mDownloadStatus;
    private OnDownloadCompleted mListener;

    GetRawData(OnDownloadCompleted listener) {
        this.mListener = listener;
        this.mDownloadStatus = DownloadStatus.IDLE;
    }

    @Override
    protected void onPostExecute(String s) {
        if (mListener != null) mListener.onDownloadCompleted(s, mDownloadStatus);
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if (strings == null) {
            this.mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }

        try {
            this.mDownloadStatus = DownloadStatus.PROCESSING;

            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int response = connection.getResponseCode();

            Log.d(TAG, "doInBackground: response code was: " + response);

            StringBuilder result = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while (null != (line = reader.readLine())) {
                result.append(line).append("\n");
            }

            this.mDownloadStatus = DownloadStatus.OK;
            return result.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: MalformedURLException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IOException: " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground: SecurityException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: Exception: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error while closing reader " + e.getMessage());
                }
            }
        }

        this.mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;

        return null;
    }
}
