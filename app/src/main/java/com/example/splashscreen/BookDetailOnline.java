package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class BookDetailOnline extends AppCompatActivity implements View.OnClickListener {

    // creating variables for strings,text view, image views and button.
    String title, description, thumbnail;

    TextView titleRV, descRV;
    private ImageView bookRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_online);

        // initializing our views..
        titleRV = findViewById(R.id.idRVBookTitle);
        descRV = findViewById(R.id.idRVDescription);
        bookRV = findViewById(R.id.idRVbook);

        // getting the data which we have passed from our adapter class.
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        thumbnail = getIntent().getStringExtra("thumbnail");

        // after getting the data we are setting
        // that data to our text views and image view.
        titleRV.setText(title);
        descRV.setText(description);
        Picasso.get().load(thumbnail.replace("http:", "https:")).into(bookRV);
    }

    @Override
    public void onClick(View v) {
        Intent r;
        switch(v.getId()) {
            case R.id.btnBkContent:
                r = new Intent(this, BookContent.class);
                r.putExtra("title",  title);
                startActivity(r);
                break;
        }
    }
}
