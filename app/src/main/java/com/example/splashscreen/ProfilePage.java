package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfilePage extends AppCompatActivity {
    EditText NameUpdate, PassUpdate,EmailUpdate;
    Button UpdateButton;
    int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        UserInfos db = new UserInfos(this);
        NameUpdate = (EditText) findViewById(R.id.UpdateName);
        PassUpdate = (EditText) findViewById(R.id.updatePass);
        EmailUpdate = (EditText) findViewById(R.id.UpdateEmail);
        UpdateButton = (Button)  findViewById(R.id.UpdateBut);
        userID = Integer.parseInt(getIntent().getStringExtra("Userid"));

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View view) {
                String name = (NameUpdate.getText().toString());
                String Pass = (PassUpdate.getText().toString());
                String Email = (EmailUpdate.getText().toString());
                Users update = new Users (name, Pass, Email);
                db.updateUser(update);
                Toast.makeText(ProfilePage.this,"User Updated",Toast.LENGTH_SHORT).show();


            }
        });

    }
}