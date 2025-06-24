package com.example.animeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ForumContentActivity extends AppCompatActivity {

    private TextView tvForumTitle;
    private RecyclerView rvComments;
    private EditText etComment;
    private Button btnPostComment;
    private CommentAdapter adapter;
    private List<Comment> commentList;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_content);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        tvForumTitle = findViewById(R.id.tv_forum_title);
        rvComments = findViewById(R.id.rv_comments);
        etComment = findViewById(R.id.et_comment);
        btnPostComment = findViewById(R.id.btn_post_comment);

        commentList = new ArrayList<>();
        adapter = new CommentAdapter(commentList);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(adapter);

        String forumId = getIntent().getStringExtra("forumId");
        if (forumId != null) {
            loadForumData(forumId);
        }

        btnPostComment.setOnClickListener(v -> {
            String commentText = etComment.getText().toString().trim();
            if (!commentText.isEmpty()) {
                postComment(forumId, commentText);
            } else {
                Toast.makeText(this, "Введите комментарий", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadForumData(String forumId) {
        databaseReference.child("forums").child(forumId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String title = snapshot.child("title").getValue(String.class);
                    tvForumTitle.setText(title != null ? title : "Без названия");

                    DataSnapshot commentsSnapshot = snapshot.child("comments");
                    commentList.clear();
                    for (DataSnapshot commentSnapshot : commentsSnapshot.getChildren()) {
                        String commentId = commentSnapshot.getKey();
                        String userId = commentSnapshot.child("userId").getValue(String.class);
                        String text = commentSnapshot.child("text").getValue(String.class);
                        commentList.add(new Comment(commentId, userId, text));
                    }
                    adapter.updateList(commentList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибки
            }
        });
    }

    private void postComment(String forumId, String commentText) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String commentId = databaseReference.child("forums").child(forumId).child("comments").push().getKey();
            Comment comment = new Comment(commentId, user.getUid(), commentText);
            databaseReference.child("forums").child(forumId).child("comments").child(commentId).setValue(comment)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            etComment.setText("");
                            Toast.makeText(this, "Комментарий добавлен", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Ошибка: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}