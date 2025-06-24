package com.example.animeapp;

import android.os.Bundle;
import android.widget.TextView;

public class FavoritesActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        setupToolbarAndNav();
    }
} 