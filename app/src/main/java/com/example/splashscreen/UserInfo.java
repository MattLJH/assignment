package com.example.splashscreen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tutlane on 06-01-2018.
 */

public class UserInfo<User> extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager";
    private static final String TABLE_Users = "Users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "pass";
    private static final String KEY_Email = "Email";

    public UserInfo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"+KEY_PASS+ "TEXT"
                + KEY_Email + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact

    void AddUser(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, users.GetName());
        values.put(KEY_PASS, users.GetPass());
        values.put(KEY_Email, users.GetEmail());

        // Inserting Row
        db.insert(TABLE_Users, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    Users getuser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_Users, new String[] { KEY_ID,
                        KEY_NAME,KEY_PASS, KEY_Email }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Users user = new Users(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return user;
    }

    // code to get all contacts in a list view
    public ArrayList<Users> collectUser() {
        ArrayList<Users> UserInfo = new ArrayList<Users>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Users;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Users users = new Users();
                users.SetID(Integer.parseInt(cursor.getString(0)));
                users.SetName(cursor.getString(1));
                users.SetPass(cursor.getString(2));
                users.SetEmail(cursor.getString(3));
                // Adding contact to list
                collectUser().add(users);
            } while (cursor.moveToNext());
        }

        // return contact list
        return collectUser();
    }

    // code to update the single contact
    public int updateUser(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, users.GetName());
        values.put(KEY_PASS, users.GetPass());
        values.put(KEY_Email, users.GetEmail());

        // updating row
        return db.update(TABLE_Users, values, KEY_ID + " = ?",
                new String[] { String.valueOf(users.GetID()) });
    }

    // Deleting single contact
    public void deleteContact(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_ID + " = ?",
                new String[] { String.valueOf(users.GetID()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_Users;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}