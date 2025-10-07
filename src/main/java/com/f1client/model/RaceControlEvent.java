package com.f1client.model;

import com.google.gson.annotations.SerializedName;

public class RaceControlEvent {

    @SerializedName("meeting_key")
    private int meetingKey;
    @SerializedName("session_key")  
    private int sessionKey;
    @SerializedName("date")
    private String date;
    @SerializedName("driver_number")
    private Integer driverNumber; // nullable
    @SerializedName("lap_number")
    private Integer lapNumber;    // sometimes 1 for all messages (not lap-specific)
    @SerializedName("category")
    private String category;      // e.g., "Flag", "Other"
    @SerializedName("flag")
    private String flag;          // e.g., "YELLOW", "DOUBLE YELLOW", "CLEAR"
    @SerializedName("scope")
    private String scope;         // e.g., "Sector"
    @SerializedName("sector")
    private Integer sector;
    @SerializedName("message")
    private String message;

    // Getters and setters
    public int getMeetingKey() { return meetingKey; }
    public void setMeetingKey(int meetingKey) { this.meetingKey = meetingKey; }

    public int getSessionKey() { return sessionKey; }
    public void setSessionKey(int sessionKey) { this.sessionKey = sessionKey; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Integer getDriverNumber() { return driverNumber; }
    public void setDriverNumber(Integer driverNumber) { this.driverNumber = driverNumber; }

    public Integer getLapNumber() { return lapNumber; }
    public void setLapNumber(Integer lapNumber) { this.lapNumber = lapNumber; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }

    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }

    public Integer getSector() { return sector; }
    public void setSector(Integer sector) { this.sector = sector; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", category, message, date);
    }
}

