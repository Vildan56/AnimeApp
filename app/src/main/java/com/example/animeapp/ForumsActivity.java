package com.example.animeapp;

import android.os.Bundle;

public class ForumsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forums);
        setupToolbarAndNav();
    }
}