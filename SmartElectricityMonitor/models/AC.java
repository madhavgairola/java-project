package models;
import exceptions.InvalidUsageException;

// Inheritance: AC extends Appliance
public class AC extends Appliance {
    public AC(String roomNumber, double powerRatingKw, double dailyUsageHours) throws InvalidUsageException {
        super("Air Conditioner", roomNumber, powerRatingKw, dailyUsageHours);
    }

    @Override
    public double calculateMonthlyConsumption() {
        // ACs might have different consumption patterns, but for simplicity we calculate based on 30 days
        return powerRatingKw * dailyUsageHours * 30;
    }
}
