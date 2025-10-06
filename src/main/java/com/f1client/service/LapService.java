package com.f1client.service;

import java.util.List;
import com.f1client.loader.LapLoader;
import com.f1client.model.Lap;

public class LapService {
    private final List<Lap> laps;

    public LapService(int sessionKey) {
        try {
            this.laps = new LapLoader(sessionKey).loadData();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load lap data for session " + sessionKey, e);
        }
    }


    public List<Lap> getAllLaps() {
        return laps;
    }

    public List<Lap> getLapsByDriverNumber(int driverNumber) {
        return laps.stream()
                .filter(l -> l.getDriverNumber() == driverNumber)
                .toList();
    }
}
