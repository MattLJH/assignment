package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText editName, editPass,editEmail;
    Button SubmitBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UserInfos db = new UserInfos(this);
        editName = (EditText) findViewById(R.id.NameRegi);
        editPass = (EditText) findViewById(R.id.PassRegi);
        editEmail = (EditText) findViewById(R.id.EmailsRegi);
        SubmitBut = (Button) findViewById(R.id.RegisterBut);

        SubmitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =(editName.getText().toString());
                String pass = (editPass.getText().toString());
                String email = (editEmail.getText().toString());
Users add = new Users(name, pass, email);
db.AddUser(add);
                Toast.makeText(Register.this,"Successfully register", Toast.LENGTH_SHORT).show();
startActivity(new Intent(Register.this,Login.class));

            }
        });
    }
}