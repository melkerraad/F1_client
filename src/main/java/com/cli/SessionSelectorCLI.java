package com.cli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.f1client.analytics.*;
import com.f1client.service.*;
import com.f1client.loader.ServiceLoader;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SessionSelectorCLI {

    private static final String SESSION_MAP_PATH = "src\\main\\java\\com\\resources\\session_map.json";

    public static void main(String[] args) throws IOException {
        new SessionSelectorCLI().run();
    }

    public void run() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(SESSION_MAP_PATH);

        if (!jsonFile.exists()) {
            System.err.println("Session_map.json not found. Please run SessionMapUpdater first.");
            return;
        }

        Map<String, Map<String, Map<String, Object>>> data =
                mapper.readValue(jsonFile, new TypeReference<>() {});

        Scanner scanner = new Scanner(System.in);

        // Ask for Grand Prix selection
        List<String> gps = new ArrayList<>(data.keySet());
        Collections.sort(gps);

        System.out.println("\n Select a Grand Prix:");
        for (int i = 0; i < gps.size(); i++) {
            System.out.printf("%3d. %s%n", i + 1, gps.get(i));
        }
        System.out.print("\nEnter number: ");
        int gpIndex = getChoice(scanner, gps.size());
        String selectedGP = gps.get(gpIndex - 1);

        // Ask for session selection
        Map<String, Map<String, Object>> sessions = data.get(selectedGP);
        List<String> sessionNames = new ArrayList<>(sessions.keySet());
        Collections.sort(sessionNames);

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

        // Get services running for specified session
        ServiceLoader serviceLoader = new ServiceLoader(sessionKey);
        DriverService driverService = serviceLoader.getDriverService();
        LapService lapService = serviceLoader.getLapTimesService();
        StintService stintService = serviceLoader.getStintService();
        SessionService sessionService = serviceLoader.getSessionService();
        RaceControlEventService raceControlEventService = serviceLoader.getRaceControlEventService();
        PitstopService pitstopService = serviceLoader.getPitstopService();
        PositionService positionService = serviceLoader.getPositionService();

        // Select analysis type
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

        // === Step 5: Run the chosen analysis ===
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

        System.out.println("\nAnalysis completed.");
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
