package threads;

import models.Room;
import interfaces.AlertSystem;

public class MonitorThread extends Thread {
    private Room room;
    private AlertSystem alertSystem;

    public MonitorThread(Room room, AlertSystem alertSystem) {
        this.room = room;
        this.alertSystem = alertSystem;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000 + (long) (Math.random() * 3000));
            double consumption = room.getTotalMonthlyConsumption();
            double threshold = room.getThreshold();
            System.out.println("[Monitor] Room " + room.getRoomNumber() + " analyzed. Consumption: " + String.format("%.2f", consumption) + " kWh (Threshold: " + String.format("%.0f", threshold) + " kWh)");
            
            if (consumption > threshold) {
                alertSystem.generateAlert(consumption, room.getRoomNumber());
                alertSystem.provideRecommendations();
            }
        } catch (InterruptedException e) {
            System.out.println("Monitoring interrupted for room: " + room.getRoomNumber());
        }
    }
}
