package com.example.a300142288.yogaproject;

/**
 */

public class User {
    private long id;
    private String name;
    private int lastLevel;
    private int lastPose;


    public void setId(long i){
        this.id=i;
    }
    public void setName(String i){
        this.name=i;
    }
    public long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public void setLastLevel(int i){this.lastLevel=i;}
    public int getLastLevel(){return lastLevel;}
    public void setLastPose(int i){this.lastPose=i;}
    public int getLastPose(){return lastPose;}


}
