package com.example.cyclingapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ConfirmationDialogFragment extends BottomSheetDialogFragment {

    private double speedValue;
    private double averageSpeedValue;
    private double timeValue;
    private double maxSpeedValue;
    private double distanceValue;

    public void setSpeedValue(double speedValue) {
        this.speedValue = speedValue;
    }

    public void setAverageSpeedValue(double averageSpeedValue) {
        this.averageSpeedValue = averageSpeedValue;
    }
    public void setMaxSpeedValue(double maxSpeedValue) {
        this.maxSpeedValue = maxSpeedValue;
    }
    public void setTimeValue(double timeValue) {
        this.timeValue = timeValue;
    }
    public void setDistanceValue(double distanceValue) {
        this.distanceValue = distanceValue;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation_dialog, container, false);
        Button yesButton = view.findViewById(R.id.yesButton);
        Button noButton = view.findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle 'Yes' button click
                Intent intent = new Intent(getActivity(), SaveRecord.class);
                intent.putExtra("time", timeValue);
                intent.putExtra("avSpeed", averageSpeedValue);
                intent.putExtra("distance", distanceValue);
                intent.putExtra("maxSpeed", maxSpeedValue);
                startActivity(intent);
                dismiss(); // Close the dialog
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle 'No' button click
                dismiss(); // Close the dialog
            }
        });
        return view;
    }
}
