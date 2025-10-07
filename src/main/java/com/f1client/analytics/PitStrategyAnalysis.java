package com.f1client.analytics;

import com.f1client.model.Driver;
import com.f1client.model.Pitstop;
import com.f1client.model.Stint;
import com.f1client.service.DriverService;
import com.f1client.service.PitstopService;
import com.f1client.service.StintService;

import java.util.List;

public class PitStrategyAnalysis {

    private final PitstopService pitstopService;
    private final StintService stintService;
    private final DriverService driverService;

    public PitStrategyAnalysis(DriverService driverService, StintService stintService, PitstopService pitstopService) {
        this.driverService = driverService;
        this.stintService = stintService;
        this.pitstopService = pitstopService;
    }

    public PitStrategyAnalysis(DriverService driverService, StintService stintService) {
        this(driverService, stintService, new PitstopService(driverService));
    }

    public void analyzePitStops(int sessionKey) {
        for (Driver driver : driverService.getAllDrivers()) {
            List<Pitstop> pitstops = pitstopService.getPitstopsByDriver(driver.getDriverNumber(), sessionKey);
            List<Stint> stints = stintService.getStintsForDriver(driver.getDriverNumber());

            if (stints.isEmpty()) {
                System.out.println("No stint data for car " + driver.getDriverNumber());
                continue;
            }

            printPitStrategy(driver.getDriverNumber(), stints, pitstops);
        }
    }

    private void printPitStrategy(int driverNumber, List<Stint> stints, List<Pitstop> pitstops) {
        System.out.println("\nPit strategy for car " + driverNumber + ":");
        System.out.println("Stint | Compound | Lap Range | Pit Lap | Pit Duration (s)");

        for (Stint stint : stints) {
            Pitstop pitInStint = null;
            for (Pitstop pit : pitstops) {
                if (pit.getLapNumber() > 0 &&
                    pit.getLapNumber() >= stint.getLapStart() &&
                    pit.getLapNumber() <= stint.getLapEnd()) {
                    pitInStint = pit;
                    break;
                }
            }

            String pitLapStr = pitInStint != null ? String.valueOf(pitInStint.getLapNumber()) : "-";
            String pitDurationStr = pitInStint != null ? String.format("%.2f", pitInStint.getPitDuration()) : "-";

            System.out.printf("%5d | %8s | %3d-%-3d | %7s | %16s%n",
                    stint.getStintNumber(),
                    stint.getCompound(),
                    stint.getLapStart(),
                    stint.getLapEnd(),
                    pitLapStr,
                    pitDurationStr
            );
        }
    }
}
