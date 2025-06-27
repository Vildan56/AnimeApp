package com.example.animeapp;

import android.os.Bundle;

public class ForumsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forums);
        setupToolbarAndNav();

        android.widget.Toolbar bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            android.widget.ImageButton btnHome = bottomNav.findViewById(R.id.home_button);
            android.widget.ImageButton btnFavorite = bottomNav.findViewById(R.id.favorite_button);
            android.widget.ImageButton btnForum = bottomNav.findViewById(R.id.forum_button_create);
            android.widget.ImageButton btnProfile = bottomNav.findViewById(R.id.profile_button);
            if (btnHome != null) btnHome.setOnClickListener(v -> startActivity(new android.content.Intent(this, MainActivity.class)));
            if (btnFavorite != null) btnFavorite.setOnClickListener(v -> startActivity(new android.content.Intent(this, FavoritesActivity.class)));
            if (btnForum != null) btnForum.setOnClickListener(v -> startActivity(new android.content.Intent(this, CreateForumActivity.class)));
            if (btnProfile != null) btnProfile.setOnClickListener(v -> {
                com.google.firebase.auth.FirebaseUser user = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    android.widget.Toast.makeText(this, "Вы должны быть авторизованы для просмотра профиля", android.widget.Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new android.content.Intent(this, ProfileActivity.class));
                }
            });
        }
    }
}