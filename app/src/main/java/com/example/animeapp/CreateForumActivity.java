package com.example.animeapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateForumActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_forum);

        EditText etForumTitle = findViewById(R.id.et_forum_title);
        Button btnCreateForum = findViewById(R.id.btn_create_forum);
        Button btnCancel = findViewById(R.id.btn_cancel);

        btnCreateForum.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) {
                Toast.makeText(this, "Вы должны быть авторизованы для создания форума", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = etForumTitle.getText().toString().trim();
            if (title.isEmpty()) {
                Toast.makeText(this, "Введите название форума", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: добавить сохранение форума в базу данных
                Toast.makeText(this, "Форум '" + title + "' создан!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }
} 