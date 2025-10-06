package com.f1client.model;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class Lap {

    @SerializedName("date_start")
    private String dateStart;

    @SerializedName("driver_number")
    private int driverNumber;

    @SerializedName("duration_sector_1")
    private float durationSector1;

    @SerializedName("duration_sector_2")
    private float durationSector2;

    @SerializedName("duration_sector_3")
    private float durationSector3;

    @SerializedName("i1_speed")
    private int i1Speed;

    @SerializedName("i2_speed")
    private int i2Speed;

    @SerializedName("is_pit_out_lap")
    private boolean isPitOutLap;

    @SerializedName("lap_duration")
    private float lapDuration;

    @SerializedName("lap_number")
    private int lapNumber;

    @SerializedName("meeting_key")
    private int meetingKey;

    // Use Integer[] instead of int[] to fix Gson deserialization issue
    @SerializedName("segments_sector_1")
    private Integer[] rawSegmentsSector1;

    @SerializedName("segments_sector_2")
    private Integer[] rawSegmentsSector2;

    @SerializedName("segments_sector_3")
    private Integer[] rawSegmentsSector3;

    @SerializedName("session_key")
    private int sessionKey;

    @SerializedName("st_speed")
    private int stSpeed;

    // Mapped segment names
    private String[] segmentsSector1;
    private String[] segmentsSector2;
    private String[] segmentsSector3;

    // Segment mapping
    private static final Map<Integer, String> SEGMENT_MAP = new HashMap<>();
    static {
        SEGMENT_MAP.put(0, "not available");
        SEGMENT_MAP.put(2048, "yellow sector");
        SEGMENT_MAP.put(2049, "green sector");
        SEGMENT_MAP.put(2050, "?");
        SEGMENT_MAP.put(2051, "purple sector");
        SEGMENT_MAP.put(2052, "?");
        SEGMENT_MAP.put(2064, "pitlane");
        SEGMENT_MAP.put(2068, "?");
    }

    public static String mapSegment(Integer code) {
        return SEGMENT_MAP.getOrDefault(code, "unknown");
    }

    public static String[] mapSegments(Integer[] codes) {
        if (codes == null) return null;
        String[] result = new String[codes.length];
        for (int i = 0; i < codes.length; i++) {
            result[i] = mapSegment(codes[i]);
        }
        return result;
    }

    // Call after Gson deserialization to populate mapped segments
    public void postDeserialize() {
        this.segmentsSector1 = mapSegments(rawSegmentsSector1);
        this.segmentsSector2 = mapSegments(rawSegmentsSector2);
        this.segmentsSector3 = mapSegments(rawSegmentsSector3);
    }

    // Getters and setters
    public String getDateStart() { return dateStart; }
    public void setDateStart(String dateStart) { this.dateStart = dateStart; }

    public int getDriverNumber() { return driverNumber; }
    public void setDriverNumber(int driverNumber) { this.driverNumber = driverNumber; }

    public float getDurationSector1() { return durationSector1; }
    public void setDurationSector1(float durationSector1) { this.durationSector1 = durationSector1; }

    public float getDurationSector2() { return durationSector2; }
    public void setDurationSector2(float durationSector2) { this.durationSector2 = durationSector2; }

    public float getDurationSector3() { return durationSector3; }
    public void setDurationSector3(float durationSector3) { this.durationSector3 = durationSector3; }

    public int getI1Speed() { return i1Speed; }
    public void setI1Speed(int i1Speed) { this.i1Speed = i1Speed; }

    public int getI2Speed() { return i2Speed; }
    public void setI2Speed(int i2Speed) { this.i2Speed = i2Speed; }

    public boolean isPitOutLap() { return isPitOutLap; }
    public void setPitOutLap(boolean pitOutLap) { isPitOutLap = pitOutLap; }

    public float getLapDuration() { return lapDuration; }
    public void setLapDuration(float lapDuration) { this.lapDuration = lapDuration; }

    public int getLapNumber() { return lapNumber; }
    public void setLapNumber(int lapNumber) { this.lapNumber = lapNumber; }

    public int getMeetingKey() { return meetingKey; }
    public void setMeetingKey(int meetingKey) { this.meetingKey = meetingKey; }

    public String[] getSegmentsSector1() { return segmentsSector1; }
    public String[] getSegmentsSector2() { return segmentsSector2; }
    public String[] getSegmentsSector3() { return segmentsSector3; }

    public int getSessionKey() { return sessionKey; }
    public void setSessionKey(int sessionKey) { this.sessionKey = sessionKey; }

    public int getStSpeed() { return stSpeed; }
    public void setStSpeed(int stSpeed) { this.stSpeed = stSpeed; }

    @Override
    public String toString() {
        return "Lap{" +
                "dateStart='" + dateStart + '\'' +
                ", driverNumber=" + driverNumber +
                ", durationSector1=" + durationSector1 +
                ", durationSector2=" + durationSector2 +
                ", durationSector3=" + durationSector3 +
                ", i1Speed=" + i1Speed +
                ", i2Speed=" + i2Speed +
                ", isPitOutLap=" + isPitOutLap +
                ", lapDuration=" + lapDuration +
                ", lapNumber=" + lapNumber +
                ", meetingKey=" + meetingKey +
                ", segmentsSector1=" + Arrays.toString(segmentsSector1) +
                ", segmentsSector2=" + Arrays.toString(segmentsSector2) +
                ", segmentsSector3=" + Arrays.toString(segmentsSector3) +
                ", sessionKey=" + sessionKey +
                ", stSpeed=" + stSpeed +
                '}';
    }
}
