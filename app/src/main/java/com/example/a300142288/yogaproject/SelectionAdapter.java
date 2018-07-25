package com.example.a300142288.yogaproject;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 */

public class SelectionAdapter extends BaseAdapter {

    private Activity context;
    private List<Integer> optList = new ArrayList<Integer>();


    public SelectionAdapter(Activity context, List<Integer> opt){
        super();
        this.context = context;
        optList = opt;



    }//public selectionadapter

    @Override
    public int getCount() {
        return this.optList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.optList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView gridPic = new ImageView(context);
        gridPic.setImageResource(optList.get(i));
        gridPic.setScaleType(ImageView.ScaleType.FIT_XY);
        gridPic.setLayoutParams(new GridView.LayoutParams(300,300));

        return gridPic;
    }
}
