package com.f1client.analytics;

import com.f1client.model.Lap;
import com.f1client.model.Stint;
import com.f1client.service.LapService;
import com.f1client.service.StintService;

import java.util.*;
import java.util.stream.Collectors;

public class CompoundAnalytics {

    private final LapService lapService;
    private final StintService stintService;

    public CompoundAnalytics(LapService lapService, StintService stintService) {
        this.lapService = lapService;
        this.stintService = stintService;
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
}
