package com.example.a300142288.yogaproject;

/**
 */

public class Lesson {
    private long id;
    private String title, desc;
    private int level, pose;


    public void setId(long i){
        this.id=i;
    }
    public long getId(){
        return id;
    }
    public void setTitle(String i){
        this.title=i;
    }
    public String getTitle(){
        return title;
    }
    public void setDesc(String i){this.desc = i;}
    public String getDesc(){return desc;}
    public void setLevel(int i){this.level = i;}
    public int getLevel(){return level;}
    public void setPose(int i){this.pose=i;}
    public int getPose(){return pose;}

}
