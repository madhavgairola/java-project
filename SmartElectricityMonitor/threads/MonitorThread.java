package threads;

import models.Room;
import interfaces.AlertSystem;

// Multithreading: Extending Thread class
public class MonitorThread extends Thread {
    private Room room;
    private AlertSystem alertSystem;
    private double threshold;

    public MonitorThread(Room room, AlertSystem alertSystem, double threshold) {
        this.room = room;
        this.alertSystem = alertSystem;
        this.threshold = threshold;
    }

    @Override
    public void run() {
        try {
            // Simulating real-time monitoring delay (Slower for Viva presentation)
            Thread.sleep(2000 + (long) (Math.random() * 3000));
            double consumption = room.getTotalMonthlyConsumption();
            System.out.println("[Monitor] Room " + room.getRoomNumber() + " analyzed. Consumption: " + String.format("%.2f", consumption) + " kWh");
            
            // Generate alert if usage exceeds threshold
            if (consumption > threshold) {
                alertSystem.generateAlert(consumption, room.getRoomNumber());
                alertSystem.provideRecommendations();
            }
        } catch (InterruptedException e) {
            System.out.println("Monitoring interrupted for room: " + room.getRoomNumber());
        }
    }
}
