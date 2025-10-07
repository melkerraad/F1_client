package com.f1client.service;

import java.util.List;
import java.util.stream.Collectors;

import com.f1client.model.RaceControlEvent;
import com.f1client.loader.RaceControlEventLoader;

public class RaceControlEventService {
    private final java.util.List<com.f1client.model.RaceControlEvent> raceControlEvents;


    public RaceControlEventService() {
        try {
            this.raceControlEvents = new RaceControlEventLoader().loadData();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load race control events", e);
        }
    }
    public java.util.List<com.f1client.model.RaceControlEvent> getAllRaceControlEvents() { //might not work TODO check
        return raceControlEvents;
    }
    public List<RaceControlEvent> getEventsBySession(int sessionKey) {
        return raceControlEvents.stream()
                .filter(event -> event.getSessionKey() == sessionKey)
                .collect(Collectors.toList());
    }

    public void addRaceControlEvent(com.f1client.model.RaceControlEvent event) {
        raceControlEvents.add(event);
    }   
    public List<RaceControlEvent> getFlagEvents(int sessionKey) {
        return getEventsBySession(sessionKey).stream()
                .filter(e -> "Flag".equalsIgnoreCase(e.getCategory()))
                .collect(Collectors.toList());
    }

    public List<RaceControlEvent> getSafetyCarEvents(int sessionKey) {
        return getEventsBySession(sessionKey).stream()
                .filter(e -> e.getMessage() != null && e.getCategory().contains("SafetyCar"))
                .collect(Collectors.toList());
    }
}
