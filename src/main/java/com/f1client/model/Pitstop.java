package com.f1client.model;

import com.google.gson.annotations.SerializedName;

public class Pitstop {
    @SerializedName("date")
    private String date;
    @SerializedName("driver_number")
    private int driverNumber;
    @SerializedName("lap_number")
    private int lapNumber;
    @SerializedName("meeting_key")
    private int meetingKey;
    @SerializedName("pit_duration")
    private float pitDuration;    
    @SerializedName("session_key")
    private int sessionKey;

    // Getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(int driverNumber) {
        this.driverNumber = driverNumber;
    }

    public int getLapNumber() {
        return lapNumber;
    }

    public void setLapNumber(int lapNumber) {
        this.lapNumber = lapNumber;
    }

    public int getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(int meetingKey) {
        this.meetingKey = meetingKey;
    }

    public float getPitDuration() {
        return pitDuration;
    }

    public void setPitDuration(float pitDuration) {
        this.pitDuration = pitDuration;
    }

    public int getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(int sessionKey) {
        this.sessionKey = sessionKey;
    }
    public Pitstop() {}

    @Override
    public String toString() {
        return "Pitstop{" +
                "date='" + date + '\'' +
                ", driverNumber=" + driverNumber +
                ", lapNumber=" + lapNumber +
                ", meetingKey=" + meetingKey +
                ", pitDuration=" + pitDuration +
                ", sessionKey=" + sessionKey +
                '}';
    }
}
