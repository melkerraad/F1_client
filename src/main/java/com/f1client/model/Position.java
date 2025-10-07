package com.f1client.model;
import com.google.gson.annotations.SerializedName;

public class Position {
    @SerializedName("date")
    private String date;
    @SerializedName("driver_number")
    private int driverNumber;
    @SerializedName("meeting_key")
    private int meetingKey;
    @SerializedName("position")
    private int position;
    @SerializedName("session_key")
    private int sessionKey;

    public String getDate() {
        return date;
    }

    public int getDriverNumber() {
        return driverNumber;
    }

    public int getMeetingKey() {
        return meetingKey;
    }
    public int getPosition() {
        return position;
    }
    public int getSessionKey() {
        return sessionKey;
    }
    @Override
    public String toString() {
        return "Position{" +
                "date='" + date + '\'' +
                ", driverNumber=" + driverNumber +
                ", meetingKey=" + meetingKey +
                ", position=" + position +
                ", sessionKey=" + sessionKey +
                '}';
    }
}

/*
 * Position
Provides driver positions throughout a session, including initial placement and subsequent changes.

curl "https://api.openf1.org/v1/position?meeting_key=1217&driver_number=40&position<=3"
Output:

[
  {
    "date": "2023-08-26T09:30:47.199000+00:00",
    "driver_number": 40,
    "meeting_key": 1217,
    "position": 2,
    "session_key": 9144
  },
  {
    "date": "2023-08-26T09:35:51.477000+00:00",
    "driver_number": 40,
    "meeting_key": 1217,
    "position": 3,
    "session_key": 9144
  }
]
HTTP Request
GET https://api.openf1.org/v1/position

Sample URL
https://api.openf1.org/v1/position?meeting_key=1217&driver_number=40&position<=3

Attributes
Name	Description
date	The UTC date and time, in ISO 8601 format.
driver_number	The unique number assigned to an F1 driver (cf. Wikipedia).
meeting_key	The unique identifier for the meeting. Use latest to identify the latest or current meeting.
position	Position of the driver (starts at 1).
session_key	The unique identifier for the session. Use latest to identify the latest or current session.
 */