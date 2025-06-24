package com.example.animeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
    }

    private void loadAnimeData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allAnimeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Anime anime = snapshot.getValue(Anime.class);
                    allAnimeList.add(anime);
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