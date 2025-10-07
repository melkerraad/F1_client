package com.f1client.analytics;

import com.f1client.model.RaceControlEvent;
import com.f1client.service.RaceControlEventService;
import java.util.List;

public class RaceControlAnalysis {

    private final RaceControlEventService raceControlEventService;

    public RaceControlAnalysis(RaceControlEventService raceControlEventService) {
        this.raceControlEventService = raceControlEventService;
    }

    public void analyzeSession(int sessionKey) {
        List<RaceControlEvent> events = raceControlEventService.getEventsBySession(sessionKey);

        System.out.println("Race control events for session " + sessionKey + ":");
        for (RaceControlEvent e : events) {
            System.out.printf("[%s] %s (Sector %s)%n", e.getFlag(), e.getMessage(),
                    e.getSector() != null ? e.getSector() : "-");
        }
    }
}
