package com.example.cyclingapp2.ui;

import java.io.Serializable;

public class CyclingData implements Serializable {
    private double avSpeed;
    private double distance;
    private double time;
    private double maxSpeed;
    private String date;
    private String note;
    // Getter for avSpeed
    public double getAvSpeed() {
        return avSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }


    // Getter for distance
    public double getDistance() {
        return distance;
    }

    // Getter for time
    public double getTime() {
        return time;
    }
    public String getDate() {
        return date;
    }


    // Getter for note
    public String getNote() {
        return note;
    }
    public CyclingData(double avSpeed, double distance, double time, String note, double maxSpeed, String date) {
        this.avSpeed = avSpeed;
        this.distance = distance;
        this.time = time;
        this.note = note;
        this.maxSpeed = maxSpeed;
        this.date = date;
    }

    // Add getters and setters if needed
}
