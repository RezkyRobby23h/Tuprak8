package com.example.localsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "Student.db";
    private static final int DATABASE_VERSION = 2; // Increment version to trigger update
    private static final String SQL_CREATE_TABLE_STUDENT = String.format("CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NAME,
            DatabaseContract.StudentColumns._ID,
            DatabaseContract.StudentColumns.NAME,
            DatabaseContract.StudentColumns.NIM,
            DatabaseContract.StudentColumns.TIMESTAMP
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Add timestamp column to existing table if upgrading from version 1
            sqLiteDatabase.execSQL("ALTER TABLE " + DatabaseContract.TABLE_NAME +
                    " ADD COLUMN " + DatabaseContract.StudentColumns.TIMESTAMP + " TEXT DEFAULT ''");
        } else {
            // For other version upgrades, drop and recreate
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}