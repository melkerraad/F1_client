package com.f1client.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SessionMapUpdater {

    private static final String SESSION_API_URL = "https://api.openf1.org/v1/sessions";
    private static final String MEETING_API_URL_TEMPLATE = "https://api.openf1.org/v1/meetings?year=%d&country_name=%s";
    private static final String OUTPUT_PATH = "src\\main\\java\\com\\resources\\session_map.json";

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Fetching session data from OpenF1...");

        HttpClient client = HttpClient.newHttpClient();

        // Fetch all sessions
        HttpRequest sessionRequest = HttpRequest.newBuilder()
                .uri(URI.create(SESSION_API_URL))
                .GET()
                .build();

        HttpResponse<String> sessionResponse = client.send(sessionRequest, HttpResponse.BodyHandlers.ofString());

        if (sessionResponse.statusCode() != 200) {
            System.err.println("❌ Failed to fetch sessions. HTTP status: " + sessionResponse.statusCode());
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> sessions = mapper.readValue(sessionResponse.body(), new TypeReference<>() {});

        // LinkedHashMap to preserve insertion order
        Map<String, Map<String, Map<String, Object>>> grouped = new LinkedHashMap<>();

        for (Map<String, Object> session : sessions) {
            Number yearNum = (Number) session.get("year");
            int year = yearNum != null ? yearNum.intValue() : 0;
            String countryName = session.get("country_name") == null ? "Unknown" : ((String) session.get("country_name")).trim();
            String sessionName = (String) session.get("session_name");
            Object sessionKey = session.get("session_key");
            Object meetingKey = session.get("meeting_key");
            String location = (String) session.get("location");
            String sessionType = (String) session.get("session_type");

            Map<String, Object> meetingInfo = fetchMeetingInfo(client, year, countryName);
            String meetingName = (String) meetingInfo.getOrDefault("meeting_official_name",
                    meetingInfo.getOrDefault("meeting_name",
                            countryName + " " + year));
            // meeting API returns date_start as ISO string; keep it if present
            String dateStartStr = meetingInfo.get("date_start") != null ? (String) meetingInfo.get("date_start") : null;

            // prepare session map (include date_start)
            Map<String, Object> sessionData = new LinkedHashMap<>();
            sessionData.put("session_key", sessionKey);
            sessionData.put("meeting_key", meetingKey);
            sessionData.put("location", location);
            sessionData.put("session_type", sessionType);
            if (dateStartStr != null) {
                sessionData.put("date_start", dateStartStr);
            }

            // Use meetingName (official name typically includes year) as GP key
            grouped.computeIfAbsent(meetingName, k -> new LinkedHashMap<>())
                    .put(sessionName, sessionData);
        }

        // Write JSON file with UTF-8
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File output = new File(OUTPUT_PATH);
        output.getParentFile().mkdirs();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8)) {
            mapper.writeValue(writer, grouped);
        }

        System.out.println("✅ Session mapping created successfully:");
        System.out.println("→ " + output.getAbsolutePath());
        System.out.println("Entries: " + grouped.size());
    }

    private static Map<String, Object> fetchMeetingInfo(HttpClient client, int year, String countryName) {
        try {
            String url = String.format(MEETING_API_URL_TEMPLATE, year,
                    URLEncoder.encode(countryName, StandardCharsets.UTF_8));
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) return Collections.emptyMap();

            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> meetings = mapper.readValue(response.body(), new TypeReference<>() {});
            if (!meetings.isEmpty()) {
                return meetings.get(0);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Failed to fetch meeting info for " + countryName + " " + year + ": " + e.getMessage());
        }
        return Collections.emptyMap();
    }
}
