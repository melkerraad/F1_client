package com.f1client.util;

import com.f1client.model.Driver;
import com.f1client.model.Lap;
import com.f1client.model.Session;
import java.util.List;
import java.util.stream.Collectors;

public class LapUtils {

    public static List<Lap> getLapsForDriver(List<Lap> laps, Driver driver) {
        return laps.stream()
                .filter(l -> l.getDriverNumber() == driver.getDriverNumber())
                .collect(Collectors.toList());
    }

    public static List<Lap> getLapsForSession(List<Lap> laps, Session session) {
        return laps.stream()
                .filter(l -> l.getSessionKey() == session.getSessionKey())
                .collect(Collectors.toList());
    }
}

