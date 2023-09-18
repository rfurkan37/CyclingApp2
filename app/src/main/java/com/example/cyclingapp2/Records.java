package com.example.cyclingapp2;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import java.io.File;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cyclingapp2.ui.CyclingData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Records extends AppCompatActivity {
    private ArrayList<Integer> recordIndices;
    private ArrayList<CyclingData> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records);

        // Retrieve the cyclingDataList from the intent
        Intent intent = getIntent();
        this.recordIndices = (ArrayList<Integer>) getIntent().getSerializableExtra("indices");
        this.records = (ArrayList<CyclingData>) getIntent().getSerializableExtra("records");

        LinearLayout dataLayout = findViewById(R.id.containerLayout2);


        if(records==null ||records.isEmpty())
        {  View dataItemView = getLayoutInflater().inflate(R.layout.custom_card_view, null);
            TextView topText = dataItemView.findViewById(R.id.textViewTop);
            topText.setText("There is no travel yet, what are you waiting for?");
            dataLayout.addView(dataItemView);}



            if(records!=null && !records.isEmpty())
            {
                for (int i = 0; i < records.size(); i++) {
                    CyclingData cyclingData = records.get(i);
                    addDataItemToLayout(dataLayout, cyclingData, i);
                }
            }

        ImageButton backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    // Add a cycling data item to the layout
    private void addDataItemToLayout(LinearLayout layout, CyclingData cyclingData, int dataCount) {
        // Create a new instance of the custom card view
        View dataItemView = getLayoutInflater().inflate(R.layout.custom_card_view, null);
        TextView topText= dataItemView.findViewById(R.id.textViewTop);
        if(dataCount==0) topText.setText("Maximum Average Speed");
        else if (dataCount==1) topText.setText("Maximum Speed");
        else if (dataCount==2) topText.setText("Maximum Distance Travelled");
        else if (dataCount==3) topText.setText("Maximum Time Travelled");


        // Set data to UI elements within the custom card view
        TextView textViewDate = dataItemView.findViewById(R.id.textViewDate);
        ImageView imageViewData = dataItemView.findViewById(R.id.imageViewData);
        TextView textViewTime = dataItemView.findViewById(R.id.textViewTime);
        TextView textViewAverageSpeed = dataItemView.findViewById(R.id.textViewAverageSpeed);

        // Set your data to the UI elements here
        textViewDate.setText(String.valueOf(cyclingData.getDate()));
        // Set image, time, and average speed to corresponding views here
        String imagePath = "image" + String.valueOf(recordIndices.get(dataCount)) + ".png";

        // Load and display the image using Glide
        Glide.with(this)
                .load(Uri.fromFile(new File(getFilesDir(), imagePath)))
                .into(imageViewData);

        textViewTime.setText("Time: " + String.format("%.2f", cyclingData.getTime())+"min");
        textViewAverageSpeed.setText("Av. Speed: " + String.format("%.2f", cyclingData.getAvSpeed())+ " km/h");
        layout.addView(dataItemView);
        dataItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the DetailActivity
                Intent intent = new Intent(Records.this, Detail.class);

                // Pass data to the DetailActivity through the intent
                intent.putExtra("imagePath", imagePath); // Pass the image path
                intent.putExtra("avSpeed", cyclingData.getAvSpeed());
                intent.putExtra("maxSpeed", cyclingData.getMaxSpeed());
                intent.putExtra("date", String.valueOf(cyclingData.getDate()));
                intent.putExtra("distance",cyclingData.getDistance());
                intent.putExtra("note", cyclingData.getNote());
                intent.putExtra("time", cyclingData.getTime());

                startActivity(intent);
            }
        });
    }
}
