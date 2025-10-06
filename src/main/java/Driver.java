package src.main.java;
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
    public String getBroadcastName() { return broadcastName; }
    public void setBroadcastName(String broadcastName) { this.broadcastName = broadcastName; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public int getDriverNumber() { return driverNumber; }
    public void setDriverNumber(int driverNumber) { this.driverNumber = driverNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getHeadshotUrl() { return headshotUrl; }
    public void setHeadshotUrl(String headshotUrl) { this.headshotUrl = headshotUrl; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getMeetingKey() { return meetingKey; }
    public void setMeetingKey(int meetingKey) { this.meetingKey = meetingKey; }

    public String getNameAcronym() { return nameAcronym; }
    public void setNameAcronym(String nameAcronym) { this.nameAcronym = nameAcronym; }

    public int getSessionKey() { return sessionKey; }
    public void setSessionKey(int sessionKey) { this.sessionKey = sessionKey; }

    public String getTeamColour() { return teamColour; }
    public void setTeamColour(String teamColour) { this.teamColour = teamColour; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

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