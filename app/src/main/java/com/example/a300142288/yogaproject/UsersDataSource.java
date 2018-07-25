package com.example.a300142288.yogaproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 */

public class UsersDataSource {

    SQLiteDatabase db;
    SQLiteOpenHelper dbhelper;
    public static final String LOGTAG = "YOGALOG";

    StringBuilder outputText = new StringBuilder();


    public UsersDataSource(Context context){
        dbhelper = new UsersDBOpenHelper(context);

    };//constructor

    private static final String[] allColumns={
        UsersDBOpenHelper.COLUMN_ID,
        UsersDBOpenHelper.COLUMN_NAME};

    public void open(){
        db = dbhelper.getWritableDatabase();
        Log.i(LOGTAG, "database opened");
    }
    public void close(){
        dbhelper.close();
        Log.i(LOGTAG, "database closed");
    }

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



    //checks if username exists, returns boolean
    public boolean login(String i)
    {
        Cursor query= db.rawQuery("select * from users where name='"+i+"'", null);

        if(query.getCount() > 0){
            Log.i(LOGTAG, "User Exists");
            query.close();
            return true;
        }
        Log.i(LOGTAG, "User Doesnt Exist");
        query.close();
        return false;
    }//login

    //debugging, post all names from users database to log
    public void checkDBs()
    {
        Cursor query= db.rawQuery("select * from users", null);

        query.moveToFirst();
        while(query.moveToNext()){
            Log.i(LOGTAG, "cacheName:"+ query.getString(query.getColumnIndex("name")));
        }
        query.close();
    }//checkdbs

    //checks the 'is watching' status from user database where id is i
    //if 1 means they were referenced to the page from continue, if 0 means
    //they were referenced from the selection list.
    public boolean checkWatched(int i)
    {

        //String queryStr = "Select * FROM  users";
        open();
        Cursor query= db.rawQuery("select * from users where userID='"+i+"'", null);
        Log.i(LOGTAG,"checkwatched method top "+ query.getCount());
        if(query.getCount()>0) {
            query.moveToFirst();

            Log.i(LOGTAG, "haswatchedLevel:" + query.getInt(query.getColumnIndex("level")));
            Log.i(LOGTAG, "haswatchedPose:" + query.getInt(query.getColumnIndex("pose")));
            if (query.getInt(query.getColumnIndex("haswatched")) !=0 ) {
                return true;
            }
        }
        query.close();
        return false;
    }//checkwatched

    //returns the level of the last watched video for hash value, paired with getPose below
    public int getLevel(int i)
    {
        open();
        Cursor query= db.rawQuery("select * from users where userID='"+i+"'", null);

        if(query.getCount()>0) {
            query.moveToFirst();
            return query.getInt(query.getColumnIndex("level"));
        }
        return 2;
    }//getlevel

    //returns pose location of last watched video for hash value, paired wth gerLevel above
    public int getPose(int i)
    {
        open();
        Cursor query= db.rawQuery("select * from users where userID='"+i+"'", null);

        if(query.getCount()>0) {
            query.moveToFirst();
            return query.getInt(query.getColumnIndex("pose"));
        }
        return 2;
    }//getPose





    //creates new DB entry, and enters name for it. returns user object with only name/id
    public User createUser(User user){
        ContentValues values = new ContentValues();
        values.put("name",user.getName());

        long insertid = db.insert(UsersDBOpenHelper.TABLE_USERS,null,values);
        user.setId(insertid);

        Log.i(LOGTAG, "inside createuser: "+user.getId() + " " + user.getName());
        return user;

    }//createUser

    //when the user watches a video the DB enrty for their userID is automatically
    //updated to reflect what the last video (this one) was. Used for continue on gridview
    public void createLastWatched(int userID, int Level, int Pose){
        open();
        Log.i(LOGTAG, "inside createlastwatched: " + userID + "level"+Level+" pose"+Pose);
        ContentValues values = new ContentValues();
        values.put("level", Level);
        values.put("pose", Pose);
        values.put("haswatched", 1);
        db.update(UsersDBOpenHelper.TABLE_USERS, values, "userID="+userID, null);

    }



}
