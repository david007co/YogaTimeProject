package com.example.a300142288.yogaproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 */

public class SelectDataSource {
    SQLiteDatabase db;
    SQLiteOpenHelper dbhelper;
    public static final String LOGTAG = "YOGALOG";

    StringBuilder outputText = new StringBuilder();


    public SelectDataSource(Context context){
        dbhelper = new SelectDBOpenHelper(context);

    };//constructor

    private static final String[] allColumns={
            SelectDBOpenHelper.COLUMN_ID,
            SelectDBOpenHelper.COLUMN_TITLE,
            SelectDBOpenHelper.COLUMN_DESC,
            SelectDBOpenHelper.COLUMN_LEVEL,
            SelectDBOpenHelper.COLUMN_POSE
    };

    public void open(){
        db = dbhelper.getWritableDatabase();
        Log.i(LOGTAG, "database opened");
    }

    //drops table so emulator wil update, redundant w/ onUpdate
    public void dropIt()
    {
        open();
        Log.i(LOGTAG, "dropping table");
        Cursor query= db.rawQuery("IF EXISTS(SELECT * FROM " + SelectDBOpenHelper.TABLE_POSES +
                " DROP TABLE " + SelectDBOpenHelper.TABLE_POSES, null);
    }

    //closes db connection
    public void close(){
        dbhelper.close();
        Log.i(LOGTAG, "database closed");
    }
    //returns userID for user with name i, if no entry found returns -1
    public int sharedPref(String i){
        Cursor query= db.rawQuery("select * from users where name='"+i+"'", null);
        if(query !=null){
            query.moveToFirst();


            int j = query.getInt(query.getColumnIndex("userID"));
            Log.i(LOGTAG, "cacheID:"+ query.getInt(query.getColumnIndex("userID")));
            return j;
        }

        return -1;


    }//sharedpref

    //checks if the lessons table is populated, returns boolean
    public boolean isPopulated(){
        Log.i(LOGTAG, "Checking Ppopulation");
        open();
        Cursor query= db.rawQuery("select * from poses", null);
        if(query.moveToFirst()){
            return true;
        }
        return false;
    }
    //not currently used, but was going to check if lessons table was emptyy
    //android didnt like it when it was removed, so despite not being used it stays.
    public void genLessons(){
        open();
        Log.i(LOGTAG, "generating lessons");
        Cursor query= db.rawQuery("select * from poses", null);
    }

    //creates new lesson onject, inserts it to db and returns the lesson object
    public Lesson create(Lesson lesson){
        ContentValues values = new ContentValues();
        values.put(SelectDBOpenHelper.COLUMN_TITLE, lesson.getTitle());
        values.put(SelectDBOpenHelper.COLUMN_DESC, lesson.getDesc());
        values.put(SelectDBOpenHelper.COLUMN_LEVEL, lesson.getLevel());
        values.put(SelectDBOpenHelper.COLUMN_POSE, lesson.getPose());
        long insertID = db.insert(SelectDBOpenHelper.TABLE_POSES, null,values);
        lesson.setId(insertID);
        return lesson;
    }

    //returns lesson title + description in form of object
    //based off level,pose location in hashmap
    public Lesson findVideo(int level, int pose){
        Lesson lesson = new Lesson();
        Cursor query= db.rawQuery("select * from " + SelectDBOpenHelper.TABLE_POSES+
                " where " + SelectDBOpenHelper.COLUMN_LEVEL+" = "+level+ " and "+SelectDBOpenHelper.COLUMN_POSE+
                " = " +pose, null);
        if(query !=null){
            query.moveToFirst();

            lesson.setTitle(query.getString(query.getColumnIndex(SelectDBOpenHelper.COLUMN_TITLE)));
            lesson.setDesc(query.getString(query.getColumnIndex(SelectDBOpenHelper.COLUMN_DESC)));

        }
        return lesson;

    }







}//class
