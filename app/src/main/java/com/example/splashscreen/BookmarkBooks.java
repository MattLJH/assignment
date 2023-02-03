package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BookmarkBooks extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ArrayList<BookInfo> bookInfoArrayList;
    int User;
    private BottomNavigationView btmMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_books);
        FavBookDatabaseHandler db = new FavBookDatabaseHandler(BookmarkBooks.this);
        btmMenu = findViewById(R.id.btmMenu);
        User = getIntent().getIntExtra("Userid", 0);
        Log.d("Bookmark userid", String.valueOf(User));
        bookInfoArrayList = db.getAllBookmarks(User);
        Log.d("Items", String.valueOf(bookInfoArrayList));
        setUIRef();

        btmMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()) {
                    case R.id.ebk:
                        i = new Intent(BookmarkBooks.this, EBook.class);
                        startActivity(i);
                        break;
                    case R.id.home:
                        i = new Intent(BookmarkBooks.this, HomeScreen.class);
                        startActivity(i);
                        break;
                }

                return true;
            }
        });
    }

    private void setUIRef() {
        recyclerView = findViewById(R.id.bkmarkList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        FavBookAdapter favBookAdapter = new FavBookAdapter(bookInfoArrayList, BookmarkBooks.this, User);
        recyclerView.setAdapter(favBookAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.imgSearch3:
                i = new Intent (this, SearchBooks.class);
                Log.d("Bookmark 2 userid", String.valueOf(User));
                i.putExtra("Userid", User);
                startActivity(i);
                break;
        }
    }
}