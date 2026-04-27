package main;

import models.*;
import exceptions.InvalidUsageException;
import interfaces.AlertSystem;
import services.ReportGenerator;
import threads.MonitorThread;

import java.util.HashMap;

public class Main {
    
    // Implementing Interface
    static class CampusAlertSystem implements AlertSystem {
        @Override
        public void generateAlert(double totalConsumption, String roomName) {
            System.out.println("\n⚠️ ALERT: High Electricity Usage in Room " + roomName + "! (" + String.format("%.2f", totalConsumption) + " kWh)");
        }

        @Override
        public void provideRecommendations() {
            System.out.println("💡 RECOMMENDATION: Turn off ACs/Lights when not in use. Opt for natural sunlight to save energy and reduce carbon footprint.");
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("    SMART ELECTRICITY USAGE MONITOR SYSTEM");
        System.out.println("==================================================\n");

        // Java Collections: HashMap to store rooms efficiently
        HashMap<String, Room> campusRooms = new HashMap<>();

        try {
            // Object Creation
            Room hostelRoom = new Room("101 (Hostel)");
            Room classRoom = new Room("202 (Classroom)");
            Room officeRoom = new Room("303 (Office)");

            // Adding appliances
            // AC: 1.5 kW, Fan: 0.075 kW, Light: 0.020 kW
            hostelRoom.addAppliance(new AC("101 (Hostel)", 1.5, 8)); // 8 hours of AC daily
            hostelRoom.addAppliance(new Fan("101 (Hostel)", 0.075, 12));
            hostelRoom.addAppliance(new Light("101 (Hostel)", 0.020, 6));

            classRoom.addAppliance(new AC("202 (Classroom)", 2.0, 6));
            classRoom.addAppliance(new Fan("202 (Classroom)", 0.075, 8));
            classRoom.addAppliance(new Fan("202 (Classroom)", 0.075, 8));
            classRoom.addAppliance(new Light("202 (Classroom)", 0.040, 5));
            classRoom.addAppliance(new Light("202 (Classroom)", 0.040, 5));

            officeRoom.addAppliance(new Fan("303 (Office)", 0.075, 9));
            officeRoom.addAppliance(new Light("303 (Office)", 0.020, 9));
            officeRoom.addAppliance(new Light("303 (Office)", 0.020, 9));
            
            // Exception Handling Demo: Deliberately passing invalid usage hours (e.g. 25 hours a day)
            // Uncommenting the below line will trigger the InvalidUsageException
            // officeRoom.addAppliance(new Light("303 (Office)", 0.020, 25));

            campusRooms.put("101", hostelRoom);
            campusRooms.put("202", classRoom);
            campusRooms.put("303", officeRoom);

        } catch (InvalidUsageException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }

        System.out.println("--- Starting Live Room Monitoring ---");
        AlertSystem alertSystem = new CampusAlertSystem();
        
        // Threshold for alerts (e.g., more than 200 kWh per month triggers an alert)
        double usageThreshold = 200.0; 

        // Multithreading: Creating threads for concurrent monitoring
        MonitorThread t1 = new MonitorThread(campusRooms.get("101"), alertSystem, usageThreshold);
        MonitorThread t2 = new MonitorThread(campusRooms.get("202"), alertSystem, usageThreshold);
        MonitorThread t3 = new MonitorThread(campusRooms.get("303"), alertSystem, usageThreshold);

        t1.start();
        t2.start();
        t3.start();

        // Wait for all monitoring threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        // Generating Final Ranking Report
        ReportGenerator.generateGreenRoomRanking(campusRooms);
    }
}
