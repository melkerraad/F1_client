package com.f1client.model;

import com.google.gson.annotations.SerializedName;

public class Session {

    @SerializedName("circuit_key")
    private int circuitKey;

    @SerializedName("circuit_short_name")
    private String circuitShortName;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("country_key")
    private int countryKey;

    @SerializedName("country_name")
    private String countryName;

    @SerializedName("date_end")
    private String dateEnd;

    @SerializedName("date_start")
    private String dateStart;

    @SerializedName("gmt_offset")
    private String gmtOffset;

    @SerializedName("location")
    private String location;

    @SerializedName("meeting_key")
    private int meetingKey;

    @SerializedName("session_key")
    private int sessionKey;

    @SerializedName("session_name")
    private String sessionName;

    @SerializedName("session_type")
    private String sessionType;

    @SerializedName("year")
    private int year;

    // No-args constructor for Gson
    public Session() {}

    // Getters and setters
    public int getCircuitKey() { return circuitKey; }
    public void setCircuitKey(int circuitKey) { this.circuitKey = circuitKey; }

    public String getCircuitShortName() { return circuitShortName; }
    public void setCircuitShortName(String circuitShortName) { this.circuitShortName = circuitShortName; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public int getCountryKey() { return countryKey; }
    public void setCountryKey(int countryKey) { this.countryKey = countryKey; }

    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }

    public String getDateEnd() { return dateEnd; }
    public void setDateEnd(String dateEnd) { this.dateEnd = dateEnd; }

    public String getDateStart() { return dateStart; }
    public void setDateStart(String dateStart) { this.dateStart = dateStart; }

    public String getGmtOffset() { return gmtOffset; }
    public void setGmtOffset(String gmtOffset) { this.gmtOffset = gmtOffset; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getMeetingKey() { return meetingKey; }
    public void setMeetingKey(int meetingKey) { this.meetingKey = meetingKey; }

    public int getSessionKey() { return sessionKey; }
    public void setSessionKey(int sessionKey) { this.sessionKey = sessionKey; }

    public String getSessionName() { return sessionName; }
    public void setSessionName(String sessionName) { this.sessionName = sessionName; }

    public String getSessionType() { return sessionType; }
    public void setSessionType(String sessionType) { this.sessionType = sessionType; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @Override
    public String toString() {
        return "Session{" +
                "circuitKey=" + circuitKey +
                ", circuitShortName='" + circuitShortName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryKey=" + countryKey +
                ", countryName='" + countryName + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", gmtOffset='" + gmtOffset + '\'' +
                ", location='" + location + '\'' +
                ", meetingKey=" + meetingKey +
                ", sessionKey=" + sessionKey +
                ", sessionName='" + sessionName + '\'' +
                ", sessionType='" + sessionType + '\'' +
                ", year=" + year +
                '}';
    }
}
