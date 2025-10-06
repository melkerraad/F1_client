package com.f1client.analytics;

import com.f1client.model.Driver;
import com.f1client.model.Lap;
import com.f1client.model.Pitstop;
import com.f1client.service.DriverService;
import com.f1client.service.PitstopService;
import com.f1client.service.SessionService;
import com.f1client.service.LapService;
import com.f1client.util.SessionUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionAnalytics {
    private final DriverService driverService;
    private final PitstopService pitstopService;
    private final SessionService sessionService;
    private final LapService lapService;

    public SessionAnalytics(DriverService driverService, PitstopService pitstopService, SessionService sessionService, LapService lapService) {
        this.driverService = driverService;
        this.pitstopService = pitstopService;
        this.sessionService = sessionService;
        this.lapService = lapService;
    }
    

    public void SimpleAnalytics() {
        // Print all sessions
        sessionService.getAllSessions().forEach(System.out::println);

        // Print all drivers
        driverService.getAllDrivers().forEach(System.out::println);

        // Print all pitstops
        pitstopService.getAllPitstops().forEach(System.out::println);

        // Print all laps
        lapService.getAllLaps().forEach(System.out::println);
    }

    public Map<Integer, Double> averageLapTimePerDriver(int sessionKey) {
        Map<Integer, Double> result = new HashMap<>();
        for (Driver driver : SessionUtils.getDriversForSession(driverService.getAllDrivers(), sessionService.getSessionByKey(sessionKey).orElseThrow())) {
           List<Lap> laps = lapService.getLapsByDriverNumber(driver.getDriverNumber())
                           .stream()
                           .filter(l -> l.getSessionKey() == sessionKey)
                           .filter(l -> !l.isPitOutLap())  // removes pit-out laps
                           .filter(l -> l.getLapDuration() > 0) // removes zero-duration laps
                           .toList();

            double avg = laps.stream().mapToDouble(Lap::getLapDuration).average().orElse(0);
            result.put(driver.getDriverNumber(), avg);
        }
        return result;
    }

    public Map<Integer, Double> averagePitTimePerDriver(int sessionKey) {
        Map<Integer, Double> result = new HashMap<>();
        for (Driver driver : SessionUtils.getDriversForSession(driverService.getAllDrivers(), sessionService.getSessionByKey(sessionKey).orElseThrow())) {
            List<Pitstop> stops = pitstopService.getPitstopsByDriver(driver.getDriverNumber(), sessionKey)
                    .stream()
                    .filter(p -> p.getPitDuration() > 0)
                    .toList();

            double avg = stops.stream()
                    .mapToDouble(Pitstop::getPitDuration)
                    .average()
                    .orElse(0.0);
            result.put(driver.getDriverNumber(), avg);
        }
        return result;
    }

    public static void main(String[] args) {
    try {
        DriverService driverService = new DriverService();
        PitstopService pitstopService = new PitstopService(driverService);
        SessionService sessionService = new SessionService();

        int sessionKey = 9896; //Singapore GP Race 2025
        LapService lapService = new LapService(sessionKey);

        SessionAnalytics analytics = new SessionAnalytics(driverService, pitstopService, sessionService, lapService);
        //analytics.SimpleAnalytics();
        Map<Integer, Double> avgLapTimes = analytics.averageLapTimePerDriver(sessionKey);
            avgLapTimes.forEach((driver, avgTime) -> 
                System.out.println("Average lap time for car " + driver + " -> " + avgTime + " seconds")
            );
        Map<Integer, Double> avgPitTimes = analytics.averagePitTimePerDriver(sessionKey);
            avgPitTimes.forEach((driver, avgTime) -> 
                System.out.println("Average pit time for car " + driver + " -> " + avgTime + " seconds")
            );
    } catch (Exception e) {
        e.printStackTrace();
    }

}
}

