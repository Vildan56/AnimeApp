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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TitleActivity extends BaseActivity {

    private ImageView ivPoster;
    private TextView tvTitle, tvDescription, tvType, tvEpisodes, tvGenres, tvRating, tvStatus;
    private DatabaseReference databaseReference;
    private boolean isFavorite = false;

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
        tvGenres = findViewById(R.id.tv_genres);
        tvRating = findViewById(R.id.tv_rating);
        tvStatus = findViewById(R.id.tv_status);

        ImageButton btnAddForum = findViewById(R.id.btn_add_forum);
        ImageButton btnAddFavorite = findViewById(R.id.btn_add_favorite);

        btnAddForum.setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, CreateForumActivity.class));
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String animeId = getIntent().getStringExtra("animeId");

        if (user != null && animeId != null) {
            DatabaseReference favRef = FirebaseDatabase.getInstance().getReference()
                .child("users").child(user.getUid()).child("favorites").child(animeId);

            // Проверяем, есть ли в избранном
            favRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    isFavorite = snapshot.exists();
                    btnAddFavorite.setImageResource(isFavorite ? R.drawable.favorite_filled : R.drawable.favorite);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });

            btnAddFavorite.setOnClickListener(v -> {
                if (isFavorite) {
                    favRef.removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            isFavorite = false;
                            btnAddFavorite.setImageResource(R.drawable.favorite);
                            Toast.makeText(this, "Удалено из избранного", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    favRef.setValue(true).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            isFavorite = true;
                            btnAddFavorite.setImageResource(R.drawable.favorite_filled);
                            Toast.makeText(this, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            btnAddFavorite.setOnClickListener(v ->
                Toast.makeText(this, "Войдите в аккаунт, чтобы добавить в избранное", Toast.LENGTH_SHORT).show()
            );
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("anime");

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
                        tvType.setText("Тип: " + (anime.getTitle_eng() != null ? anime.getTitle_eng() : "-"));
                        tvEpisodes.setText("Эпизоды: " + anime.getEpisodes());
                        tvGenres.setText("Жанры: " + (anime.getGenres() != null ? android.text.TextUtils.join(", ", anime.getGenres()) : "-"));
                        tvRating.setText("Рейтинг: " + anime.getRating());
                        tvStatus.setText("Статус: " + (anime.getStatus() != null ? anime.getStatus() : "-"));
                        if (anime.getPoster_url() != null && !anime.getPoster_url().isEmpty()) {
                            Picasso.get().load(anime.getPoster_url()).into(ivPoster);
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