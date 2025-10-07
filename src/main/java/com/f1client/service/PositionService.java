package com.f1client.service;
import com.f1client.loader.PositionLoader;
import com.f1client.model.Position;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.time.Duration;

public class PositionService {
    private final PositionLoader positionLoader;
    private final List<Position> positions;
    public PositionService(int sessionKey) {
        this.positionLoader = new PositionLoader();
        try {
            this.positions = positionLoader.loadData(sessionKey);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load position data for session " + sessionKey, e);
        }
    }
    public List<Position> getAllPositions() {
        return positions;
    }
    public List<Position> getPositionsByDriverNumber(int driverNumber) {
        return positions.stream()
                .filter(p -> p.getDriverNumber() == driverNumber)
                .toList();
    }
    public Position getClosestPosition(int driverNumber, Instant targetTime) {
        List<Position> positions = getPositionsByDriverNumber(driverNumber);

        return positions.stream()
            .filter(p -> p.getDate() != null)
            .min(Comparator.comparingLong(p -> {
                try {
                    Instant positionTime = Instant.parse(p.getDate());
                    return Math.abs(Duration.between(positionTime, targetTime).toMillis());
                } catch (DateTimeParseException e) {
                    return Long.MAX_VALUE; // skip invalid timestamps
                }
            }))
            .orElse(null);
    }

}
