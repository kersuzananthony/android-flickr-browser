package com.kersuzananthony.flickrbrowser2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements GetFlickrJsonData.OnDataAvailable,
        RecyclerItemClickListener.OnRecyclerClickListener {

    private static final String TAG = MainActivity.class.getName();
    private static final String API_BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateToolbar(false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);
        this.mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(this, new ArrayList<Photo>());

        recyclerView.setAdapter(this.mFlickrRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String queryResult = sharedPreferences.getString(FLICKR_QUERY, "");
        if (queryResult.length() > 0) {
            GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData(this, API_BASE_URL, "en-us", true);
            getFlickrJsonData.execute(queryResult);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                startActivity(SearchActivity.newIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDataAvailable(List<Photo> photos, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: photos length " + photos.size());

        if (status == DownloadStatus.OK) {
            this.mFlickrRecyclerViewAdapter.setPhotoList(photos);
        } else {
            this.mFlickrRecyclerViewAdapter.setPhotoList(new ArrayList<Photo>());
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: Item clicked at position " + position);
        startActivity(PhotoDetailActivity.newIntent(this, mFlickrRecyclerViewAdapter.getPhoto(position)));
    }

    @Override
    public void onItemLongClick(View view, int position) {
    }
}
