package com.f1client.model;

import com.google.gson.annotations.SerializedName;

public class Driver {

    @SerializedName("broadcast_name")
    private String broadcastName;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("driver_number")
    private int driverNumber;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("headshot_url")
    private String headshotUrl;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("meeting_key")
    private int meetingKey;

    @SerializedName("name_acronym")
    private String nameAcronym;

    @SerializedName("session_key")
    private int sessionKey;

    @SerializedName("team_colour")
    private String teamColour;

    @SerializedName("team_name")
    private String teamName;

    // Default constructor
    public Driver() {}

    // Getters
    public String getBroadcastName() { return broadcastName; }
    public String getCountryCode() { return countryCode; }
    public int getDriverNumber() { return driverNumber; }
    public String getFirstName() { return firstName; }
    public String getFullName() { return fullName; }
    public String getHeadshotUrl() { return headshotUrl; }
    public String getLastName() { return lastName; }
    public int getMeetingKey() { return meetingKey; }
    public String getNameAcronym() { return nameAcronym; }
    public int getSessionKey() { return sessionKey; }
    public String getTeamColour() { return teamColour; }
    public String getTeamName() { return teamName; }

    @Override
    public String toString() {
        return "Driver{" +
                "broadcastName='" + broadcastName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", driverNumber=" + driverNumber +
                ", firstName='" + firstName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", headshotUrl='" + headshotUrl + '\'' +
                ", lastName='" + lastName + '\'' +
                ", meetingKey=" + meetingKey +
                ", nameAcronym='" + nameAcronym + '\'' +
                ", sessionKey=" + sessionKey +
                ", teamColour='" + teamColour + '\'' +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
