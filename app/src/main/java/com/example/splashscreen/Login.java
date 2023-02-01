package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText LoginName, LoginPassword;
    private Button LoginButton;
    private UserInfo UserDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginName = (EditText) findViewById(R.id.LogName);
        LoginPassword = (EditText) findViewById(R.id.LogPass);
        LoginButton = (Button) findViewById(R.id.Logbutton);
        UserDb = new UserInfo(this);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLogin();
            }
        });
    }
private void validateLogin(){

        String UserName = LoginName.getText().toString();
        String UserPass = LoginPassword.getText().toString();
    SQLiteDatabase db = UserDb.getReadableDatabase();
    Cursor cursor = db.rawQuery("select name, pass from Users Where name = ? And Password = ?", new String[]{UserName,UserPass});
    if (cursor.moveToFirst()){
        Toast.makeText(Login.this, "Login Successful",Toast.LENGTH_SHORT).show();
       startActivity(new Intent(Login.this, MainActivity.class));
    }
    else
        Toast.makeText(Login.this, "Login Fail",Toast.LENGTH_SHORT).show();
    cursor.close();
    db.close();
}

}