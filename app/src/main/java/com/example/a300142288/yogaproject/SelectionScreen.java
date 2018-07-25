package com.example.a300142288.yogaproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SelectionScreen extends AppCompatActivity {

    List<Integer> opt = new ArrayList<Integer>();
    public static final String LOGTAG = "YOGALOG";


    private void addOptions(){

        opt.add(R.drawable.cont);
        opt.add(R.drawable.lessons);
        opt.add(R.drawable.fav);
        opt.add(R.drawable.cal);
        opt.add(R.drawable.logout);
    }

    //oct25slides

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);

        final SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
        int i = userDetails.getInt("userid", 4);
        //Toast.makeText(this, "Login details are accessed.. userid: "+i , Toast.LENGTH_SHORT).show();

        addOptions();
        GridView grid = (GridView)findViewById(R.id.gridView);
        grid.setAdapter(new SelectionAdapter(this,opt));

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            //continue = 0, ListActivities = 1, fav = 2, calendar = 3, logout = 4k
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //name of shared pref, default value
                int j =userDetails.getInt("userid", 4);
                if(i==0){
                    setWatch();
                    startActivity(new Intent(SelectionScreen.this, WatchLesson.class));
                }
                else if(i==1){
                    startActivity(new Intent(SelectionScreen.this, ListActivities.class));
                }
                else if(i==2){
                    Toast.makeText(SelectionScreen.this, "Feature in development stage. Wait soon for an update", Toast.LENGTH_SHORT).show();
                }
                else if(i==3){
                    startActivity(new Intent(SelectionScreen.this, Caldendar.class));
                }
                else if(i==4){
                    startActivity(new Intent(SelectionScreen.this, MainActivity.class));
                }


                }//onitemclick

        });//onclicklistener



    }//oncreate


    //if user selects 'continue' their watching status is automatically set to 1 to reflect
    //that they were linked to the watch page by continue. If they select the list their 'watching' status
    //is not updated until they select an entry, in case they back out nd then click on continue instead.
    protected void setWatch(){
        SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor edit = userDetails.edit();
        int i = userDetails.getInt("userid", 0);
        Log.i(LOGTAG, "user selected " + i);
        edit.clear();
        edit.putInt("userid", i);
        edit.putInt("userWatching", 1);
        edit.commit();
        //Toast.makeText(this, "Login details are saved.. userid: "+i , Toast.LENGTH_SHORT).show();
    }
}
