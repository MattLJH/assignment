package com.example.splashscreen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import com.google.gson.Gson;
//



public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NovelReader";
    private static final String TABLE_EBOOKS = "ebooks";
    private static final String KEY_ID = "id";
    private static final String KEY_EBOOKURI = "uri";
    private static final String KEY_EBOOKTITLE = "title";
    private static final String KEY_EBOOKAUTHORS = "authors";
    private static final String KEY_EBOOKTHUMBNAIL = "thumbnail";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
///*
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        //ebooks table
        String CREATE_EBOOKS_TABLE = "CREATE TABLE " + TABLE_EBOOKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_EBOOKURI + " TEXT,"
                + KEY_EBOOKTITLE + " TEXT,"
                + KEY_EBOOKAUTHORS + " TEXT,"
                + KEY_EBOOKTHUMBNAIL + " BLOB"
                + ")";
        db.execSQL(CREATE_EBOOKS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EBOOKS);

        // Create tables again
        onCreate(db);
    }

    //add ebook
    void addEBook(Uri uri, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            //
            InputStream is = context.getContentResolver().openInputStream(uri);
            Book book = (new EpubReader()).readEpub(is);

            String title = book.getTitle();

            List authors = book.getMetadata().getAuthors();
            //String authorsJson = new Gson().toJson(authors);
            String authorsStr = "";
            for(Object author : authors){
                authorsStr += author.toString() + "\n";
            }

            Bitmap thumbnail = BitmapFactory.decodeStream(book.getCoverImage().getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] thumbnailBArray = bos.toByteArray();
            thumbnail.recycle();
            //

            ContentValues values = new ContentValues();
            values.put(KEY_EBOOKURI, uri.toString());
            values.put(KEY_EBOOKTITLE, title);
            values.put(KEY_EBOOKAUTHORS, authorsStr);
            values.put(KEY_EBOOKTHUMBNAIL, thumbnailBArray);

            // Inserting Row
            db.insert(TABLE_EBOOKS, null, values);
            //2nd argument is String containing nullColumnHack
            db.close(); // Closing database connection
        } catch (IOException e) {
            Log.e("epublib", e.getMessage());
        }
    }

    //get all ebooks
    public ArrayList<EBookInfo> getAllEBooks() {
        ArrayList<EBookInfo> EBookList = new ArrayList<EBookInfo>();

        String selectQuery = "SELECT  * FROM " + TABLE_EBOOKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                EBookInfo ebook = new EBookInfo();
                ebook.setId(Integer.parseInt(cursor.getString(0)));
                ebook.setUri(cursor.getString(1));
                ebook.setTitle(cursor.getString(2));
                ebook.setAuthors(cursor.getString(3));

                byte[] byteArray = cursor.getBlob(4);
                Bitmap thumbnail = BitmapFactory.decodeByteArray(cursor.getBlob(4), 0 ,cursor.getBlob(4).length);
                ebook.setThumbnail(thumbnail);

                EBookList.add(ebook);
            } while (cursor.moveToNext());
        }
        return EBookList;
    }
//*/
    private InputStream getEpubInputStream(String uri) {
        try {
            InputStream epubInputStream = new FileInputStream(uri);
            epubInputStream.close();
            return epubInputStream;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

//    //start
//    ///*
//    // Creating Tables
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        //ebooks table
//        String CREATE_EBOOKS_TABLE = "CREATE TABLE " + TABLE_EBOOKS + "("
//                + KEY_ID + " INTEGER PRIMARY KEY,"
//                + KEY_EBOOKURI + " TEXT,"
//                + KEY_EBOOKTITLE + " TEXT"
//                + ")";
//        db.execSQL(CREATE_EBOOKS_TABLE);
//    }
//
//    // Upgrading database
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EBOOKS);
//
//        // Create tables again
//        onCreate(db);
//    }
//
//    //add ebook
//    void addEBook(Uri uri, Context context) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        try{
//            InputStream is = context.getContentResolver().openInputStream(uri);
//            Book book = (new EpubReader()).readEpub(is);
//
//            String title = book.getTitle();
//
//            ContentValues values = new ContentValues();
//            values.put(KEY_EBOOKURI, uri.toString());
//            values.put(KEY_EBOOKTITLE, title);
//
//            // Inserting Row
//            db.insert(TABLE_EBOOKS, null, values);
//            //2nd argument is String containing nullColumnHack
//            db.close(); // Closing database connection
//        } catch (IOException e) {
//            Log.e("epublib", e.getMessage());
//        }
//    }
//
//    //get all ebooks
//    public ArrayList<EBookInfo> getAllEBooks() {
//        ArrayList<EBookInfo> EBookList = new ArrayList<EBookInfo>();
//
//        String selectQuery = "SELECT  * FROM " + TABLE_EBOOKS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        if (cursor.moveToFirst()) {
//            do {
//                EBookInfo ebook = new EBookInfo();
//                ebook.setId(Integer.parseInt(cursor.getString(0)));
//                ebook.setUri(cursor.getString(1));
//                ebook.setTitle(cursor.getString(2));
//
//                EBookList.add(ebook);
//            } while (cursor.moveToNext());
//        }
//        return EBookList;
//    }
//    //*/
//    //end
}
