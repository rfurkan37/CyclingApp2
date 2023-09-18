package com.example.cyclingapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.lang.Double;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {
    private Chronometer chronometer;
    private TextView speedTextView;
    private  TextView averageTextView;
    private  TextView maxSpeedTextView;
    private  TextView distanceTextView;

    private long pauseOffset;
    private boolean running;
    private double velocity = 0;

    private double maxSpeed = 0;

    double averageSpeed = 0;
    long changes = 1;

    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.stop();
        running = false;
        pauseOffset = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());
        speedTextView = findViewById(R.id.textViewSpeed);
        averageTextView = findViewById(R.id.textViewSlope);
        maxSpeedTextView = findViewById(R.id.textViewMaxSpeed);
        distanceTextView = findViewById(R.id.textViewDistance);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager != null) {
            Sensor acceleroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            if (acceleroSensor != null) {
                sensorManager.registerListener(this, acceleroSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        } else {
            Toast.makeText(this, "Accelero sensor not detected.", Toast.LENGTH_SHORT).show();
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0 , 0, this);
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
    public void startChronometer(View v) {
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }
    public void pauseChronometer(View v) {
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }
    public void resetChronometer(View v) {
        chronometer.stop();
        running = false;
        pauseOffset = 0;
        ConfirmationDialogFragment dialog = new ConfirmationDialogFragment();

        // Set the data using the setter methods
        dialog.setSpeedValue(velocity);
        dialog.setMaxSpeedValue(maxSpeed);
        dialog.setAverageSpeedValue(averageSpeed);
        long time = ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000);
        double timeRes = time/60.0;
        dialog.setTimeValue(timeRes);
        dialog.setDistanceValue(time*(averageSpeed/3600));
        dialog.show(getSupportFragmentManager(), "ConfirmationDialogFragment");
        chronometer.setBase(SystemClock.elapsedRealtime());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged (SensorEvent event) {
        TextView accelerationview = (TextView)findViewById(R.id.textViewAcceleration);
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && running){
            accelerationview.setText("Acceleration: " + String.format("%.2f", event.values[0]));
            
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy){
    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onLocationChanged(@NonNull Location location) {
        changes = changes + 1;
        double speed = location.getSpeed();
        this.velocity = speed;

        if(running){
        averageSpeed = averageSpeed + (speed - averageSpeed)/changes;

        System.out.println(changes);
        averageTextView.setText("Av.Speed: " + String.format("%.2f km/h", averageSpeed));

        if(speed>this.maxSpeed)
            maxSpeed = speed;
        long time = ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000);// Replace with your actual value
        speedTextView.setText("Current Speed: " + String.format("%.2f km/h", speed));
        maxSpeedTextView.setText("Speed Record: " + String.format("%.2f km/h", maxSpeed));
        distanceTextView.setText("Distance Travelled: " + String.format("%.2f", time*(averageSpeed/3600)) + "km");
}}

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}