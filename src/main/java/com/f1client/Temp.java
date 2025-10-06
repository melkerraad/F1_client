package com.f1client;
import java.util.List;
import com.f1client.loader.DriverLoader;
import com.f1client.loader.PitstopLoader;
import com.f1client.model.Driver;
import com.f1client.model.Pitstop;

public class Temp {
    public static void main(String[] args) {
        try {
            List<Driver> drivers = new DriverLoader().loadData();
            System.out.println(drivers);
            System.out.println("-----");
            List<Pitstop> pitstops = new PitstopLoader().loadData();
            System.out.println(pitstops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
