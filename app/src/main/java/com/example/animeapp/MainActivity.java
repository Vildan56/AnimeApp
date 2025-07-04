package com.example.animeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimeAdapter adapter;
    private List<Anime> allAnimeList;
    private List<Anime> displayedAnimeList;
    private SearchView searchView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        searchView = findViewById(R.id.search_view);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 столбца

        allAnimeList = new ArrayList<>();
        displayedAnimeList = new ArrayList<>();
        adapter = new AnimeAdapter(displayedAnimeList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("anime");

        loadAnimeData();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        // Навигация по нижней панели
        android.widget.Toolbar bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            android.widget.ImageButton btnHome = bottomNav.findViewById(R.id.home_button);
            android.widget.ImageButton btnFavorite = bottomNav.findViewById(R.id.favorite_button);
            android.widget.ImageButton btnForum = bottomNav.findViewById(R.id.forum_button);
            android.widget.ImageButton btnProfile = bottomNav.findViewById(R.id.profile_button);
            if (btnHome != null) btnHome.setOnClickListener(v -> startActivity(new android.content.Intent(this, MainActivity.class)));
            if (btnFavorite != null) btnFavorite.setOnClickListener(v -> startActivity(new android.content.Intent(this, FavoritesActivity.class)));
            if (btnForum != null) btnForum.setOnClickListener(v -> startActivity(new android.content.Intent(this, ForumsActivity.class)));
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

    private void loadAnimeData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allAnimeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Anime anime = snapshot.getValue(Anime.class);
                    if (anime != null && anime.getTitle() != null && anime.getPoster_url() != null && !anime.getTitle().isEmpty() && !anime.getPoster_url().isEmpty()) {
                        allAnimeList.add(anime);
                        Log.d("AnimeAdapter", "title: " + anime.getTitle() + ", poster: " + anime.getPoster_url());
                    } else {
                        Log.w("AnimeAdapter", "Пропущен элемент: " + snapshot.getKey() + ", title: " + (anime != null ? anime.getTitle() : "null") + ", poster: " + (anime != null ? anime.getPoster_url() : "null"));
                    }
                }
                displayedAnimeList.clear();
                displayedAnimeList.addAll(allAnimeList);
                adapter.updateList(displayedAnimeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обработка ошибки
            }
        });
    }

    private void filterList(String text) {
        displayedAnimeList.clear();
        if (text.isEmpty()) {
            displayedAnimeList.addAll(allAnimeList);
        } else {
            for (Anime anime : allAnimeList) {
                if (anime.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    displayedAnimeList.add(anime);
                }
            }
        }
        adapter.updateList(displayedAnimeList);
    }
}