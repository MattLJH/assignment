package com.example.splashscreen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class UserInfos extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UsersManager";
    private static final String TABLE_User = "UserTable";
    private static final String KEY_ID = "Id";
    private static final String KEY_NAME = "Name";
    private static final String KEY_PASS = "Password";
    private static final String KEY_Email = "Emails";

    public UserInfos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_User_Table = "CREATE TABLE " + TABLE_User + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT,"+
                KEY_PASS + " TEXT," +
                KEY_Email + " TEXT" + ")";
        db.execSQL(CREATE_User_Table);
        Log.d(
                KEY_NAME +" " + KEY_PASS + " "+ KEY_Email,"is created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_User);

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
        Log.d("The values are", values.toString());
        Log.d("Table is ", TABLE_User);

        // Inserting Row
        db.insert(TABLE_User , null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    int getUser(String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id FROM UserTable WHERE Name=?", new String[]{username});
        int id = -1;
        if (cursor.moveToFirst()) id = cursor.getInt(0);
        cursor.close();
        sqLiteDatabase.close();
        return id;
    }


    // code to get all contacts in a list view
    public ArrayList<Users> collectUser() {
        ArrayList<Users> UserInfo = new ArrayList<Users>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_User;

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
        return db.update(TABLE_User, values, KEY_ID + " = ?",
                new String[] { String.valueOf(users.GetID()) });
    }

    // Deleting single contact
    public void deleteContact(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_User, KEY_ID + " = ?",
                new String[] { String.valueOf(users.GetID()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_User;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    public boolean UserCheck(String UserName, String Password){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] Columns = {this.KEY_ID};
        String Selection = this.KEY_NAME + "= ? AND " + this.KEY_PASS + "= ?";
        String[] SelectionArgs = {UserName, Password};

        Cursor cursor = db.query(TABLE_User, Columns,Selection,SelectionArgs,null,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        return count >0;
    }
}
