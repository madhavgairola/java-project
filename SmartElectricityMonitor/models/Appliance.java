package models;

import exceptions.InvalidUsageException;

public abstract class Appliance {
    protected String name;
    protected String roomNumber;
    protected double powerRatingKw;
    protected double dailyUsageHours;

    public Appliance(String name, String roomNumber, double powerRatingKw, double dailyUsageHours) throws InvalidUsageException {
        if (powerRatingKw <= 0 || dailyUsageHours < 0 || dailyUsageHours > 24) {
            throw new InvalidUsageException("Invalid power rating or usage hours for appliance: " + name + " in room " + roomNumber);
        }
        this.name = name;
        this.roomNumber = roomNumber;
        this.powerRatingKw = powerRatingKw;
        this.dailyUsageHours = dailyUsageHours;
    }

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
