package com.example.cyclingapp2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cyclingapp2.HomePage;
import com.example.cyclingapp2.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // Create a Handler to delay the transition to the home screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the MainActivity (home screen)
                Intent intent = new Intent(Splash.this, HomePage.class);
                startActivity(intent);

                // Close the splash activity to prevent going back
                finish();
            }
        }, 5000); // Delay for 5 seconds (5000 milliseconds)
    }
}
