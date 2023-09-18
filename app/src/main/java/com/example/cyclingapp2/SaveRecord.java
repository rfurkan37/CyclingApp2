package com.example.cyclingapp2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cyclingapp2.ui.CyclingData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SaveRecord extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 101;
    private ImageView imageView;
    private EditText noteField;

    private double timeValue;

    private boolean camFlag;

    private double averageSpeedValue;

    private double distanceValue;

    private double maxSpeed;


    public void setAverageSpeedValue(double averageSpeedValue) {
        this.averageSpeedValue = averageSpeedValue;
    }

    public void setTimeValue(double timeValue) {
        this.timeValue = timeValue;
    }

    public void setDistanceValue(long distanceValue) {
        this.distanceValue = distanceValue;
    }

    public void setMaxSpeed(double maxSpeedValue) {
        this.maxSpeed = maxSpeedValue;
    }


    // Function to count dictionaries in a JSON file
    private int countDictionariesInJsonFile(String filePath) {
        int count = 0;

        try {
            // Read the JSON file content as a string
            FileInputStream inputStream = new FileInputStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            String jsonContent = stringBuilder.toString();

            // Parse the JSON content into a JSONArray
            JSONArray jsonArray = new JSONArray(jsonContent);

            // Count the number of dictionaries (JSON objects) in the array
            count = jsonArray.length();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return count-1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_record);
        Intent intent = getIntent();
        camFlag = false;
        if (intent != null) {
            averageSpeedValue = intent.getDoubleExtra("avSpeed", 0L);
            timeValue = intent.getDoubleExtra("time", 0L);
            distanceValue = intent.getDoubleExtra("distance", 0L);
            maxSpeed = intent.getDoubleExtra("maxSpeed", 0L);
        }

        TextView avSpeedField = findViewById(R.id.averageSpeedText);
        TextView distanceField = findViewById(R.id.distanceText);
        TextView timeField = findViewById(R.id.timeText);
        TextView maxSpeedText = findViewById(R.id.maxSpeedText);

        avSpeedField.setText(String.format("Your average speed is: %s", String.format("%.2f", averageSpeedValue)));
        distanceField.setText(String.format("Your distance is: %s", String.format("%.2f", distanceValue)));
        maxSpeedText.setText(String.format("Your max speed is: %s", String.format("%.2f", maxSpeed)));
        timeField.setText(String.format("Travel time is: %s", String.format("%.2f min", timeValue)));

        imageView = findViewById(R.id.imageView); // ImageView for displaying the taken image
        noteField = findViewById(R.id.noteField); // EditText for the note

        Button takePictureButton = findViewById(R.id.takePictureButton);
        Button saveButton = findViewById(R.id.saveButton);

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the camera to take a picture
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = noteField.getText().toString();
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = today.format(formatter);

                TextView noteFieldText = findViewById(R.id.noteField);
                CyclingData cyclingData = new CyclingData(averageSpeedValue, distanceValue, timeValue,
                        noteFieldText.getText().toString(), maxSpeed, formattedDate);

                try {
                    if(camFlag==false)
                    {
                        Toast.makeText(SaveRecord.this, "Please take a picture", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    File file = new File(getFilesDir(), "data.json");
                    if (!file.exists()) {
                        // Create the file if it doesn't exist
                        file.createNewFile();
                    }

                    FileInputStream inputStream = openFileInput("data.json");
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder jsonData = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        jsonData.append(line);
                    }

                    // Parse the existing JSON data into a list of CyclingData objects
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<CyclingData>>() {}.getType();
                    List<CyclingData> cyclingDataList;

                    if (jsonData.length() > 0) {
                        cyclingDataList = gson.fromJson(jsonData.toString(), listType);
                    } else {
                        cyclingDataList = new ArrayList<>(); // Create a new list if the file is empty
                    }

                    // Create a new CyclingData object
                    CyclingData newCyclingData = new CyclingData(averageSpeedValue, distanceValue, timeValue, note, maxSpeed,formattedDate);

                    // Add the new element to the list
                    cyclingDataList.add(newCyclingData);

                    // Serialize the updated list back to JSON
                    String updatedJsonData = gson.toJson(cyclingDataList);

                    // Create or open the JSON file for writing
                    FileOutputStream outputStream = openFileOutput("data.json", Context.MODE_PRIVATE);

                    // Write the updated JSON data to the file
                    outputStream.write(updatedJsonData.getBytes());

                    // Close the file
                    outputStream.close();

                    Toast.makeText(SaveRecord.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String filePath = getFilesDir() + "/data.json"; // Change the file path accordingly
                int dictionaryCount = countDictionariesInJsonFile(filePath);

                imageView.setDrawingCacheEnabled(true);
                Bitmap bitmap = imageView.getDrawingCache();
                String fileName = "image" + (String.valueOf(dictionaryCount)) + ".png";
                File file = new File(getFilesDir(), (fileName)); // Change the file name and path as needed
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SaveRecord.this, HomePage.class); // Replace HomePage with the actual name of your activity
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            // Retrieve the image taken by the camera
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            // Display the image in the ImageView
            imageView.setImageBitmap(imageBitmap);
            camFlag = true;
        }
        else camFlag = false;
    }

}