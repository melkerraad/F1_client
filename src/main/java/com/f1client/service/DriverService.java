package com.f1client.service;

import com.f1client.loader.DriverLoader;
import com.f1client.model.Driver;
import java.util.List;
import java.util.stream.Collectors;

public class DriverService {
    private final List<Driver> drivers;

    public DriverService() {
        try {
            this.drivers = new DriverLoader().loadData();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load driver data", e);
        }
    }

    public List<Driver> getAllDrivers() {
        return drivers;
    }

    public List<Driver> getUniqueDrivers() {
        return drivers.stream()
                .collect(Collectors.toMap(
                        Driver::getFullName,
                        d -> d,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .collect(Collectors.toList());
    }

    public List<Driver> getDriversByTeam(String teamName) {
        return drivers.stream()
                .filter(d -> d.getTeamName().equalsIgnoreCase(teamName))
                .collect(Collectors.toList());
    }

    public List<Driver> getDriversByCountry(String countryCode) {
        return drivers.stream()
                .filter(d -> d.getCountryCode().equalsIgnoreCase(countryCode))
                .collect(Collectors.toList());
    }

    public List<String> getTeamsPerDriver(String driverFullName) {
        return drivers.stream()
                .filter(d -> d.getFullName().equalsIgnoreCase(driverFullName))
                .map(Driver::getTeamName)
                .distinct()
                .collect(Collectors.toList());
    }

    public java.util.Optional<Driver> getDriverByName(String driverName) {
        return drivers.stream()
                .filter(d -> d.getFullName().equalsIgnoreCase(driverName))
                .findFirst();
    }

    public java.util.Optional<Driver> getDriverByNumber(int driverNumber) {
        return drivers.stream()
                .filter(d -> d.getDriverNumber() == driverNumber)
                .findFirst();
    }
    public List<Driver> getDriversBySession(int sessionKey) {
        return drivers.stream()
                .filter(d -> d.getSessionKey() == sessionKey)
                .collect(Collectors.toList());
    }
}
