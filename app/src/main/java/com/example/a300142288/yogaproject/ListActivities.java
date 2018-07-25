package com.example.a300142288.yogaproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListActivities extends AppCompatActivity {

    List<String> lstLevels;
    HashMap<String, List<String>> lstPoses;
    String[] levelOnePoses = {"Mountain Pose", "Downward Dog", "Warrior", "Tree Pose"};
    String[] levelTwoPoses = {"Bridge Pose", "Triangle Pose", "Seated Twist"};
    String[] levelThreePoses = {"Cobra", "Pidgeon Pose", "Crow Pose"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activities);

        final SharedPreferences selectionDetails = this.getSharedPreferences("selectionDetails", MODE_PRIVATE);
        ExpandableListView yogaList = (ExpandableListView)findViewById(R.id.posesLevels);
        genList();
        ListAdapter lstAdapter = new ListAdapter(this,lstLevels, lstPoses);
        yogaList.setAdapter(lstAdapter);

            //if an element is clicked shared preferences with that index are created, and the 'watching' status is
            //sett to 0 to signify that the list was clicked nad not 'continue' from menu.
        yogaList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int levelPos, int posePos, long id) {

                sharedPref(levelPos,posePos);
                setWatch();
                int i=selectionDetails.getInt("selectionLevel",0);
                int j=selectionDetails.getInt("selectionPose",0);
                Toast.makeText(ListActivities.this, "LEVEL: " + i + "POSE: " + j, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ListActivities.this, WatchLesson.class));
            return false;
            }
        });


    }//oncreate
    protected void setWatch(){
        SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor edit = userDetails.edit();
        int i = userDetails.getInt("userid", 0);
        edit.clear();
        edit.putInt("userid", i);
        edit.putInt("userWatching", 0);
        edit.commit();
        //Toast.makeText(this, "Login details are saved.. userid: "+i , Toast.LENGTH_SHORT).show();
    }


    protected void sharedPref(int levelPos, int posePos){
        SharedPreferences selectionDetails = this.getSharedPreferences("selectionDetails", MODE_PRIVATE);
        SharedPreferences.Editor edit = selectionDetails.edit();

        edit.clear();
        edit.putInt("selectionLevel", levelPos);
        edit.putInt("selectionPose",posePos);
        edit.commit();
        //Toast.makeText(this, "Login details are saved.. userid: "+i , Toast.LENGTH_SHORT).show();
    }


    private void genList() {
        lstLevels = new ArrayList<String>();
        lstPoses = new HashMap<String, List<String>>();

        // create headers
        lstLevels.add("Level One");
        lstLevels.add("Level Two");
        lstLevels.add("Level Three");

        //create arrays for poses, insert arrays created above to each
        List<String> level_One = new ArrayList<String>();
        List<String> level_Two = new ArrayList<String>();
        List<String> level_Three = new ArrayList<String>();

        //for each loops, except that android studio wont allow foreach, only this.

        for (String a:levelOnePoses
                ) {
            level_One.add(a);
        }

        for (String a:levelTwoPoses
                ) {
            level_Two.add(a);
        }

        for (String a:levelThreePoses
                ) {
            level_Three.add(a);
        }

        //finally assign poses to their corresponding levels in key-value pairs
        lstPoses.put(lstLevels.get(0), level_One); // Header, Child data
        lstPoses.put(lstLevels.get(1), level_Two);
        lstPoses.put(lstLevels.get(2), level_Three);
    }






    }


