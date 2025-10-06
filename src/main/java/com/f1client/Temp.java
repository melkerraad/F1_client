package com.f1client;
import java.util.List;

import com.f1client.loader.DriverLoader;
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
            List<Driver> drivers = new DriverLoader().loadData();
            List<Pitstop> pitstops = new PitstopLoader().loadData();
            List<Session> sessions = new SessionLoader().loadData();
            Session sess = sessions.get(0);
            //new Temp().APItest(drivers, pitstops);
            SessionUtils s = new SessionUtils();
            System.out.println(s.getDriversForSession(drivers, sess));
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
