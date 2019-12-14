package com.example.weather_application;

import com.example.weather_application.MapsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.weather_application.R;


public class HistoryActivity extends MapsActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
    }


    public void Maps(View view) {
        Intent intent = new Intent(HistoryActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}