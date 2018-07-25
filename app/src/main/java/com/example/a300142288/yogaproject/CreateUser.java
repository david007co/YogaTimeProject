package com.example.a300142288.yogaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CreateUser extends AppCompatActivity {

    String cUserName;
    public static final String LOGTAG = "YOGALOG";
    UsersDataSource datasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        final EditText txtUserName =(EditText)findViewById(R.id.txtCreateName);
        Button button = (Button)findViewById(R.id.btnCreateCreate);
        datasource = new UsersDataSource(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cUserName = txtUserName.getText().toString();
                datasource.open();
                if(datasource.login(cUserName)){

                    Toast.makeText(getApplicationContext(), "Username Already Exists", Toast.LENGTH_SHORT).show();
                    txtUserName.setText("");
                }
                else{
                    createUser(cUserName);
                    Toast.makeText(CreateUser.this,"Created user: "+cUserName,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateUser.this, SelectionScreen.class));
                }

            }
        });

    }

    public void createUser(String i){

        User user = new User();
        user.setName(i);
        user = datasource.createUser(user);
        Log.i(LOGTAG, "new user created with id "+user.getId() + " " + user.getName());
}
}
