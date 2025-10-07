package com.f1client.analytics.model;

public class CompoundDegradation {
    private final int driverNumber;
    private final String compound;
    private final int stintNumber;
    private final double initialLapTime;
    private final double finalLapTime;
    private final double avgDegradationPerLap;
    private final int totalLaps;

    public CompoundDegradation(int driverNumber, String compound, int stintNumber,
                               double initialLapTime, double finalLapTime,
                               double avgDegradationPerLap, int totalLaps) {
        this.driverNumber = driverNumber;
        this.compound = compound;
        this.stintNumber = stintNumber;
        this.initialLapTime = initialLapTime;
        this.finalLapTime = finalLapTime;
        this.avgDegradationPerLap = avgDegradationPerLap;
        this.totalLaps = totalLaps;
    }

    public int getDriverNumber() { return driverNumber; }
    public String getCompound() { return compound; }
    public int getStintNumber() { return stintNumber; }
    public double getInitialLapTime() { return initialLapTime; }
    public double getFinalLapTime() { return finalLapTime; }
    public double getAvgDegradationPerLap() { return avgDegradationPerLap; }
    public int getTotalLaps() { return totalLaps; }

    @Override
    public String toString() {
        return String.format(
            "Car %d | Stint %d | %s | %.3fs → %.3fs | Drop %.3fs/lap over %d laps",
            driverNumber, stintNumber, compound,
            initialLapTime, finalLapTime, avgDegradationPerLap, totalLaps
        );
    }
}

