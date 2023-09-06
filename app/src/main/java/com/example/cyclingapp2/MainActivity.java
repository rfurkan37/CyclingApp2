package com.example.cyclingapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Double;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private long velocity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = (Chronometer)findViewById(R.id.chronometer);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager != null){
            Sensor acceleroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            if(acceleroSensor != null){
                sensorManager.registerListener(this,acceleroSensor,SensorManager.SENSOR_DELAY_NORMAL);
            }
        }

        else {
            Toast.makeText(this,"Accelero sensor not detected.",Toast.LENGTH_SHORT).show();
        }
    }
    public void editVelocity() {
        TextView velocity = (TextView)findViewById(R.id.textViewSpeed);
        TextView acc = (TextView)findViewById(R.id.textViewAcceleration);

        final double [] accele = new double[1];
        final double [] time = new double[1];

        time[0] = (SystemClock.elapsedRealtime()-chronometer.getBase()) / 1000;
        accele[0] = Double.parseDouble(acc.getText().toString());
        velocity.setText("ms" + time[0]*accele[0]);
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
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged (SensorEvent event) {
        TextView accelerationview = (TextView)findViewById(R.id.textViewAcceleration);

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelerationview.setText(String.format("%f", event.values[0]));
            if (running) editVelocity();
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy){
    }
}