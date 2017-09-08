package com.kersuzananthony.flickrbrowser2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SearchActivity extends BaseActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        activateToolbar(true);
    }

}
