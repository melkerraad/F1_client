package com.f1client.util;

import com.f1client.model.Driver;
import com.f1client.model.Session;

import java.util.List;
import java.util.stream.Collectors;

public class SessionUtils {
    public static List<Driver> getDriversForSession(List<Driver> drivers, Session session) {
        int meetingKey = session.getMeetingKey();
        int sessionKey = session.getSessionKey();

        return drivers.stream()
                .filter(d -> d.getMeetingKey() == meetingKey && d.getSessionKey() == sessionKey)
                .collect(Collectors.toMap(
                        Driver::getFullName, // key = full name
                        d -> d,               // value = driver object
                        (existing, replacement) -> existing // in case of duplicates, keep first
                ))
                .values()
                .stream()
                .collect(Collectors.toList());
    }
}
