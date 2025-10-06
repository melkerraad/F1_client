package com.f1client;
import java.util.List;

import com.f1client.service.DriverService;
import com.f1client.service.PitstopService;
import com.f1client.loader.PitstopLoader;
import com.f1client.loader.SessionLoader;
import com.f1client.model.Driver;
import com.f1client.model.Pitstop;
import com.f1client.model.Session;
import com.f1client.util.SessionUtils;
import com.f1client.*;

public class Temp {
    public static void main(String[] args) {
        try {
            DriverService driverService = new DriverService();
            PitstopService pitstopService = new PitstopService(driverService);
            List<Session> sessions = new SessionLoader().loadData();
            Session sess = sessions.get(0);
            SessionUtils s = new SessionUtils();
            System.out.println(s.getDriversForSession(driverService.getAllDrivers(), sess));
            System.out.println(driverService.getTeamsPerDriver("Nico HULKENBERG"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void APItest(List<Driver> drivers, List<Pitstop> pitstops) {
        try {
            System.out.println(drivers);
            System.out.println("-----");
            System.out.println(pitstops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
