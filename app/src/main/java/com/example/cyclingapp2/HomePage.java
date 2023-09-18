package com.example.cyclingapp2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;



public class HomePage extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1001; // Choose any unique value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            // Permission is already granted, you can proceed with location-related tasks
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // Find the buttons by their IDs
        Button startJourneyButton = findViewById(R.id.start_journey);
        Button showStatsJourney = findViewById(R.id.show_stats);

        // Set click listeners for the buttons
        startJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the new activity
                Intent intent = new Intent(HomePage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        showStatsJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the new activity
                Intent intent = new Intent(HomePage.this, DisplayDataActivity.class);
                startActivity(intent);
            }
        });
    }
}
