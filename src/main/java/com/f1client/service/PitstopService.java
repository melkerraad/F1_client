package com.f1client.service;

import com.f1client.loader.PitstopLoader;
import com.f1client.model.Driver;
import com.f1client.model.Pitstop;
import java.util.List;
import java.util.stream.Collectors;

public class PitstopService {
    private final List<Pitstop> pitstops;
    private final DriverService driverService;

    public PitstopService(DriverService driverService) {
        try {
            this.driverService = driverService;
            this.pitstops = new PitstopLoader().loadData();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load pitstop data", e);
        }
    }
    public List<Pitstop> getAllPitstops() {
        return pitstops;
    }

    public List<Pitstop> getPitstopsByDriver(int driverNumber, int sessionId) {
        return driverService.getDriverByNumber(driverNumber)
                .map(driver -> pitstops.stream()
                        .filter(p -> p.getDriverNumber() == driver.getDriverNumber() && p.getSessionKey() == sessionId)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public List<Pitstop> getPitstopsForTeam(String teamName) {
        List<Driver> teamDrivers = driverService.getDriversByTeam(teamName);
        return pitstops.stream()
                .filter(p -> teamDrivers.stream()
                        .anyMatch(d -> d.getDriverNumber() == p.getDriverNumber()))
                .collect(Collectors.toList());
    }

    public List<Pitstop> getPitstopsForSession(int sessionKey) {
        return pitstops.stream()
                .filter(p -> p.getSessionKey() == sessionKey)
                .collect(Collectors.toList());
    }
}
