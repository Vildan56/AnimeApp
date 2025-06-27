package com.example.animeapp;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TitleActivity extends BaseActivity {

    private ImageView ivPoster;
    private TextView tvTitle, tvDescription, tvType, tvEpisodes;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        setupToolbarAndNav();

        ivPoster = findViewById(R.id.iv_poster);
        tvTitle = findViewById(R.id.tv_title);
        tvDescription = findViewById(R.id.tv_description);
        tvType = findViewById(R.id.tv_type);
        tvEpisodes = findViewById(R.id.tv_episodes);

        ImageButton btnAddForum = findViewById(R.id.btn_add_forum);
        ImageButton btnAddFavorite = findViewById(R.id.btn_add_favorite);

        btnAddForum.setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, CreateForumActivity.class));
        });

        btnAddFavorite.setOnClickListener(v -> {
            // TODO: добавить логику добавления в избранное
            Toast.makeText(this, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("anime");

        String animeId = getIntent().getStringExtra("animeId");
        if (animeId != null) {
            loadAnimeData(animeId);
        }
    }

    private void loadAnimeData(String animeId) {
        databaseReference.child(animeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Anime anime = snapshot.getValue(Anime.class);
                    if (anime != null) {
                        tvTitle.setText(anime.getTitle());
                        tvDescription.setText(anime.getDescription());
                        tvType.setText("Тип: " + anime.getType());
                        tvEpisodes.setText("Эпизоды: " + anime.getEpisodes());
                        if (anime.getPosterUrl() != null && !anime.getPosterUrl().isEmpty()) {
                            Picasso.get().load(anime.getPosterUrl()).into(ivPoster);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибки
            }
        });
    }
}