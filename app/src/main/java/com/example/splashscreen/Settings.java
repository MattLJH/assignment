package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {
    Button Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Profile = (Button) findViewById(R.id.profilePage);


        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                String user;
                user = getIntent().getStringExtra("Userid");
                i = new Intent(Settings.this,ProfilePage.class);
                i.putExtra("Userid",user);
                startActivity(i);

            }
        });
    }
}