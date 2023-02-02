package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText LoginName, LoginPassword;
    private Button LoginButton;
    private TextView RegiButton;
    private UserInfos UserDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginName = (EditText) findViewById(R.id.LogName);
        LoginPassword = (EditText) findViewById(R.id.LogPass);
        LoginButton = (Button) findViewById(R.id.Logbutton);
        RegiButton = (TextView) findViewById(R.id.RegiClick);
        UserDb = new UserInfos(this);
        RegiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserName = LoginName.getText().toString();
                String UserPass = LoginPassword.getText().toString();
                if (UserDb.UserCheck(UserName, UserPass)) {
                    Log.d("UserID", String.valueOf(UserDb.getUser(UserName)));
                    Intent i;
                    i = new Intent(Login.this, HomeScreen.class);
                    i.putExtra("Userid", String.valueOf(UserDb.getUser(UserName)));
                    startActivity(i);
                } else {
                    Toast.makeText(Login.this, "Login fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
