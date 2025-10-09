package com.f1client.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

public class SessionMapUpdater {

    private static final String API_URL = "https://api.openf1.org/v1/sessions";
    private static final String OUTPUT_PATH = "src\\main\\java\\com\\resources\\session_map.json";

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Fetching session data from OpenF1...");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.err.println("❌ Failed to fetch data. HTTP status: " + response.statusCode());
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> sessions = mapper.readValue(response.body(), new TypeReference<>() {});

        // Group sessions by "Grand Prix + Year"
        Map<String, Map<String, Map<String, Object>>> grouped = sessions.stream()
                .filter(s -> s.get("year") != null && s.get("country_name") != null && s.get("session_name") != null)
                .collect(Collectors.groupingBy(
                        s -> ((String) s.get("country_name")).trim() + " Grand Prix " + ((Integer) s.get("year")),
                        Collectors.toMap(
                                s -> (String) s.get("session_name"),
                                s -> Map.of(
                                        "meeting_key", s.get("meeting_key"),
                                        "session_key", s.get("session_key"),
                                        "location", s.get("location"),
                                        "session_type", s.get("session_type")
                                ),
                                (a, b) -> a  // if duplicate, keep first
                        )
                ));

        // Write JSON file
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File output = new File(OUTPUT_PATH);
        mapper.writeValue(output, grouped);

        System.out.println("✅ Session mapping created successfully:");
        System.out.println("→ " + output.getAbsolutePath());
        System.out.println("Entries: " + grouped.size());
    }
}
