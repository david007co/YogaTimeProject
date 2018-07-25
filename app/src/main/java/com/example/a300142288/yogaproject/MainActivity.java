//button to move to create user activity not working yet.
// Everything else in order - login checks count of recorsd with matching name
//and if count is 0 it fails login.
//to do: fix create user button, check if users > 1 how to adjust?










package com.example.a300142288.yogaproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //class level variables declared here
    public static final String LOGTAG = "YOGALOG";
    UsersDataSource datasource;
    String userName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText txtUserName =(EditText)findViewById(R.id.txtMainUser);
        userName = txtUserName.getText().toString();
        datasource = new UsersDataSource(this);

        //once DB is created set button properties & text entry
        Button btnLogin = (Button)findViewById(R.id.btnMainLogin);
        Button btnCreate = (Button)findViewById(R.id.btnMainCreate);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = txtUserName.getText().toString();

                //datasource.checkDBs();

                //Toast.makeText(getApplicationContext(), userName, Toast.LENGTH_SHORT).show();

                if(datasource.login(userName)){
                    int j = datasource.sharedPref(userName);
                    //Toast.makeText(getApplicationContext(), "userid:"+j, Toast.LENGTH_SHORT).show();
                    sharedPref(j);
                    Toast.makeText(getApplicationContext(), "User Found", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, SelectionScreen.class));

                }
                else{
                    Toast.makeText(getApplicationContext(), "username not found try again", Toast.LENGTH_SHORT).show();
                }


            }
        });//loginonclick

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateUser.class));


            }
        });//createonclick




    }//oncreate


    //sets shared preference userID for use in later activities
    protected void sharedPref(int i){
        SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor edit = userDetails.edit();
        edit.clear();
        edit.putInt("userid", i);
        edit.commit();
        //Toast.makeText(this, "Login details are saved.. userid: "+i , Toast.LENGTH_SHORT).show();
    }

    protected void onPause(){
        super.onPause();
        datasource.close();
    }//when activity pauses (off screen) close connection


    protected void onResume(){
        super.onResume();
        datasource.open();
    }//when activity unpauses connect to db




}//main
