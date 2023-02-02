package com.example.splashscreen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavBookDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BookmarkedBooks";
    private static final String TABLE_BOOKMARKED = "Bookmark";
    private static final String KEY_ID = "bookmarkId";
    private static final String KEY_USERID = "userId";
    private static final String KEY_BOOK = "book";

    public FavBookDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKMARK_TABLE = "CREATE TABLE " + TABLE_BOOKMARKED + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERID + " TEXT," + KEY_BOOK + " TEXT" + ")";
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

    /**
     * Adds a bookmarked book into the database.
     * @param userId - User ID of the user adding the recipe to favourites
     * @param book - Recipe ID of the favourited recipe
     * @return a boolean value
     */
    boolean addBookmark(int userId, String book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("bookId", book);
        long result = db.insert("Bookmark", null, values);

        if (result == -1) return false;
        else return true;
    }

    /**
     * Deletes a bookmarked book from the database.
     * @param userId - User ID of the user removing the recipe from favourites
     * @param book - Recipe ID of the favourited recipe
     * @return a boolean value
     */
    boolean deleteBookmark(int userId, String book) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("Bookmark", "userId=? AND recipeId = ?", new String[] { String.valueOf(userId), book});

        if (result == -1) return false;
        else return true;
    }

}
