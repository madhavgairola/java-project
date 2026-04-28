package models;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomNumber;
    private double threshold;
    private List<Appliance> appliances;

    public Room(String roomNumber, double threshold) {
        this.roomNumber = roomNumber;
        this.threshold = threshold;
        this.appliances = new ArrayList<>();
    }

    public void addAppliance(Appliance appliance) {
        appliances.add(appliance);
    }

    public double getTotalMonthlyConsumption() {
        double total = 0;
        for (Appliance app : appliances) {
            total += app.calculateMonthlyConsumption();
        }
        return total;
    }

    public double getUsagePercentage() {
        return (getTotalMonthlyConsumption() / threshold) * 100;
    }

    public String getRoomNumber() { return roomNumber; }
    public double getThreshold() { return threshold; }
    public List<Appliance> getAppliances() { return appliances; }
}
