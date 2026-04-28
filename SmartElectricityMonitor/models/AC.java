package models;
import exceptions.InvalidUsageException;

public class AC extends Appliance {
    public AC(String roomNumber, double powerRatingKw, double dailyUsageHours) throws InvalidUsageException {
        super("Air Conditioner", roomNumber, powerRatingKw, dailyUsageHours);
    }

    @Override
    public double calculateMonthlyConsumption() {
        return powerRatingKw * dailyUsageHours * 30;
    }
}
