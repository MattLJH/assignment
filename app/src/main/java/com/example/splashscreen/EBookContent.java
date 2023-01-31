package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
//-
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;
//--

public class EBookContent extends AppCompatActivity implements View.OnClickListener {
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook_content);
        AssetManager assetManager = getAssets();
        Intent intent = getIntent();
        SharedPreferences sp = getSharedPreferences("ebookchapters", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        id = intent.getStringExtra("id");
        Uri uri = Uri.parse(intent.getStringExtra("uri"));
        try {
            // find InputStream for book
//            InputStream epubInputStream = assetManager
//                    .open("books/crompton-just-william.epub");
            InputStream b = getContentResolver().openInputStream(uri);
            Book book = (new EpubReader()).readEpub(b);

            int chapter = sp.getInt(id, 1);

            if(chapter == 1) {
                editor.putInt(id, 1);
                editor.commit();
            }
            TextView t5 = findViewById(R.id.textView5);
            t5.setText(id+" "+chapter);


            /*
            // Log the book's authors
            Log.i("epublib", "author(s): " + book.getMetadata().getAuthors());

            // Log the book's title
            Log.i("epublib", "title: " + book.getTitle());

            // Log the book's coverimage property
            Bitmap coverImage = BitmapFactory.decodeStream(book.getCoverImage()
                    .getInputStream());
            Log.i("epublib", "Coverimage is " + coverImage.getWidth() + " by "
                    + coverImage.getHeight() + " pixels");

            // Log the tale of contents
            logTableOfContents(book.getTableOfContents().getTocReferences(), 0);

            */

            WebView wv = findViewById(R.id.ebookcontent);
            //String baseUrl = "content://com.android.providers.downloads.documents/document/msf%3A24";
            String data = new String(book.getContents().get(chapter).getData());
            wv.loadDataWithBaseURL("", data, "text/html", "UTF-8", null);
        } catch (IOException e) {
            Log.e("epublib", e.getMessage());
        }
    }

    private void logTableOfContents(List<TOCReference> tocReferences, int depth) {
        if (tocReferences == null) {
            return;
        }
        for (TOCReference tocReference : tocReferences) {
            StringBuilder tocString = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                tocString.append("\t");
            }
            tocString.append(tocReference.getTitle());
            Log.i("epublib", tocString.toString());

            logTableOfContents(tocReference.getChildren(), depth + 1);
        }
    }

    @Override
    public void onClick(View v) {
        int chapter;
        SharedPreferences sp = getSharedPreferences("ebookchapters", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        switch (v.getId()) {
            case R.id.back:
                chapter = sp.getInt(id, 1);
                if(chapter != 1) {
                    editor.putInt(id, chapter-1);
                    editor.commit();
                }

                finish();
                startActivity(getIntent());
                break;
            case R.id.forward:
                chapter = sp.getInt(id, 1);
                editor.putInt(id, chapter+1);
                editor.commit();

                finish();
                startActivity(getIntent());
                break;
        }
    }
}