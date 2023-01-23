package com.example.splashscreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookDetailOnline extends AppCompatActivity {

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
        Picasso.get().load(thumbnail).into(bookRV);

//        // adding on click listener for our preview button.
//        previewBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (previewLink.isEmpty()) {
//                    // below toast message is displayed when preview link is not present.
//                    Toast.makeText(BookDetails.this, "No preview Link present", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                // if the link is present we are opening
//                // that link via an intent.
//                Uri uri = Uri.parse(previewLink);
//                Intent i = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(i);
//            }
//        });
//
//        // initializing on click listener for buy button.
//        buyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (buyLink.isEmpty()) {
//                    // below toast message is displaying when buy link is empty.
//                    Toast.makeText(BookDetails.this, "No buy page present for this book", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                // if the link is present we are opening
//                // the link via an intent.
//                Uri uri = Uri.parse(buyLink);
//                Intent i = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(i);
//            }
//        });
    }
}
