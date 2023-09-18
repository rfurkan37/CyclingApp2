package com.example.cyclingapp2;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import java.io.File;
import android.net.Uri;
import android.widget.Button;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DisplayDataActivity extends AppCompatActivity {
private ArrayList<CyclingData> cyclingDataList;
private ArrayList<CyclingData> records;
private ArrayList<Integer> recordIndices;


    private void updateRecords(List<CyclingData> cyclingDataList) {
        if (cyclingDataList == null || cyclingDataList.isEmpty()) {
            // Handle the case where the list is empty or null
            return;
        }

        // Initialize variables to keep track of the greatest values
        CyclingData greatestAvgSpeedData = cyclingDataList.get(0);
        CyclingData greatestMaxSpeedData = cyclingDataList.get(0);
        CyclingData greatestDistanceData = cyclingDataList.get(0);
        CyclingData greatestTimeData = cyclingDataList.get(0);
        for (int i = 0; i < cyclingDataList.size(); i++) {
            CyclingData data = cyclingDataList.get(i);
            // Calculate the greatest average speed
            if (data.getAvSpeed() >= greatestAvgSpeedData.getAvSpeed()) {
                greatestAvgSpeedData = data;
            }

            // Calculate the greatest maximum speed
            if (data.getMaxSpeed() >= greatestMaxSpeedData.getMaxSpeed()) {
                greatestMaxSpeedData = data;
            }

            // Calculate the greatest distance
            if (data.getDistance() >= greatestDistanceData.getDistance()) {
                greatestDistanceData = data;
            }

            // Calculate the greatest time
            if (data.getTime() >= greatestTimeData.getTime()) {
                greatestTimeData = data;
            }
        }

        // Update the records with the greatest values
        records = new ArrayList<>();
        recordIndices = new ArrayList<>();

        records.add(greatestAvgSpeedData);
        recordIndices.add(this.cyclingDataList.indexOf(greatestAvgSpeedData));

        records.add(greatestMaxSpeedData);
        recordIndices.add(this.cyclingDataList.indexOf(greatestMaxSpeedData));

        records.add(greatestDistanceData);
        recordIndices.add(this.cyclingDataList.indexOf(greatestDistanceData));

        records.add(greatestTimeData);
        recordIndices.add(this.cyclingDataList.indexOf(greatestTimeData));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_data);
        LinearLayout dataLayout = findViewById(R.id.containerLayout);

        // Read JSON data and parse it into a list of CyclingData objects
        List<CyclingData> cyclingDataList = readAndParseJsonData();
        if(cyclingDataList.isEmpty()) {
            View dataItemView = getLayoutInflater().inflate(R.layout.custom_card_view, null);
            TextView topText = dataItemView.findViewById(R.id.textViewTop);
                topText.setText("There is no travel yet, what are you waiting for?");
            dataLayout.addView(dataItemView);
        }

        for (int i = 0; i < cyclingDataList.size(); i++) {
            CyclingData cyclingData = cyclingDataList.get(i);
            addDataItemToLayout(dataLayout, cyclingData, i + 1);
        }

        updateRecords(this.cyclingDataList);

        Button showRecordsButton = findViewById(R.id.showRecordsButton);
        showRecordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the Records activity
                Intent intent = new Intent(DisplayDataActivity.this, Records.class);

                // Pass the records  array to the Records activity
                intent.putExtra("records", records);
                intent.putExtra("indices", recordIndices);
                startActivity(intent);
            }
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    // Read JSON data from a file and parse it into a list of CyclingData objects
    private List<CyclingData> readAndParseJsonData() {
        StringBuilder jsonData = null;
        File fileDirectory = getFilesDir();

        File file = new File(fileDirectory, "data.json");

        if (!file.exists()) {
            // The file does not exist, return an empty list
            return new ArrayList<>();
        }

        try {
            FileInputStream inputStream = openFileInput("data.json");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            jsonData = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                jsonData.append(line);
            }

            Gson gson = new Gson();
            Type listType = new TypeToken<List<CyclingData>>() {}.getType();
            this.cyclingDataList = gson.fromJson(jsonData.toString(), listType);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<List<CyclingData>>() {}.getType();
        this.cyclingDataList = gson.fromJson(String.valueOf(jsonData), listType);
        return cyclingDataList;
    }

    // Add a cycling data item to the layout
    private void addDataItemToLayout(LinearLayout layout, CyclingData cyclingData, int dataCount) {
        // Create a new instance of the custom card view
        View dataItemView = getLayoutInflater().inflate(R.layout.custom_card_view, null);
        TextView topText = dataItemView.findViewById(R.id.textViewTop);
        if(dataCount==1)
            topText.setText("Your travel archive");

        // Set data to UI elements within the custom card view
        TextView textViewDate = dataItemView.findViewById(R.id.textViewDate);
        ImageView imageViewData = dataItemView.findViewById(R.id.imageViewData);
        TextView textViewTime = dataItemView.findViewById(R.id.textViewTime);
        TextView textViewAverageSpeed = dataItemView.findViewById(R.id.textViewAverageSpeed);

        // Set your data to the UI elements here
        textViewDate.setText(String.valueOf(cyclingData.getDate()));
        // Set image, time, and average speed to corresponding views here
        String imagePath = "image" + String.valueOf(dataCount - 1) + ".png";

        // Load and display the image using Glide
        Glide.with(this)
                .load(Uri.fromFile(new File(getFilesDir(), imagePath)))
                .into(imageViewData);

        textViewTime.setText("Time: " +  String.format("%.2f", cyclingData.getTime()) +"min");
        textViewAverageSpeed.setText("Av. Speed: " + String.format("%.2f", cyclingData.getAvSpeed()) + "km/h");

        dataItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the DetailActivity
                Intent intent = new Intent(DisplayDataActivity.this, Detail.class);

                // Pass data to the DetailActivity through the intent
                intent.putExtra("imagePath", imagePath); // Pass the image path
                intent.putExtra("avSpeed", cyclingData.getAvSpeed());
                intent.putExtra("maxSpeed", cyclingData.getMaxSpeed());
                intent.putExtra("date", String.valueOf(cyclingData.getDate()));
                intent.putExtra("distance", cyclingData.getDistance());
                intent.putExtra("note", cyclingData.getNote());
                intent.putExtra("time", cyclingData.getTime());

                startActivity(intent);
            }
        });
        // Add the custom card view to the layout
        layout.addView(dataItemView);
    }
}
