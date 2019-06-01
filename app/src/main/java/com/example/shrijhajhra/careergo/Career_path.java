package com.example.shrijhajhra.careergo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Career_path extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_path);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //used to get the message we passed in intent
        Intent intent = getIntent();
       String clicked_career = intent.getExtras().getString("Career name");
        setTitle(clicked_career);
    }
}
