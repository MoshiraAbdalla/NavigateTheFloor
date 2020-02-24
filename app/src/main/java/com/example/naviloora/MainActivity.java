package com.example.naviloora;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
  private ImageView image;
  private ViewGroup mainlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=(ImageView)findViewById(R.id.Man);
        //relativeLayout=(RelativeLayout)findViewById(R.id.RelativeLayout);
        mainlayout=(RelativeLayout) findViewById(R.id.RelativeLayout);
        image=(ImageView) findViewById(R.id.Man);
        image.setX(200);
        image.setY(250);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance(); // main access point for my database
        DatabaseReference myRef = database.getReference("message");// get a reference for a specific part of the database "the messages portion of the database

       // myRef.setValue("Hello, World!");

    }






}
