package com.example.localsqlite;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "student";

    public static final class StudentColumns implements BaseColumns {
        public static String NAME = "name";
        public static String NIM = "nim";
        public static String TIMESTAMP = "timestamp";
    }
}