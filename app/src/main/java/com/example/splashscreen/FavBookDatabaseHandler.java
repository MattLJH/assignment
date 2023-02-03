package com.example.splashscreen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class FavBookDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BookmarkedBooks";
    private static final String TABLE_BOOKMARKED = "Bookmark";
    private static final String KEY_ID = "bookmarkId";
    private static final String KEY_USERID = "userId";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PUBLISHER = "publisher";
    private static final String KEY_DATE = "date";
    private static final String KEY_IMAGE = "thumbnail";
    private static final String KEY_DESCRIPTION = "description";

    public FavBookDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKMARK_TABLE = "CREATE TABLE " + TABLE_BOOKMARKED + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERID + " TEXT," + KEY_TITLE + " TEXT,"  + KEY_PUBLISHER + " TEXT,"  + KEY_DATE + " TEXT,"  + KEY_IMAGE + " TEXT," + KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_BOOKMARK_TABLE);
    }

    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARKED);

        // Create tables again
        onCreate(db);
    }

    boolean isBookBookmarkedByUser(int userId, String bookId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "Bookmark",
                new String[] {"bookmarkId"},
                "userId = ? AND book = ?", new String[] {String.valueOf(userId), bookId},
                null, null, null, null);

        return cursor.moveToFirst();
    }

    public ArrayList<BookInfo> getAllBookmarks(int userid) {
        ArrayList<BookInfo> bookList = new ArrayList<BookInfo>();
        String selectQuery = "SELECT * FROM " + TABLE_BOOKMARKED + " WHERE userid = " + userid;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                BookInfo bookinfo = new BookInfo();
                bookinfo.setId(Integer.parseInt(cursor.getString(1)));
                bookinfo.setTitle(cursor.getString(2));
                bookinfo.setPublisher(cursor.getString(3));
                bookinfo.setPublishedDate(cursor.getString(4));
                bookinfo.setThumbnail(cursor.getString(5));
                bookinfo.setDescription(cursor.getString(6));
                bookList.add(bookinfo);
            } while (cursor.moveToNext());
        }

        return bookList;
    }

    boolean addBookmark(int userid, String title, String publisher, String date, String thumbnail, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Userid", userid);
        values.put("title", title);
        values.put("publisher", publisher);
        values.put("date", date);
        values.put("thumbnail", thumbnail);
        values.put("description", description);

        long result = db.insert("Bookmark", null, values);

        if (result == -1) return false;
        else return true;
    }

    boolean deleteBookmark(int userid, String title, String publisher, String date, String thumbnail, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("Bookmark", "Userid = ? AND title = ? AND publisher = ? AND date = ? AND thumbnail = ? AND description = ?", new String[] { String.valueOf(userid), title, publisher, date, thumbnail, description});

        if (result == -1) return false;
        else return true;
    }

}
