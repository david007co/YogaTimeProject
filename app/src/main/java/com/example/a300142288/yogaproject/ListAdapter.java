package com.example.a300142288.yogaproject;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 */

public class ListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private List<String> levels;
    private HashMap<String, List<String>> poses;

    public ListAdapter(Activity context, List<String> levels, HashMap<String, List<String>> poses) {
        super();
        this.context = context;
        this.levels = levels;
        this.poses=poses;

    }

    @Override
    public int getGroupCount() {
        //how many level 1 groups (headers)
        return levels.size();
    }

    @Override
    public int getChildrenCount(int levelPos) {
        //returns count of children for levels
        return this.poses.get(this.levels.get(levelPos)).size();
    }

    @Override
    public Object getGroup(int levelPos) {
        return this.levels.get(levelPos);
    }

    @Override
    public Object getChild(int levelPos, int posePos){
        //returns the child object at levelpos level, childpos location
        return this.poses.get(this.levels.get(levelPos)).get(posePos);
    }

    @Override
    public long getGroupId(int levelPos) {
        return levelPos;
    }

    @Override
    public long getChildId(int levelPos, int posePos){
        //returns child id
        return posePos;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int levelPos, boolean b, View view, ViewGroup viewGroup) {
        String levelTitle = (String) getGroup(levelPos);
        LayoutInflater inflater = context.getLayoutInflater();
        if (view == null) {
            view=inflater.inflate(R.layout.level_one, null);
        }
        TextView levelHead = (TextView) view.findViewById(R.id.poseLevelOne);
        levelHead.setText(levelTitle);
        return view;
    }


    /////

    @Override
    public View getChildView(int levelPos, int posePos, boolean b, View view, ViewGroup viewGroup){
        //get text of object at (level,pose) and insert it into list for group levelPos
        final String poseText = (String) getChild(levelPos, posePos);
        LayoutInflater inflater = context.getLayoutInflater();

        if(view==null){
            view=inflater.inflate(R.layout.level_two, null);
        }

        TextView pose = (TextView) view.findViewById(R.id.poseLevelTwo);
        pose.setText(poseText);
        return view;
    }

    @Override
    public boolean isChildSelectable(int levelPos, int posePos) {
        return true;
    }


    ///////////
/*
    @Override
    public int getCount() {
        return poses.length;
    }

    @Override
    public Object getItem(int i) {
        return poses[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = context.getLayoutInflater();
        if(view == null){
            view = inflater.inflate(R.layout.level_one, null);
        }
        TextView txt = (TextView)view.findViewById(R.id.pose);
        txt.setText(poses[i]);
        return view;

    }//getview
    */
}//class
