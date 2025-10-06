package com.f1client.service;

import com.f1client.loader.SessionLoader;
import com.f1client.model.Session;
import java.util.List;
import java.util.stream.Collectors;

public class SessionService {
    private final List<Session> sessions;
    public SessionService() {
        try {
            this.sessions = new SessionLoader().loadData();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load session data", e);
        }
    }
    public List<Session> getAllSessions() {
        return sessions;
    }
    public List<Session> getSessionsByMeeting(int meetingKey) {
        return sessions.stream()
                .filter(s -> s.getMeetingKey() == meetingKey)
                .collect(Collectors.toList());
    }
    public java.util.Optional<Session> getSessionByKey(int sessionKey) {
        return sessions.stream()
                .filter(s -> s.getSessionKey() == sessionKey)
                .findFirst();
    }
    public List<Integer> getMeetingsBySession(int sessionKey) {
        return sessions.stream()
                .filter(s -> s.getSessionKey() == sessionKey)
                .map(Session::getMeetingKey)
                .distinct()
                .collect(Collectors.toList());
    }
}
