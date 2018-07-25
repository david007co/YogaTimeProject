package com.example.a300142288.yogaproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 */

public class UsersDBOpenHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "YOGALOG";

    private static final String DATABASE_NAME = "yoga.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "userID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_POSE = "pose";
    public static final String COLUMN_HASWATCHED = "haswatched";




    private static final String TABLE_CREATE =
            "CREATE TABLE "+TABLE_USERS + " ("+
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT UNIQUE, "+
                    COLUMN_LEVEL + " INTEGER, "+
                    COLUMN_POSE + " INTEGER, "+
                    COLUMN_HASWATCHED + " INTEGER DEFAULT 0"+
                    ")";





    public UsersDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL("INSERT INTO " + TABLE_USERS+
                "(NAME) VALUES ('Jim')");

        Log.i(LOGTAG, "table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
