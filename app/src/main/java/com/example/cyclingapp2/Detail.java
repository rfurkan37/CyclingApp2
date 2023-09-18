package com.example.cyclingapp2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;

public class Detail extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_detail);

        // Get references to UI elements
        ImageView imageViewDetail = findViewById(R.id.imageViewDetail);
        TextView noteText = findViewById(R.id.noteText);
        TextView dateText = findViewById(R.id.dateText);
        TextView timeText = findViewById(R.id.timeText);
        TextView maximumSpeedText = findViewById(R.id.maximumSpeedText);
        TextView avSpeedText = findViewById(R.id.averageSpeedText);
        TextView distanceText = findViewById(R.id.distanceText);

        // Get data from the intent
        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("imagePath");
        Double avSpeed = intent.getDoubleExtra("avSpeed", 0L);
        Double maxSpeed = intent.getDoubleExtra("maxSpeed", 0L);
        String date = intent.getStringExtra("date");
        String note = intent.getStringExtra("note");
        Double time = intent.getDoubleExtra("time", 0L);
        Double distance = intent.getDoubleExtra("distance", 0L);

        noteText.setText(note);
        dateText.setText("Date: " + date);
        timeText.setText("Journey time: " + String.format("%.2f", time) + "min");
        maximumSpeedText.setText("Your max speed: " + String.format("%.2f", maxSpeed) +" km/h");
        avSpeedText.setText("Your average speed: " + String.format("%.2f", avSpeed)+" km/h");
        distanceText.setText("Distance you travelled: " + String.format("%.3f", distance)+" km");
        Glide.with(this)
                .load(Uri.fromFile(new File(getFilesDir(), imagePath)))
                .into(imageViewDetail);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
