package com.example.weather_application;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


//public class MapsActivity extends AppComponentFactory{
//    Button myButton
//}

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private final static int marker_value = 10;

    private GoogleMap mMap;

    private Double markers[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMarker();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    public void onClick1(View view){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("weather.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS forecast (minTemp INTEGER, maxTemp INTEGER)");
        db.execSQL("INSERT INTO forecast VALUES (24, 23);");
        db.execSQL("INSERT INTO forecast VALUES (12, 31);");

        Cursor query = db.rawQuery("SELECT * FROM forecast;", null);
        TextView textView = (TextView) findViewById(R.id.textView1);
        if(query.moveToFirst()){
            do{
                int minTemp = query.getInt(0);
                int maxTemp = query.getInt(0);
                textView.append("Name: " + minTemp + " Age: " + maxTemp + "\n");
            }
            while(query.moveToNext());
        }
        query.close();
        db.close();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        addMarkers();
        mMap.setOnMarkerClickListener(new MarkerClickListener());

    }


//        Places.initialize(getApplicationContext(), "@string/google_maps_key");


    private void addMarkers(){
        for(int i=0; i<marker_value; i++){
            mMap.addMarker(new MarkerOptions().position(new LatLng(markers[i][0],markers[i][1])));
        }
    }


    private void getMarker(){
        markers = new Double[marker_value][2];
            double minLat = 90.00;
            double maxLat = -90.00;
            double minLon = 180.00;
            double maxLon = 0.00;
            for(int i=0; i<marker_value; i++){
                markers[i][0] = minLat + Math.random() * (maxLat - minLat);
                markers[i][1] = minLon + Math.random() * (maxLon - minLon);
            }


    }

    public void onClick(View view) {
        Intent intent = new Intent(MapsActivity.this, HistoryActivity.class);
        startActivity(intent);
    }



}
