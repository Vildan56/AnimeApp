package com.example.animeapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView ivProfilePicture;
    private TextView tvProfileName;
    private RecyclerView rvCreatedForums;
    private ForumAdapter adapter;
    private List<Forum> forumList;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        ivProfilePicture = findViewById(R.id.iv_profile_picture);
        tvProfileName = findViewById(R.id.tv_profile_name);
        rvCreatedForums = findViewById(R.id.rv_created_forums);

        forumList = new ArrayList<>();
        adapter = new ForumAdapter(forumList);
        rvCreatedForums.setLayoutManager(new LinearLayoutManager(this));
        rvCreatedForums.setAdapter(adapter);

        loadProfileData();
    }

    private void loadProfileData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            databaseReference.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("name").getValue(String.class);
                        String profilePicture = snapshot.child("profilePicture").getValue(String.class);

                        tvProfileName.setText(name != null ? name : "Без имени");
                        if (profilePicture != null && !profilePicture.isEmpty()) {
                            Picasso.get().load(profilePicture).into(ivProfilePicture);
                        }

                        DataSnapshot forumsSnapshot = snapshot.child("createdForums");
                        forumList.clear();
                        for (DataSnapshot forumSnapshot : forumsSnapshot.getChildren()) {
                            String forumId = forumSnapshot.getKey();
                            String title = forumSnapshot.getValue(String.class);
                            forumList.add(new Forum(forumId, title));
                        }
                        adapter.updateList(forumList);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Обработка ошибки
                }
            });
        }
    }
}