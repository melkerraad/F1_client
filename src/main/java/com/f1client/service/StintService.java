package com.f1client.service;

import com.f1client.loader.StintLoader;
import com.f1client.model.Stint;
import java.util.List;
import java.util.stream.Collectors;

public class StintService {
    private final List<Stint> stints;

    public StintService(int sessionKey) throws Exception {
        this.stints = StintLoader.loadStintsForSession(sessionKey);
    }

    public List<Stint> getAllStints() {
        return stints;
    }

    public List<Stint> getStintsForDriver(int driverNumber) {
        return stints.stream()
                .filter(s -> s.getDriverNumber() == driverNumber)
                .collect(Collectors.toList());
    }

    public List<Stint> getStintsForSession(int sessionKey) {
        return stints.stream()
                .filter(s -> s.getSessionKey() == sessionKey)
                .collect(Collectors.toList());
    }
}
