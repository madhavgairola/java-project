package models;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomNumber;
    private double threshold; // Max allowed kWh for this room
    // Java Collections: ArrayList to store multiple appliances
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
        // Control Statements: For-each loop
        for (Appliance app : appliances) {
            total += app.calculateMonthlyConsumption();
        }
        return total;
    }

    // Efficiency: What % of the threshold is being used
    // Lower % = greener room
    public double getUsagePercentage() {
        return (getTotalMonthlyConsumption() / threshold) * 100;
    }

    public String getRoomNumber() { return roomNumber; }
    public double getThreshold() { return threshold; }
    public List<Appliance> getAppliances() { return appliances; }
}
