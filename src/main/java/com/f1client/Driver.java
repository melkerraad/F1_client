package com.f1client;


public class Driver {

    // The driver's name, as displayed on TV.
    private String broadcastName;

    // A code that uniquely identifies the country.
    private String countryCode;

    // The unique number assigned to an F1 driver.
    private int driverNumber;

    // The driver's first name.
    private String firstName;

    // The driver's full name.
    private String fullName;

    // URL of the driver's face photo.
    private String headshotUrl;

    // The driver's last name.
    private String lastName;

    // The unique identifier for the meeting.
    private int meetingKey;

    // Three-letter acronym of the driver's name.
    private String nameAcronym;

    // The unique identifier for the session.
    private int sessionKey;

    // The hexadecimal color value (RRGGBB) of the driver's team.
    private String teamColour;

    // Name of the driver's team.
    private String teamName;

    // Default constructor
    public Driver() {}

    // Parameterized constructor (optional)
    public Driver(String broadcastName, String countryCode, int driverNumber, String firstName, String fullName,
                  String headshotUrl, String lastName, int meetingKey, String nameAcronym, int sessionKey,
                  String teamColour, String teamName) {
        this.broadcastName = broadcastName;
        this.countryCode = countryCode;
        this.driverNumber = driverNumber;
        this.firstName = firstName;
        this.fullName = fullName;
        this.headshotUrl = headshotUrl;
        this.lastName = lastName;
        this.meetingKey = meetingKey;
        this.nameAcronym = nameAcronym;
        this.sessionKey = sessionKey;
        this.teamColour = teamColour;
        this.teamName = teamName;
    }

    // Getters and Setters
    public String getBroadcast_name() { return broadcastName; }
    public void setBroadcast_name(String broadcastName) { this.broadcastName = broadcastName; }

    public String getCountry_code() { return countryCode; }
    public void setCountry_code(String countryCode) { this.countryCode = countryCode; }

    public int getDriver_number() { return driverNumber; }
    public void setDriver_number(int driverNumber) { this.driverNumber = driverNumber; }

    public String getFirst_name() { return firstName; }
    public void setFirst_name(String firstName) { this.firstName = firstName; }

    public String getFull_name() { return fullName; }
    public void setFull_name(String fullName) { this.fullName = fullName; }

    public String getHeadshot_url() { return headshotUrl; }
    public void setHeadshot_url(String headshotUrl) { this.headshotUrl = headshotUrl; }

    public String getLast_name() { return lastName; }
    public void setLast_name(String lastName) { this.lastName = lastName; }

    public int getMeeting_key() { return meetingKey; }
    public void setMeeting_key(int meetingKey) { this.meetingKey = meetingKey; }

    public String getName_acronym() { return nameAcronym; }
    public void setName_acronym(String nameAcronym) { this.nameAcronym = nameAcronym; }

    public int getSession_key() { return sessionKey; }
    public void setSession_key(int sessionKey) { this.sessionKey = sessionKey; }

    public String getTeam_colour() { return teamColour; }
    public void setTeam_colour(String teamColour) { this.teamColour = teamColour; }

    public String getTeam_name() { return teamName; }
    public void setTeam_name(String teamName) { this.teamName = teamName; }

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