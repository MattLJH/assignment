package com.example.splashscreen;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EBook extends AppCompatActivity {
    DatabaseHandler db;
    private ArrayList<EBookInfo> ebooks;
    private RecyclerView recyclerView;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        db = new DatabaseHandler(this);
        userid = getIntent().getIntExtra("Userid",0);


        ebooks = db.getAllEBooks(userid);
        recyclerView = findViewById(R.id.ebookList);
        setAdapter();

//        //test print items in db
//        TextView tv5 = findViewById(R.id.textView5);
//
//        if(ebooks.isEmpty()){
//            tv5.setText("empty");
//        }else {
//            String a = "";
//            for(EBookInfo ebook : ebooks){
//                a+=ebook.getId()+"\n"+ebook.getTitle()+"\n"+ebook.getUri();
//            }
//            tv5.setText(a);
//        }
    }

    ActivityResultLauncher<Intent> sActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri uri = data.getData();


                        //EBookInfo ebook = new EBookInfo(uri.toString());
                        db.addEBook(uri, EBook.this, userid);
                        //refresh activity to show newly added books
                        finish();
                        startActivity(getIntent());
                    }
                }
            }
    );

    public void openFileDialog(View v) {
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.setType("application/epub+zip"); //only epub files can be added
        data = Intent.createChooser(data, "choose a file");
        sActivityResultLauncher.launch(data);
    }

    public void openEBookContent(View v) {
        Intent i = new Intent(this, EBookContent.class);
        String id = ((List<String>)v.getTag()).get(0);
        String uri = ((List<String>)v.getTag()).get(1);
        i.putExtra("id", id);
        i.putExtra("uri", uri);
        startActivity(i);
    }

    private void setAdapter() {
        EBookAdapter adapter = new EBookAdapter(ebooks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}