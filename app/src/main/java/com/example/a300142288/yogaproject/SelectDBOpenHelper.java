package com.example.a300142288.yogaproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 */

public class SelectDBOpenHelper extends SQLiteOpenHelper {
    private static final String LOGTAG = "YOGALOG";

    private static final String DATABASE_NAME = "poses.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_POSES = "poses";
    public static final String COLUMN_ID = "poseID";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_POSE = "pose";


    private static final String TABLE_CREATE =
            "CREATE TABLE "+TABLE_POSES + " ("+
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT UNIQUE, "+
                    COLUMN_DESC + " TEXT, "+
                    COLUMN_LEVEL + " INTEGER, "+
                    COLUMN_POSE + " INTEGER"+
                    ")";





    public SelectDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        Log.i(LOGTAG, "tableposescreated");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSES);
        onCreate(db);
    }




}
