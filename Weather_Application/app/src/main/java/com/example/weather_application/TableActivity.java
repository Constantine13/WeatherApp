package com.example.weather_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TableActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);
    }

    public void Maps(View view) {
        Intent intent = new Intent(TableActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    public void onClick(View view) {
        Intent intent = new Intent(TableActivity.this, HistoryActivity.class);
        startActivity(intent);
    }
}
