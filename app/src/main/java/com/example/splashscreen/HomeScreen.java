package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        String User;
        User = getIntent().getStringExtra("Userid");
        Log.d("userid", User);
        switch (v.getId()) {
            case R.id.ebookList:
                i = new Intent(this, EBook.class);
                i.putExtra("Userid", User);
                startActivity(i);
                break;
            case R.id.onlinebks:
                i = new Intent(this, SearchBooks.class);
                i.putExtra("Userid", User);
                startActivity(i);
                break;
            case R.id.settings:
                i = new Intent(this, Settings.class);
                i.putExtra("Userid", User);
                startActivity(i);
                break;
        }
    }
}