package com.cli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.f1client.analytics.*;
import com.f1client.loader.ServiceLoader;
import com.f1client.service.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class SessionSelectorCLI {

    private static final String SESSION_MAP_PATH = "src\\main\\java\\com\\resources\\session_map.json";
    private static final ZonedDateTime FAR_FUTURE = ZonedDateTime.parse("9999-12-31T23:59:59Z");

    public static void main(String[] args) throws IOException {
        new SessionSelectorCLI().run();
    }

    public void run() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(SESSION_MAP_PATH);

        if (!jsonFile.exists()) {
            System.err.println("❌ session_map.json not found. Please run SessionMapUpdater first.");
            return;
        }

        Map<String, Map<String, Map<String, Object>>> data;
        try (Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
            data = mapper.readValue(reader, new TypeReference<>() {});
        }

        Scanner scanner = new Scanner(System.in);

        // Build list of (GP, earliestDate) and sort by earliestDate
        List<Map.Entry<String, ZonedDateTime>> gpWithDates = data.entrySet().stream()
                .filter(e -> !e.getKey().startsWith("Unknown GP"))
                .map(e -> {
                    ZonedDateTime minDate = e.getValue().values().stream()
                            .map(s -> {
                                Object ds = s.get("date_start");
                                if (ds instanceof String) {
                                    try {
                                        return ZonedDateTime.parse((String) ds);
                                    } catch (DateTimeParseException ex) {
                                        // ignore invalid dates
                                    }
                                }
                                return null;
                            })
                            .filter(Objects::nonNull)
                            .min(Comparator.naturalOrder())
                            .orElse(FAR_FUTURE);
                    return Map.entry(e.getKey(), minDate);
                })
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());

        List<String> gps = gpWithDates.stream().map(Map.Entry::getKey).collect(Collectors.toList());

        System.out.println("\n Select a Grand Prix (chronological order):");
        for (int i = 0; i < gps.size(); i++) {
            System.out.printf("%3d. %s%n", i + 1, gps.get(i));
        }

        System.out.print("\nEnter number: ");
        int gpIndex = getChoice(scanner, gps.size());
        String selectedGP = gps.get(gpIndex - 1);

        // Sort sessions for this GP by date_start (fallback to session_key)
        Map<String, Map<String, Object>> sessions = data.get(selectedGP);
        List<String> sessionNames = new ArrayList<>(sessions.keySet());

        sessionNames.sort(Comparator.comparing(sName -> {
            Map<String, Object> sMap = sessions.get(sName);
            Object ds = sMap.get("date_start");
            if (ds instanceof String) {
                try {
                    return ZonedDateTime.parse((String) ds);
                } catch (DateTimeParseException ignored) {
                }
            }
            Object sk = sMap.get("session_key");
            if (sk instanceof Number) {
                long val = ((Number) sk).longValue();
                return ZonedDateTime.ofInstant(
                        java.time.Instant.ofEpochSecond(Math.max(0, Math.min(val, 2_000_000_000L))),
                        java.time.ZoneOffset.UTC
                );
            }
            return FAR_FUTURE;
        }));

        System.out.println("\n Available sessions for " + selectedGP + ":");
        for (int i = 0; i < sessionNames.size(); i++) {
            System.out.printf("%3d. %s%n", i + 1, sessionNames.get(i));
        }

        System.out.print("\nEnter number: ");
        int sessionIndex = getChoice(scanner, sessionNames.size());
        String selectedSession = sessionNames.get(sessionIndex - 1);

        Map<String, Object> sessionInfo = sessions.get(selectedSession);
        int sessionKey = (int) sessionInfo.get("session_key");

        System.out.println("\n Selected:");
        System.out.println("Grand Prix: " + selectedGP);
        System.out.println("Session:    " + selectedSession);
        System.out.println("Session Key: " + sessionKey);

        // Load services
        ServiceLoader serviceLoader = new ServiceLoader(sessionKey);
        DriverService driverService = serviceLoader.getDriverService();
        LapService lapService = serviceLoader.getLapTimesService();
        StintService stintService = serviceLoader.getStintService();
        SessionService sessionService = serviceLoader.getSessionService();
        RaceControlEventService raceControlEventService = serviceLoader.getRaceControlEventService();
        PitstopService pitstopService = serviceLoader.getPitstopService();
        PositionService positionService = serviceLoader.getPositionService();

        List<String> analyses = List.of(
                "Compound Performance",
                "Compound Degradation",
                "Pit Strategy",
                "Average Lap Times",
                "Race Control Events"
        );

        System.out.println("\nChoose analysis type:");
        for (int i = 0; i < analyses.size(); i++) {
            System.out.printf("%3d. %s%n", i + 1, analyses.get(i));
        }

        System.out.print("\nEnter number: ");
        int analysisIndex = getChoice(scanner, analyses.size());
        String selectedAnalysis = analyses.get(analysisIndex - 1);

        System.out.println("\nRunning " + selectedAnalysis + " analysis...\n");

        switch (selectedAnalysis) {
            case "Compound Performance" -> {
                CompoundAnalytics ca = new CompoundAnalytics(lapService, stintService, driverService, sessionService);
                ca.analyzeCompoundPerformance(sessionKey);
            }
            case "Compound Degradation" -> {
                CompoundAnalytics ca = new CompoundAnalytics(lapService, stintService, driverService, sessionService);
                ca.analyzeCompoundDegradation(sessionKey).forEach(System.out::println);
            }
            case "Pit Strategy" -> {
                PitStrategyAnalysis psa = new PitStrategyAnalysis(driverService, stintService, pitstopService, positionService);
                psa.printPitStrategy(sessionKey);
            }
            case "Average Lap Times" -> {
                SessionAnalytics sa = new SessionAnalytics(driverService, lapService, stintService, sessionService, raceControlEventService, pitstopService);
                sa.averageLapTimePerDriver(sessionKey).forEach((car, avg) ->
                        System.out.println("Car " + car + " avg lap time: " + String.format("%.3f s", avg)));
            }
            case "Race Control Events" -> {
                RaceControlAnalysis rca = new RaceControlAnalysis(raceControlEventService);
                rca.analyzeSession(sessionKey);
            }
            default -> System.out.println("Unknown analysis selected.");
        }

        System.out.println("\n✅ Analysis completed.");
    }

    private int getChoice(Scanner scanner, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= max) return choice;
                System.out.print("Invalid choice, try again: ");
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number: ");
            }
        }
    }
}
