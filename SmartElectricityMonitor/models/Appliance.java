package models;

import exceptions.InvalidUsageException;

public abstract class Appliance {
    protected String name;
    protected String roomNumber;
    protected double powerRatingKw; // in Kilowatts
    protected double dailyUsageHours;

    public Appliance(String name, String roomNumber, double powerRatingKw, double dailyUsageHours) throws InvalidUsageException {
        // Exception Handling: Checking for invalid inputs
        if (powerRatingKw <= 0 || dailyUsageHours < 0 || dailyUsageHours > 24) {
            throw new InvalidUsageException("Invalid power rating or usage hours for appliance: " + name + " in room " + roomNumber);
        }
        this.name = name;
        this.roomNumber = roomNumber;
        this.powerRatingKw = powerRatingKw;
        this.dailyUsageHours = dailyUsageHours;
    }

    // Abstraction: Abstract method to be implemented by child classes
    public abstract double calculateMonthlyConsumption();

    public String getName() { return name; }
    public String getRoomNumber() { return roomNumber; }
    public double getPowerRatingKw() { return powerRatingKw; }
    public double getDailyUsageHours() { return dailyUsageHours; }

    @Override
    public String toString() {
        return name + " (" + powerRatingKw + " kW, " + dailyUsageHours + " hrs/day)";
    }
}
