package com.example.localsqlite;

import android.database.Cursor;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Student> mapCursorToArrayList(Cursor cursor) {
        ArrayList<Student> students = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.StudentColumns._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StudentColumns.NAME));
            String nim = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StudentColumns.NIM));
            students.add(new Student(id, name, nim));
        }
        return students;
    }
}
