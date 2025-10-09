package com.f1client.loader;
import com.f1client.service.DriverService;
import com.f1client.service.PitstopService;
import com.f1client.service.RaceControlEventService;
import com.f1client.service.SessionService;
import com.f1client.service.StintService;
import com.f1client.service.LapService;
import com.f1client.service.PositionService;

public class ServiceLoader {
    private int sessionKey;

    // Service instances
    private DriverService driverService;
    private LapService lapTimesService;
    private PitstopService pitstopService;
    private PositionService positionService;
    private RaceControlEventService raceControlEventService;
    private SessionService sessionService;
    private StintService stintService;

    public ServiceLoader(int sessionKey) {
        this.sessionKey = sessionKey;

        // Initialize all services
        this.driverService = new DriverService();
        this.lapTimesService = new LapService(sessionKey);
        this.pitstopService = new PitstopService(driverService);
        this.positionService = new PositionService(sessionKey);
        this.raceControlEventService = new RaceControlEventService();
        this.sessionService = new SessionService();
        try {
            this.stintService = new StintService(sessionKey);
        } catch (Exception e) {
            System.err.println("Failed to initialize StintService: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Getters for each service
    public DriverService getDriverService() { return driverService; }
    public LapService getLapTimesService() { return lapTimesService; }
    public PitstopService getPitstopService() { return pitstopService; }
    public PositionService getPositionService() { return positionService; }
    public RaceControlEventService getRaceControlEventService() { return raceControlEventService; }
    public SessionService getSessionService() { return sessionService; }
    public StintService getStintService() { return stintService; }
}
