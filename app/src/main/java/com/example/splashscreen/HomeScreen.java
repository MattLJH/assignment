package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.ebookList:
                i = new Intent(this, EBook.class);
                startActivity(i);
                break;
            case R.id.onlinebks:
                i = new Intent(this, SearchBooks.class);
                startActivity(i);
                break;
            case R.id.settings:
        }
    }
}