package com.example.webviewandviewpager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class databaseScript extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyAppDatabase.db";
    private static final int DATABASE_VERSION = 25;

    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE IF NOT EXISTS Users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL," +
                    "password TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "alamat TEXT NOT NULL," +
                    "nomor TEXT NOT NULL);";

    private static final String CREATE_PESANAN_TABLE =
            "CREATE TABLE IF NOT EXISTS Pesanan (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "alamat TEXT NOT NULL," +
                    "nomor TEXT NOT NULL," +
                    "peminjaman TEXT NOT NULL);";

    public databaseScript(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_PESANAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS Pesanan");
            db.execSQL(CREATE_PESANAN_TABLE);
        }
    }

    // Register a new user
    public long registerUser(String username, String password, String email, String alamat, String nomor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("email", email);
        values.put("alamat", alamat);
        values.put("nomor", nomor);

        long result = db.insert("Users", null, values);
        db.close();
        return result;
    }

    // Add a new order
    public long orderUser(String username, String email, String alamat, String nomor, String peminjaman) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("alamat", alamat);
        values.put("nomor", nomor);
        values.put("peminjaman", peminjaman);

        long result = -1;
        try {
            result = db.insertOrThrow("Pesanan", null, values);
        } catch (SQLiteConstraintException e) {
            Log.e("DatabaseScript", "Error inserting data: " + e.getMessage(), e);
        } finally {
            db.close();
        }
        return result;
    }

    // Retrieve user data
    public Cursor dataUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] columns = {"id", "username", "email", "alamat", "nomor"};
            cursor = db.query("Users", columns, null, null, null, null, null);
        } catch (SQLiteException e) {
            Log.e("DatabaseScript", "Error querying Users: " + e.getMessage(), e);
        }
        return cursor;
    }

    // Retrieve order data
    public Cursor dataPesananAmbil() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query("Pesanan", null, null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        } catch (SQLiteException e) {
            Log.e("DatabaseScript", "Error querying Pesanan: " + e.getMessage(), e);
        }
        return cursor;
    }

    // Verify user login
    public boolean login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean success = cursor != null && cursor.moveToFirst();
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return success;
    }
}
