package com.f1client.analytics;

import com.f1client.analytics.model.CompoundDegradation;

import com.f1client.model.Driver;
import com.f1client.model.Lap;

import com.f1client.service.DriverService;
import com.f1client.service.LapService;
import com.f1client.service.SessionService;
import com.f1client.service.StintService;
import com.f1client.service.RaceControlEventService;
import com.f1client.service.PitstopService;
import com.f1client.service.PositionService;

import com.f1client.util.SessionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionAnalytics {
    private final DriverService driverService;
    private final LapService lapService;
    private final StintService stintService;
    private final SessionService sessionService;
    private final RaceControlEventService raceControlEventService;
    private final PitstopService pitstopService;

    public SessionAnalytics(DriverService driverService, LapService lapService, StintService stintService, SessionService sessionService, RaceControlEventService raceControlEventService, PitstopService pitstopService) {
        this.driverService = driverService;
        this.lapService = lapService;
        this.stintService = stintService;
        this.sessionService = sessionService;
        this.raceControlEventService = raceControlEventService;
        this.pitstopService = pitstopService;
    }

    /**
     * Computes average lap time per driver for a given session.
     */
    public Map<Integer, Double> averageLapTimePerDriver(int sessionKey) {
        Map<Integer, Double> result = new HashMap<>();
        for (Driver driver : SessionUtils.getDriversForSession(driverService.getAllDrivers(),
                sessionService.getSessionByKey(sessionKey).orElseThrow())) {
            List<Lap> laps = lapService.getLapsByDriverNumber(driver.getDriverNumber())
                    .stream()
                    .filter(l -> l.getSessionKey() == sessionKey)
                    .filter(l -> !l.isPitOutLap())
                    .filter(l -> l.getLapDuration() > 0)
                    .toList();

            double avg = laps.stream().mapToDouble(Lap::getLapDuration).average().orElse(0);
            result.put(driver.getDriverNumber(), avg);
        }
        return result;
    }

    /**
     * Prints stints for a specific driver.
     */
    public void printDriverStints(int driverNumber) {
        stintService.getStintsForDriver(driverNumber).forEach(stint ->
                System.out.println("Stint " + stint.getStintNumber() +
                        ": laps " + stint.getLapStart() + "-" + stint.getLapEnd() +
                        ", compound = " + stint.getCompound() +
                        ", tyre age at start of stint = " + stint.getTyreAgeAtStart()));
    }

    public static void main(String[] args) {
        try {
            DriverService driverService = new DriverService();
            LapService lapService = new LapService(9896); // Singapore GP Race 2025
            StintService stintService = new StintService(9896);
            SessionService sessionService = new SessionService();
            RaceControlEventService raceControlEventService = new RaceControlEventService();
            PitstopService pitstopService = new PitstopService(driverService);
            PositionService positionService = new PositionService(9896);

            SessionAnalytics analytics = new SessionAnalytics(driverService, lapService, stintService, sessionService, raceControlEventService, pitstopService);

            // --- Lap-time analysis ---
            Map<Integer, Double> avgLapTimes = analytics.averageLapTimePerDriver(9896);
            avgLapTimes.forEach((driver, avgTime) ->
                    System.out.println("Average lap time for car " + driver + " -> " + avgTime + " seconds")
            );

        
            analytics.printDriverStints(81);

            CompoundAnalytics compoundAnalytics = new CompoundAnalytics(lapService, stintService,
                    driverService, sessionService);
            compoundAnalytics.analyzeCompoundPerformance(9896);

            PitStrategyAnalysis pitStrategyAnalysis = new PitStrategyAnalysis(driverService,stintService,pitstopService,positionService);
            pitStrategyAnalysis.analyzePitStops(9896);
            pitStrategyAnalysis.printPitStrategy(9896);

            List<CompoundDegradation> deg = compoundAnalytics.analyzeCompoundDegradation(9896);
            deg.forEach(System.out::println);

            RaceControlAnalysis raceControlAnalysis = new RaceControlAnalysis(raceControlEventService);
            raceControlAnalysis.analyzeSession(9896);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
