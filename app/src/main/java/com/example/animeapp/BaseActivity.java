package com.example.animeapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageButton;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupToolbarAndNav() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.widget.Toolbar bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            int childCount = bottomNav.getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View child = bottomNav.getChildAt(i);
                if (child instanceof ImageButton) {
                    int index = i;
                    child.setOnClickListener(v -> {
                        switch (index) {
                            case 0: // home
                                startActivity(new Intent(this, MainActivity.class));
                                break;
                            case 1: // favorite
                                startActivity(new Intent(this, FavoritesActivity.class));
                                break;
                            case 2: // forum
                                startActivity(new Intent(this, ForumsActivity.class));
                                break;
                            case 3: // profile
                                startActivity(new Intent(this, ProfileActivity.class));
                                break;
                        }
                    });
                }
            }
        }
    }
} 