package com.f1client.analytics;

import com.f1client.model.Driver;
import com.f1client.model.Pitstop;
import com.f1client.model.Position;
import com.f1client.model.Stint;
import com.f1client.service.DriverService;
import com.f1client.service.PitstopService;
import com.f1client.service.PositionService;
import com.f1client.service.StintService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PitStrategyAnalysis {

    private final PitstopService pitstopService;
    private final StintService stintService;
    private final DriverService driverService;
    private final PositionService positionService;

    public PitStrategyAnalysis(DriverService driverService, StintService stintService,
                               PitstopService pitstopService, PositionService positionService) {
        this.driverService = driverService;
        this.stintService = stintService;
        this.pitstopService = pitstopService;
        this.positionService = positionService;
    }

    // Core analysis method that returns structured data
    public List<PitAnalysisResult> analyzePitStops(int sessionKey) {
        List<PitAnalysisResult> results = new ArrayList<>();

        List<Driver> sessionDrivers = driverService.getDriversBySession(sessionKey);
        for (Driver driver : sessionDrivers) {
            int driverNumber = driver.getDriverNumber();

            List<Pitstop> pitstops = pitstopService.getPitstopsByDriver(driverNumber, sessionKey);
            List<Stint> stints = stintService.getStintsForDriver(driverNumber);

            if (stints == null || stints.isEmpty()) {
                continue;
            }

            for (Stint stint : stints) {
                Pitstop pitInStint = pitstops.stream()
                        .filter(p -> p.getLapNumber() > 0 &&
                                     p.getLapNumber() >= stint.getLapStart() &&
                                     p.getLapNumber() <= stint.getLapEnd())
                        .findFirst()
                        .orElse(null);

                if (pitInStint != null) {
                    // Estimate timing window
                    Instant pitTime = Instant.parse(pitInStint.getDate());

                    // before = last known position up to 10s before entering pit
                    Instant beforePit = pitTime.minusSeconds(10);

                    // after = first known position at least 15s after pit stop ends
                    Instant afterPit = pitTime.plusSeconds((long) pitInStint.getPitDuration() + 15);

                    Position before = positionService.getClosestPosition(driverNumber, beforePit);
                    Position after = positionService.getClosestPosition(driverNumber, afterPit);

                    Integer posBefore = before != null ? before.getPosition() : null;
                    Integer posAfter = after != null ? after.getPosition() : null;
                    Integer delta = (posBefore != null && posAfter != null) ? (posAfter - posBefore) : null;


                    results.add(new PitAnalysisResult(
                            driverNumber,
                            stint.getStintNumber(),
                            stint.getCompound(),
                            stint.getLapStart(),
                            stint.getLapEnd(),
                            pitInStint.getLapNumber(),
                            pitInStint.getPitDuration(),
                            posBefore,
                            posAfter,
                            delta
                    ));
                }
            }
        }

        return results;
    }

    public void printPitStrategy(int sessionKey) {
        List<PitAnalysisResult> results = analyzePitStops(sessionKey);

        results.stream()
                .sorted(Comparator.comparingInt(PitAnalysisResult::getDriverNumber)
                        .thenComparingInt(PitAnalysisResult::getStintNumber))
                .forEach(r -> {
                    System.out.printf(
                            "Car %2d | Stint %d | %-8s | Laps %3d-%-3d | Pit Lap %-3d | Duration %6.2fs | Pos Before %-2s | Pos After %-2s | Delta: %-2s%n",
                            r.getDriverNumber(),
                            r.getStintNumber(),
                            r.getCompound(),
                            r.getLapStart(),
                            r.getLapEnd(),
                            r.getPitLap(),
                            r.getPitDuration(),
                            r.getPositionBefore() != null ? r.getPositionBefore() : "-",
                            r.getPositionAfter() != null ? r.getPositionAfter() : "-",
                            r.getPositionDelta() != null ? (r.getPositionDelta() > 0 ? "+" + r.getPositionDelta() : r.getPositionDelta()) : "-"
                    );
                });
    }

    // Simple data container for results
    public static class PitAnalysisResult {
        private final int driverNumber;
        private final int stintNumber;
        private final String compound;
        private final int lapStart;
        private final int lapEnd;
        private final int pitLap;
        private final double pitDuration;
        private final Integer positionBefore;
        private final Integer positionAfter;
        private final Integer positionDelta;

        public PitAnalysisResult(int driverNumber, int stintNumber, String compound,
                                 int lapStart, int lapEnd, int pitLap, double pitDuration,
                                 Integer positionBefore, Integer positionAfter, Integer positionDelta) {
            this.driverNumber = driverNumber;
            this.stintNumber = stintNumber;
            this.compound = compound;
            this.lapStart = lapStart;
            this.lapEnd = lapEnd;
            this.pitLap = pitLap;
            this.pitDuration = pitDuration;
            this.positionBefore = positionBefore;
            this.positionAfter = positionAfter;
            this.positionDelta = positionDelta;
        }

        public int getDriverNumber() { return driverNumber; }
        public int getStintNumber() { return stintNumber; }
        public String getCompound() { return compound; }
        public int getLapStart() { return lapStart; }
        public int getLapEnd() { return lapEnd; }
        public int getPitLap() { return pitLap; }
        public double getPitDuration() { return pitDuration; }
        public Integer getPositionBefore() { return positionBefore; }
        public Integer getPositionAfter() { return positionAfter; }
        public Integer getPositionDelta() { return positionDelta; }
    }
}
