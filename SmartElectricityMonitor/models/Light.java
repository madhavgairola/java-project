package models;
import exceptions.InvalidUsageException;

// Inheritance: Light extends Appliance
public class Light extends Appliance {
    public Light(String roomNumber, double powerRatingKw, double dailyUsageHours) throws InvalidUsageException {
        super("Light Bulb", roomNumber, powerRatingKw, dailyUsageHours);
    }

    @Override
    public double calculateMonthlyConsumption() {
        return powerRatingKw * dailyUsageHours * 30;
    }
}
