package main;

import models.*;
import exceptions.InvalidUsageException;
import interfaces.AlertSystem;
import services.ReportGenerator;
import services.DashboardGenerator;
import threads.MonitorThread;

import java.util.HashMap;

public class Main {
    
    static class CampusAlertSystem implements AlertSystem {
        @Override
        public void generateAlert(double totalConsumption, String roomName) {
            System.out.println("\n[!] ALERT: High Electricity Usage in Room " + roomName + "! (" + String.format("%.2f", totalConsumption) + " kWh)");
        }

        @Override
        public void provideRecommendations() {
            System.out.println("[*] RECOMMENDATION: Turn off ACs/Lights when not in use. Opt for natural sunlight to save energy and reduce carbon footprint.");
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("    SMART ELECTRICITY USAGE MONITOR SYSTEM");
        System.out.println("==================================================\n");

        HashMap<String, Room> campusRooms = new HashMap<>();

        try {
            Room hostelRoom = new Room("101 (Hostel)", 500);
            Room classRoom = new Room("202 (Classroom)", 400);
            Room officeRoom = new Room("303 (Office)", 50);

            hostelRoom.addAppliance(new AC("101 (Hostel)", 1.5, 8));
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

            campusRooms.put("101", hostelRoom);
            campusRooms.put("202", classRoom);
            campusRooms.put("303", officeRoom);

        } catch (InvalidUsageException e) {
            System.err.println("[ERROR]: " + e.getMessage());
        }

        System.out.println("--- Starting Live Room Monitoring ---");
        AlertSystem alertSystem = new CampusAlertSystem();

        MonitorThread t1 = new MonitorThread(campusRooms.get("101"), alertSystem);
        MonitorThread t2 = new MonitorThread(campusRooms.get("202"), alertSystem);
        MonitorThread t3 = new MonitorThread(campusRooms.get("303"), alertSystem);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        ReportGenerator.generateGreenRoomRanking(campusRooms);
        DashboardGenerator.generateDashboard(campusRooms, "dashboard.html");
    }
}
