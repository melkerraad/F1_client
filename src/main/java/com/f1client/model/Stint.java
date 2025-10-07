package com.f1client.model;

import com.google.gson.annotations.SerializedName;

public class Stint {
    @SerializedName("meeting_key")
    private int meetingKey;

    @SerializedName("session_key")
    private int sessionKey;

    @SerializedName("stint_number")
    private int stintNumber;

    @SerializedName("driver_number")
    private int driverNumber;

    @SerializedName("lap_start")
    private Integer lapStart; // nullable

    @SerializedName("lap_end")
    private Integer lapEnd; // nullable

    @SerializedName("compound")
    private String compound;

    @SerializedName("tyre_age_at_start")
    private int tyreAgeAtStart;


    public Stint() {}
    
    public int getMeetingKey() { return meetingKey; }
    public int getSessionKey() { return sessionKey; }
    public int getStintNumber() { return stintNumber; }
    public int getDriverNumber() { return driverNumber; }
    public Integer getLapStart() { return lapStart; }
    public Integer getLapEnd() { return lapEnd; }
    public String getCompound() { return compound; }
    public int getTyreAgeAtStart() { return tyreAgeAtStart; }

    @Override
    public String toString() {
        return "Stint{" +
                "meetingKey=" + meetingKey +
                ", sessionKey=" + sessionKey +
                ", stintNumber=" + stintNumber +
                ", driverNumber=" + driverNumber +
                ", lapStart=" + lapStart +
                ", lapEnd=" + lapEnd +
                ", compound='" + compound + '\'' +
                ", tyreAgeAtStart=" + tyreAgeAtStart +
                '}';
    }
}
