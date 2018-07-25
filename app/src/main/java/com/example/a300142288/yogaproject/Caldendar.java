package com.example.a300142288.yogaproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Caldendar extends AppCompatActivity {
    UsersDataSource datasource;
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caldendar);

        datasource = new UsersDataSource(this);

        final SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);

        //get userID
        int k = userDetails.getInt("userid", 0);


        Toast.makeText(this, String.valueOf(k), Toast.LENGTH_SHORT).show();



        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2017, 10, 29, 11, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 10, 29, 11, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Practice Your Yoga")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Friendly reminder from  NamasTime app")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Home")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "simon@gmail.com,nick@gmail.com");

        //.putExtra(Events.EVENT_COLOR, 0xffff0000)

        startActivity(intent);

    }
}
