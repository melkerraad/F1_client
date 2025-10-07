package com.f1client.analytics;

import com.f1client.model.Lap;
import com.f1client.model.Stint;
import com.f1client.model.Driver;
import com.f1client.service.DriverService;  
import com.f1client.service.SessionService;
import com.f1client.service.LapService;
import com.f1client.service.StintService;
import com.f1client.util.SessionUtils;
import com.f1client.analytics.model.CompoundDegradation;

import java.util.*;
import java.util.stream.Collectors;

public class CompoundAnalytics {

    private final LapService lapService;
    private final StintService stintService;
    private final DriverService driverService;
    private final SessionService sessionService;

    public CompoundAnalytics(LapService lapService, StintService stintService,
                             DriverService driverService, SessionService sessionService) {
        this.lapService = lapService;
        this.stintService = stintService;
        this.driverService = driverService;
        this.sessionService = sessionService;
    }

    public void analyzeCompoundPerformance(int sessionKey) {
        List<Stint> stints = stintService.getStintsForSession(sessionKey);
        Map<String, List<Stint>> stintsByCompound = stints.stream()
                .collect(Collectors.groupingBy(Stint::getCompound));

        for (String compound : stintsByCompound.keySet()) {
            List<Stint> compoundStints = stintsByCompound.get(compound);
            List<Lap> compoundLaps = new ArrayList<>();

            for (Stint stint : compoundStints) {
                // Filter laps for driver and stint
                List<Lap> laps = lapService.getLapsByDriverNumber(stint.getDriverNumber()).stream()
                        .filter(l -> l.getLapNumber() >= stint.getLapStart() && l.getLapNumber() <= stint.getLapEnd())
                        .collect(Collectors.toList());
                compoundLaps.addAll(laps);
            }

            // Compute average lap time
            double avgLapTime = compoundLaps.stream()
                    .mapToDouble(Lap::getLapDuration)
                    .average()
                    .orElse(0.0);

            System.out.println("Compound: " + compound +
                    " | Total laps: " + compoundLaps.size() +
                    " | Average lap time: " + avgLapTime + " seconds");
        }
    }

    public List<CompoundDegradation> analyzeCompoundDegradation(int sessionKey) {
    List<CompoundDegradation> results = new ArrayList<>();

    for (Driver driver : SessionUtils.getDriversForSession(driverService.getAllDrivers(),
            sessionService.getSessionByKey(sessionKey).orElseThrow())) {

        List<Stint> stints = stintService.getStintsForDriver(driver.getDriverNumber());
        List<Lap> laps = lapService.getLapsByDriverNumber(driver.getDriverNumber())
            .stream()
            .filter(l -> l.getSessionKey() == sessionKey && !l.isPitOutLap() && l.getLapDuration() > 0)
            .toList();

        for (Stint stint : stints) {
            List<Lap> stintLaps = laps.stream()
                .filter(l -> l.getLapNumber() >= stint.getLapStart() && l.getLapNumber() <= stint.getLapEnd())
                .toList();

            if (stintLaps.size() < 3) continue; // too short to analyze

            double initial = stintLaps.get(0).getLapDuration();
            double finalLap = stintLaps.get(stintLaps.size() - 1).getLapDuration();

            double totalDrop = finalLap - initial;
            double avgDropPerLap = totalDrop / (stintLaps.size() - 1);

            results.add(new CompoundDegradation(
                driver.getDriverNumber(),
                stint.getCompound(),
                stint.getStintNumber(),
                initial,
                finalLap,
                avgDropPerLap,
                stintLaps.size()
            ));
        }
    }

    return results;
}
}
