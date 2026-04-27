package models;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomNumber;
    // Java Collections: ArrayList to store multiple appliances
    private List<Appliance> appliances;

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
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

    public String getRoomNumber() { return roomNumber; }
    public List<Appliance> getAppliances() { return appliances; }
}
