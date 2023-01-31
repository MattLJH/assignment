package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class BookContent extends AppCompatActivity {

    String title;

    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_content);

        txtTitle = findViewById(R.id.idTxtTitle);

        title = getIntent().getStringExtra("title");

        txtTitle.setText(title);
    }
}